package com.deloitte.ResturantService.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.ResturantService.dto.FoodItemDTO;
import com.deloitte.ResturantService.dto.ResturantDTO;
import com.deloitte.ResturantService.dto.response.ResturantDTOResponse;
import com.deloitte.ResturantService.dto.response.UserDTOResponse;
import com.deloitte.ResturantService.service.ResturantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/resturant")
@Validated
public class ResturantController {

	@Autowired
	private ResturantService resturantService;

	@Operation(summary = "Register Registurant")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Resturant Registered", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResturantDTOResponse.class)) }),
			@ApiResponse(responseCode = "409", description = "Resturant Details already exists", content = @Content),
			@ApiResponse(responseCode = "400", description = "Missing field in Request Body", content = @Content) })
	@PostMapping("/register")
	public ResponseEntity<ResturantDTOResponse> register(@Valid @RequestBody ResturantDTO resturant) {
		System.out.println("Registering...");
		Optional<ResturantDTOResponse> resturantReponse = resturantService.register(resturant);
		return ResponseEntity.status(HttpStatus.CREATED).body(resturantReponse.get());
	}

	@Operation(summary = "Get Resturant Details by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Resturant Successfully Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResturantDTOResponse.class)) }) })
	@GetMapping("/")
	public ResponseEntity<List<ResturantDTOResponse>> fetchResturant() {
		return ResponseEntity.ok(resturantService.fetchResturant().get());
	}

	@Operation(summary = "Get Resturant Details by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Resturant Successfully Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResturantDTOResponse.class)) }) })
	@GetMapping("/available")
	public ResponseEntity<List<ResturantDTOResponse>> fetchAvailableResturant() {
		return ResponseEntity.ok(resturantService.fetchAvailableResturant().get());
	}
	
	@Operation(summary = "Get Resturant Details by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Resturant Successfully Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResturantDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resturant Detail not available", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<ResturantDTOResponse> fetchResturantById(@PathVariable String id) {
		return ResponseEntity.ok(resturantService.fetchResturant(id).get());
	}

	@Operation(summary = "Update Resturant details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resturant Details updated Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resturant Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@PatchMapping("/{id}")
	public ResponseEntity<ResturantDTOResponse> updateResturant(@PathVariable String id,
			@RequestBody Map<String, Object> resturant) {
		return ResponseEntity.ok(resturantService.updateResturant(id, resturant).get());
	}

	@Operation(summary = "Update Resturant Inventory details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resturant Inventory Details updated Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resturant Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@PatchMapping("/{id}/inventory/{itemId}")
	public ResponseEntity<ResturantDTOResponse> updateResturant(@PathVariable String id,
			@RequestBody FoodItemDTO resturant, @PathVariable("itemId") String itemId) {
		return ResponseEntity.ok(resturantService.updateResturantInventory(id, resturant, itemId).get());
	}

	@Operation(summary = "Create Resturant Inventory details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resturant Inventory Details updated Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resturant Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@PostMapping("/{id}/inventory")
	public ResponseEntity<ResturantDTOResponse> addInventoryResturantBy(@PathVariable String id,
			@RequestBody FoodItemDTO resturant) {
		return ResponseEntity.ok(resturantService.addResturantInventory(id, resturant).get());
	}

	@Operation(summary = "Delete Resturant by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Resturant Details delete Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resturant Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@DeleteMapping("/{id}")
	public ResponseEntity<ResturantDTOResponse> deleteResturant(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resturantService.deleteResturant(id).get());
	}

	@Operation(summary = "Delete Resturant by User Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Resturant Details delete Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resturant Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@DeleteMapping("/user/{id}")
	public ResponseEntity<ResturantDTOResponse> deleteResturantByUserId(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resturantService.deleteResturantByUserId(id).get());
	}

	@Operation(summary = "Verify Resturant Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Resturant Successfully Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResturantDTOResponse.class)) }) })
	@PatchMapping("/verify/{id}")
	public ResponseEntity<ResturantDTOResponse> verifyResturant(@PathVariable String id) {
		return ResponseEntity.ok(resturantService.verifyResturant(id).get());
	}

}
