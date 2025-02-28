package com.deloitte.AuthenticationService.controller;

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

import com.deloitte.AuthenticationService.dto.UserDTO;
import com.deloitte.AuthenticationService.dto.UserLoginDTO;
import com.deloitte.AuthenticationService.response.UserDTOResponse;
import com.deloitte.AuthenticationService.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class UserApiController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Register User")
	@PostMapping("/register")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created User", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "409", description = "User already exists", content = @Content),
			@ApiResponse(responseCode = "400", description = "Missing field in Request Body", content = @Content) })
	public ResponseEntity<UserDTOResponse> registerUser(@NotNull @Valid @RequestBody UserDTO userDetails) {
		System.out.println("tests...");
		Optional<UserDTOResponse> usr = userService.registerUserDetails(userDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(usr.get());
	}

	@Operation(summary = "Get User Details by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User Successfully Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "403", description = "Missing Authorization Token", content = @Content),
			@ApiResponse(responseCode = "404", description = "User Detail not available", content = @Content),
			@ApiResponse(responseCode = "401", description = "Invalid Credentials", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<UserDTOResponse> fetchUser(@PathVariable String id) {
		Optional<UserDTOResponse> usr = userService.fetchUserDetails(id);
		return ResponseEntity.ok(usr.get());
	}

	@Operation(summary = "Login using User Credentials")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User is Authenticated Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "User Detail not available", content = @Content),
			@ApiResponse(responseCode = "401", description = "Invalid Credentials", content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<String> login(@NotNull @Valid @RequestBody UserLoginDTO userDetails) {
		Optional<String> token = userService.getUserDetails(userDetails);
		return ResponseEntity.ok(token.get());
	}

	@Operation(summary = "Validate User Authentication Token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Details Successfully Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content),
			@ApiResponse(responseCode = "401", description = "Invalid Credentials", content = @Content) })
	@PostMapping("/auth")
	public ResponseEntity<UserDTOResponse> validate(@RequestHeader("Authorization") String token) {
		Optional<UserDTOResponse> user = userService.validatateToken(token.substring(7));
		return ResponseEntity.ok(user.get());
	}

	@Operation(summary = "Update User details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Details updated Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "User Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@PatchMapping("/{id}")
	public ResponseEntity<UserDTOResponse> updateUser(@PathVariable String id,
			@RequestBody Map<String, Object> userDetails) {
		Optional<UserDTOResponse> user = userService.updateUser(id, userDetails);
		return ResponseEntity.ok(user.get());
	}

	@Operation(summary = "Delete User by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "User Details delete Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "User Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTOResponse> deleteUser(@PathVariable String id) {
		Optional<UserDTOResponse> user = userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(user.get());
	}
}
