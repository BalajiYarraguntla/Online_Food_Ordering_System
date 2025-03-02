package com.deloitte.OrderService.service;

import java.util.List;
import java.util.Optional;

import com.deloitte.OrderService.dto.OrderDTO;
import com.deloitte.OrderService.dto.response.OrderDTOResponse;
import com.deloitte.OrderService.enums.ORDER_STATUS;

import jakarta.validation.Valid;

public interface OrderService {

	Optional<OrderDTOResponse> create(@Valid OrderDTO order);

	Optional<List<OrderDTOResponse>> fetchOrderByResturantId(String id);

	Optional<List<OrderDTOResponse>> fetchOrderByUserId(String id);

	Optional<OrderDTOResponse> deleteOrder(String id);

	Optional<OrderDTOResponse> trackOrderByUserId(String id);

	Optional<OrderDTOResponse> updateOrderStatus(String orderId, String status);

}
