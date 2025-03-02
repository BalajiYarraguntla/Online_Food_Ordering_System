package com.deloitte.OrderService.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.OrderService.dto.OrderDTO;
import com.deloitte.OrderService.dto.UserDTOResponse;
import com.deloitte.OrderService.dto.response.OrderDTOResponse;
import com.deloitte.OrderService.entity.OrderDetails;
import com.deloitte.OrderService.enums.ORDER_STATUS;
import com.deloitte.OrderService.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Operation(summary = "Create Order")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Order Registered", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTOResponse.class)) }),
			@ApiResponse(responseCode = "409", description = "Order Details already exists", content = @Content),
			@ApiResponse(responseCode = "400", description = "Missing field in Request Body", content = @Content) })
	@PostMapping("/create")
	public ResponseEntity<OrderDTOResponse> createOrder(@Valid @RequestBody OrderDTO order) {
		Optional<OrderDTOResponse> OrderReponse = orderService.create(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(OrderReponse.get());
	}

	@Operation(summary = "Get Order Details by Resturant ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order Successfully Found by resturant Id", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTOResponse[].class)) }) })
	@GetMapping("/resturant/{id}")
	public ResponseEntity<List<OrderDTOResponse>> fetchOrder(@PathVariable String id) {
		return ResponseEntity.ok(orderService.fetchOrderByResturantId(id).get());
	}

	@Operation(summary = "Get Order Details by User ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order Successfully Found by user Id", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTOResponse[].class)) }),
			@ApiResponse(responseCode = "404", description = "Order Detail not available", content = @Content) })
	@GetMapping("/user/{id}")
	public ResponseEntity<List<OrderDTOResponse>> fetchOrderByUserId(@PathVariable String id) {
		return ResponseEntity.ok(orderService.fetchOrderByUserId(id).get());
	}

	@Operation(summary = "Get Order Details by User ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order Successfully Found by user Id", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTOResponse[].class)) }),
			@ApiResponse(responseCode = "404", description = "Order Detail not available", content = @Content) })
	@GetMapping("/{orderid}")
	public ResponseEntity<OrderDTOResponse> fetchOrderByOrderId(@PathVariable("orderid") String orderId) {
		return ResponseEntity.ok(orderService.trackOrderByUserId(orderId).get());
	}
	
	@Operation(summary = "Update Delivery Order Status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order Successfully Found by user Id", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTOResponse[].class)) }),
			@ApiResponse(responseCode = "404", description = "Order Detail not available", content = @Content) })
	@PatchMapping("/{orderid}")
	public ResponseEntity<OrderDTOResponse> updateOrderByOrderId(@PathVariable("orderid") String orderId,@PathParam("STATUS") String status) {
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status).get());
	}

	@Operation(summary = "Cancal Order by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Order Details delete Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTOResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Order Detail not available", content = @Content),
			@ApiResponse(responseCode = "403", description = "Invalid Authorization Token", content = @Content), })
	@DeleteMapping("/{id}")
	public ResponseEntity<OrderDTOResponse> cancelOrder(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.deleteOrder(id).get());
	}

}
