package com.deloitte.ResturantService.service.serviceImpl;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.deloitte.ResturantService.dto.FoodItemDTO;
import com.deloitte.ResturantService.dto.OrderDTO;
import com.deloitte.ResturantService.dto.ResturantDTO;
import com.deloitte.ResturantService.dto.UserDTO;
import com.deloitte.ResturantService.dto.response.FoodItemMenuDTOResponse;
import com.deloitte.ResturantService.dto.response.ResturantDTOResponse;
import com.deloitte.ResturantService.entity.FoodItem;
import com.deloitte.ResturantService.entity.Resturant;
import com.deloitte.ResturantService.enums.RESTURANT_VERIFICATION_STATUS;
import com.deloitte.ResturantService.exception.DuplicateEntryException;
import com.deloitte.ResturantService.exception.InvalidCredentialsException;
import com.deloitte.ResturantService.exception.ResturantException;
import com.deloitte.ResturantService.exception.ResturantNotFoundException;
import com.deloitte.ResturantService.repository.FoodItemRepository;
import com.deloitte.ResturantService.repository.ResturantRepository;
import com.deloitte.ResturantService.service.ResturantService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResturantServiceImpl implements ResturantService {

	Logger logger = LoggerFactory.getLogger(ResturantServiceImpl.class);

	@Autowired
	private ResturantRepository resturantRepository;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.gateway.url}")
	private String apiUrl;

	@Override
	public Optional<ResturantDTOResponse> register(ResturantDTO resturant) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);
			Resturant resturantDetails = mapper.map(resturant, Resturant.class);
			resturantDetails.getFoodItems().stream().forEach(rst -> rst.setResturant(resturantDetails));
			resturantDetails.setIsVerified(RESTURANT_VERIFICATION_STATUS.PENDING_VERIFICATION);
			resturantDetails.setUserId(userTemp.getId());
			resturantRepository.save(resturantDetails);
			logger.info("Resturant is successfully saved in records: {}", resturantDetails.getId());
			return Optional.ofNullable(mapper.map(resturantDetails, ResturantDTOResponse.class));
		} catch (DataIntegrityViolationException e) {
			logger.error("Error Occured: Resturant Details already exists");
			throw new DuplicateEntryException("Resturant Details already exists");
		}
	}

	@Override
	public Optional<List<ResturantDTOResponse>> fetchResturant() {
		List<Resturant> resturantList = resturantRepository.findAll();
		if (resturantList.size() > 0) {
			resturantList = resturantList.stream()
					.filter(rst -> rst.getIsVerified().equals(RESTURANT_VERIFICATION_STATUS.VERIFIED))
					.collect(Collectors.toList());
			logger.info("Fetching All resturant Details from records {}", resturantList.toArray());
			return Optional.ofNullable(Arrays.asList(mapper.map(resturantList, ResturantDTOResponse[].class)));
		} else {
			logger.error("No resturant records available");
			return Optional.empty();
		}
	}

	@Override
	public Optional<ResturantDTOResponse> fetchResturant(String id) {
		Optional<Resturant> resturant = resturantRepository.findById(Long.parseLong(id));
		if (resturant.isPresent()) {
			logger.info("Fetching All resturant Details from records with Id : {}", resturant.get().getId());
			return Optional.ofNullable(mapper.map(
					resturant.orElseThrow(() -> new ResturantNotFoundException("Resturant Details not found")),
					ResturantDTOResponse.class));
		} else {
			logger.error("No resturant records available for Id: {}", id);
			throw new ResturantNotFoundException("Resturant Details not found for Id: " + id);
		}
	}

	@Override
	public Optional<ResturantDTOResponse> updateResturant(String id, Map<String, Object> fields) {

		Optional<Resturant> res = resturantRepository.findById(Long.parseLong(id));
		try {
			if (res.isPresent()) {
				logger.info("Successfully fetched resturant Details from records with id: {}", id);
				fields.forEach((key, val) -> {
					Field field = ReflectionUtils.findField(Resturant.class, key);
					field.setAccessible(true);
					ReflectionUtils.setField(field, res.get(), val);
				});
				logger.info("Successfully updated resturant Details for Id");
				resturantRepository.save(res.get());
			}
			return Optional.ofNullable(mapper.map(res.get(), ResturantDTOResponse.class));
		} catch (Exception ex) {
			logger.error("Failed to update resturant Details for details Id: {}", res.get().getId());
			throw new ResturantNotFoundException("Failed to update resturant Details for Id:" + res.get().getId());
		}
	}

	@Override
	public Optional<ResturantDTOResponse> deleteResturant(String id) {
		Optional<Resturant> resturant = Optional.ofNullable(resturantRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new ResturantNotFoundException("Resturant Details not found")));
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);
			boolean isValidUser = (userTemp.getId() == Long.parseLong(id)
					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
			if (!isValidUser) {
				logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}",
						id);
				throw new InvalidCredentialsException(
						"Access Denied : User is not Authorized to perform this action with requested Id :" + id);

			}
			if (isValidUser && resturant.isPresent()) {
				resturantRepository.delete(resturant.get());
				logger.info("Resturant Details is deleted successfully with requested Id : {}", id);
			}
		} catch (Exception ex) {
			logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
		return Optional.ofNullable(mapper.map(resturant, ResturantDTOResponse.class));
	}

	@Override
	public Optional<ResturantDTOResponse> deleteResturantByUserId(String id) {
		Optional<Resturant> resturant = resturantRepository.findByUserId(Long.parseLong(id));
		if (resturant.isEmpty())
			throw new ResturantNotFoundException("Resturant Details not found");
		try {
			resturantRepository.delete(resturant.get());
			return Optional.ofNullable(mapper.map(resturant, ResturantDTOResponse.class));
		} catch (Exception ex) {
			throw new ResturantException("Failed to Perform Delete Operation");
		}
	}

	@Override
	public Optional<List<FoodItemMenuDTOResponse>> fetchAllMenuByResturantId(String id) {
		List<FoodItem> foodItems = foodItemRepository.findAll();
		if (foodItems.size() > 0) {
			logger.info("Fetching All resturant Details from records with Id : {}", id);
			foodItems = foodItems.stream().filter(item -> item.getResturant().getId() == Long.parseLong(id))
					.collect(Collectors.toList());
			System.out.println("foodItems" + foodItems.toString());
			return Optional.ofNullable(Arrays.asList(mapper.map(foodItems, FoodItemMenuDTOResponse.class)));
		} else {
			logger.error("No resturant records available for Id: {}", id);
			throw new ResturantNotFoundException("Resturant Details not found for Id: " + id);
		}
	}

	@Override
	public Optional<ResturantDTOResponse> updateResturantInventory(String resturantId, FoodItemDTO itemDTO,
			String itemId) {

		Optional<FoodItem> foodItem = foodItemRepository.findById(Long.parseLong(itemId));
		Optional<Resturant> resturant = resturantRepository.findById(Long.parseLong(resturantId));
		if (resturant.isPresent()) {

			if (foodItem.isPresent()) {
				foodItem.get().setCategory(
						itemDTO.getCategory() == null ? foodItem.get().getCategory() : itemDTO.getCategory());
				foodItem.get().setName(itemDTO.getName() == null ? foodItem.get().getName() : itemDTO.getName());
				foodItem.get()
						.setStatus(itemDTO.getStatus() == null ? foodItem.get().getStatus() : itemDTO.getStatus());
				foodItem.get()
						.setDiscountedPrice(itemDTO.getDiscountedPrice() == null ? foodItem.get().getDiscountedPrice()
								: itemDTO.getDiscountedPrice());
				foodItem.get().setPrice(itemDTO.getPrice() == null ? foodItem.get().getPrice() : itemDTO.getPrice());
				foodItem.get().setResturant(resturant.get());
				foodItemRepository.save(foodItem.get());
				return Optional.ofNullable(mapper.map(resturantRepository.findById(Long.parseLong(resturantId)),
						ResturantDTOResponse.class));

			} else {
				logger.error("Menu Item Details not found for Id: {}", itemId);
				throw new ResturantNotFoundException("Menu Item Details not found for Id: " + itemId);
			}

		} else {
			logger.error("Resturant Details not found for Id: {}", resturantId);
			throw new ResturantNotFoundException("Resturant Details not found for Id: " + resturantId);
		}
	}

	@Override
	public Optional<ResturantDTOResponse> addResturantInventory(String resturantId, FoodItemDTO itemDTO) {

		Optional<Resturant> resturant = resturantRepository.findById(Long.parseLong(resturantId));
		if (resturant.isPresent()) {

			FoodItem fItems = mapper.map(itemDTO, FoodItem.class);
			fItems.setResturant(resturant.get());
			foodItemRepository.save(fItems);
			return Optional.ofNullable(
					mapper.map(resturantRepository.findById(Long.parseLong(resturantId)), ResturantDTOResponse.class));

		} else {
			logger.error("Resturant Details not found for Id: {}", resturantId);
			throw new ResturantNotFoundException("Resturant Details not found for Id: " + resturantId);
		}
	}

	@Override
	public Optional<ResturantDTOResponse> verifyResturant(String id) {
		Optional<Resturant> resturant = resturantRepository.findById(Long.parseLong(id));
		if (resturant.isEmpty())
			throw new ResturantNotFoundException("Resturant Details not found");
		try {
			resturant.get().setIsVerified(RESTURANT_VERIFICATION_STATUS.VERIFIED);
			resturantRepository.save(resturant.get());
			return Optional.ofNullable(mapper.map(resturant, ResturantDTOResponse.class));
		} catch (Exception ex) {
			throw new ResturantException("Failed to Perform Delete Operation");
		}
	}

	@Override
	public Optional<List<ResturantDTOResponse>> fetchAvailableResturant() {
		List<Resturant> resturantList = resturantRepository.findAll();
		if (resturantList.size() > 0) {
			logger.info("Fetching All resturant Details from records ");
			resturantList = resturantList.stream()
					.filter(rst -> rst.getIsVerified().equals(RESTURANT_VERIFICATION_STATUS.VERIFIED)
							&& rst.getOpenTime().compareTo(LocalTime.now()) < 0
							&& rst.getCloseTime().compareTo(LocalTime.now()) > 0)
					.collect(Collectors.toList());
			return Optional.ofNullable(Arrays.asList(mapper.map(resturantList, ResturantDTOResponse[].class)));
		} else {
			logger.error("No resturant records available");
			return Optional.empty();
		}
	}
}
