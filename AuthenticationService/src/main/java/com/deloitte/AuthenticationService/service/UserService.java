package com.deloitte.AuthenticationService.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.deloitte.AuthenticationService.dto.UserDTO;
import com.deloitte.AuthenticationService.dto.UserLoginDTO;
import com.deloitte.AuthenticationService.response.UserDTOResponse;

public abstract class UserService implements UserDetailsService, AuthenticationProvider {

	public abstract Optional<UserDTOResponse> registerUserDetails(UserDTO userDetails);

	public abstract Optional<String> getUserDetails(UserLoginDTO userDetails);

	public abstract Optional<UserDTOResponse> validatateToken(String token);

	public abstract Optional<UserDTOResponse> fetchUserDetails(String id);

	public abstract Optional<UserDTOResponse> updateUser(String id, Map<String, Object> userDetails);

	public abstract Optional<UserDTOResponse> deleteUser(String id);
}
