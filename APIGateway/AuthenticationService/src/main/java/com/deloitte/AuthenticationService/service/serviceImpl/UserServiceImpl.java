package com.deloitte.AuthenticationService.service.serviceImpl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.deloitte.AuthenticationService.dto.UserDTO;
import com.deloitte.AuthenticationService.dto.UserLoginDTO;
import com.deloitte.AuthenticationService.entity.Address;
import com.deloitte.AuthenticationService.entity.User;
import com.deloitte.AuthenticationService.exception.DuplicateEntryException;
import com.deloitte.AuthenticationService.exception.InvalidCredentialsException;
import com.deloitte.AuthenticationService.exception.InvalidTokenException;
import com.deloitte.AuthenticationService.exception.UserNotFoundException;
import com.deloitte.AuthenticationService.repository.UserServiceRepository;
import com.deloitte.AuthenticationService.response.UserDTOResponse;
import com.deloitte.AuthenticationService.service.UserService;
import com.deloitte.AuthenticationService.utility.JwtTokenUtil;

@Service
public class UserServiceImpl extends UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserServiceRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtToken;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Optional<UserDTOResponse> registerUserDetails(UserDTO userDetails) {

		try {
			User user = modelMapper.map(userDetails, User.class);
			Address add = modelMapper.map(userDetails.getAddress(), Address.class);
			user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
			user.setAddress(add);
			logger.debug("User Details passed to Database: {}", user.toString());
			user = userRepository.save(user);
			logger.info("User is successfully saved in records: {}", user.toString());
			return Optional.ofNullable(modelMapper.map(userDetails, UserDTOResponse.class));
		} catch (DataIntegrityViolationException ex) {
			logger.error("Error Occured: User Details already with requested exists");
			throw new DuplicateEntryException("User Details already exists");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Fetching User Details from records with username: {}", username);
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UserNotFoundException("User Details not found"));
		return user;
	}

	@Override
	public Optional<String> getUserDetails(UserLoginDTO userDTO) {
		UserDetails user = loadUserByUsername(userDTO.getEmail());
		logger.info("Sucessfully fetched User Details from records with username: {}", userDTO.getEmail());
		if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
			logger.info("Sucessfully generated Authorization Token for username: {}", userDTO.getEmail());
			return Optional.ofNullable(jwtToken.generateToken(user));
		} else {
			logger.error("Error Occured: Invalid Credentials with requested Details for username: {}",
					userDTO.getEmail());
			throw new InvalidCredentialsException("Invalid Credentials");
		}
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			UserDetails userDetails = loadUserByUsername(authentication.getName());
			logger.info("Sucessfully fetched User Details from records with username: {}", authentication.getName());
			return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
					userDetails.getAuthorities());
		} catch (UsernameNotFoundException e) {
			logger.error("Error Occured: Invalid Credentials");
			throw new BadCredentialsException("Invalid Credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	public Optional<UserDTOResponse> validatateToken(String token) {
		try {
			UserDetails userDetail = loadUserByUsername(jwtToken.getUsernameFromToken(token));
			logger.info("Sucessfully fetched User Details from records with username: {}", userDetail.getUsername());
			return Optional
					.ofNullable(
							jwtToken.validateToken(token, userDetail)
									? modelMapper.map(
											userRepository.findByEmail(userDetail.getUsername()).orElseThrow(
													() -> new UserNotFoundException("User Details not found")),
											UserDTOResponse.class)
									: null);
		} catch (Exception ex) {
			logger.error("Error Occured: Invalid Credentials");
			throw new InvalidTokenException("Invalid Credentials");
		}
	}

	public User fetchUserDetails(Long id) {
		Optional<User> usr = userRepository.findById(id);
		if (usr.isPresent()) {
			return usr.get();
		} else {
			logger.error("User Details not found");
			throw new UserNotFoundException("User Details not found");
		}
	}

	@Override
	public Optional<UserDTOResponse> fetchUserDetails(String id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User userTemp = modelMapper.map(auth.getPrincipal(), User.class);
		boolean isValidUser = (userTemp.getId() == Long.parseLong(id)
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
		if (!isValidUser) {
			logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
		try {
			Optional<User> user = userRepository.findById(Long.parseLong(id));
			if (user.isPresent()) {
				logger.info("Sucessfully fetched User Details from records with user Id: {}", id);
				return Optional.ofNullable(
						modelMapper.map(user.orElseThrow(() -> new UserNotFoundException("User Details not found")),
								UserDTOResponse.class));
			} else {
				logger.error("Error Occured: User Details not found with user Id : {}", id);
				throw new UserNotFoundException("User Details not found for id: " + id);
			}
		} catch (Exception ex) {
			logger.error("Error Occured: User Details not found with user Id : {}", id);
			throw new UserNotFoundException("User Details not found for id: " + id);
		}
	}

	@Override
	public Optional<UserDTOResponse> updateUser(String id, Map<String, Object> fields) {
		Optional<User> res = Optional.ofNullable(userRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new UserNotFoundException("User Details not found")));
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User userTemp = modelMapper.map(auth.getPrincipal(), User.class);
			boolean isValidUser = (userTemp.getId() == Long.parseLong(id)
					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
			if (!isValidUser) {
				logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}", id);
				throw new InvalidCredentialsException(
						"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
			}
			Map<String,Object> tempfields = new HashMap<>();
			Map<String,Object> addressMap = new HashMap<>();
			for(Map.Entry<String,Object> set:fields.entrySet()){
				if(set.getKey().equals("address")) {
					 Map<String,Object> address = ((LinkedHashMap<String, Object>)set.getValue());
					for(Map.Entry<String,Object> addressSet: address. entrySet()){
						addressMap.put(addressSet.getKey(), addressSet.getValue());
						
					}
					
					tempfields.put(set.getKey(), addressMap);
				}else
				tempfields.put(set.getKey(), set.getValue());
				
				
			}
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			User userTemp = modelMapper.map(auth.getPrincipal(), User.class);
//			boolean isValidUser = (userTemp.getId() == Long.parseLong(id)
//					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
//			if (!isValidUser) {
//				logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}",
//						id);
//				throw new InvalidCredentialsException(
//						"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
//			}

			if (isValidUser && res.isPresent()) {
				tempfields.forEach((key, val) -> {
					Field field = ReflectionUtils.findField(User.class, key);
					if(field != null) {
					field.setAccessible(true);
					ReflectionUtils.setField(field, res.get(), val);
				}
				});
				res.get().setPassword(res.get().getPassword());
				userRepository.save(res.get());
				logger.info("User Details is updated successfully with requested Id : {}", id);
			}
		} catch (Exception ex) {
			logger.error("Access Denied : Missmatched Credentials with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
		return Optional.ofNullable(modelMapper.map(res, UserDTOResponse.class));
	}

	@Override
	public Optional<UserDTOResponse> deleteUser(String id) {
		Optional<User> usr = Optional.ofNullable(userRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new UserNotFoundException("User Details not found")));
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User userTemp = modelMapper.map(auth.getPrincipal(), User.class);
			boolean isValidUser = (userTemp.getId() == Long.parseLong(id)
					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
			if (!isValidUser) {
				logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}",
						id);
				throw new InvalidCredentialsException(
						"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
			}
			if (isValidUser && usr.isPresent()) {
				userRepository.delete(usr.get());
				logger.info("User Details is deleted successfully with requested Id : {}", id);
			}
		} catch (Exception ex) {
			logger.error("Access Denied : Missmatched Credentials with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
		return Optional.ofNullable(modelMapper.map(usr, UserDTOResponse.class));
	}

}
