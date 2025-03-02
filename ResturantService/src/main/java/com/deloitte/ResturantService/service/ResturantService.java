package com.deloitte.ResturantService.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.deloitte.ResturantService.dto.FoodItemDTO;
import com.deloitte.ResturantService.dto.ResturantDTO;
import com.deloitte.ResturantService.dto.response.FoodItemMenuDTOResponse;
import com.deloitte.ResturantService.dto.response.ResturantDTOResponse;

public interface ResturantService {

	Optional<ResturantDTOResponse> register(ResturantDTO resturant);

	Optional<List<ResturantDTOResponse>> fetchResturant();
	
	Optional<ResturantDTOResponse> fetchResturant(String id);

	Optional<ResturantDTOResponse> updateResturant(String id, Map<String, Object> resturant);

	Optional<ResturantDTOResponse> deleteResturant(String id);

	Optional<ResturantDTOResponse> deleteResturantByUserId(String id);

	Optional<List<FoodItemMenuDTOResponse>> fetchAllMenuByResturantId(String id);

	Optional<ResturantDTOResponse> updateResturantInventory(String resturantId, FoodItemDTO resturant, String itemId);

	Optional<ResturantDTOResponse> verifyResturant(String id);

	Optional<ResturantDTOResponse> addResturantInventory(String resturantId, FoodItemDTO itemDTO);

	Optional<List<ResturantDTOResponse>> fetchAvailableResturant();

}
