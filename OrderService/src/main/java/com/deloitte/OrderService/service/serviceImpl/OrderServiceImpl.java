package com.deloitte.OrderService.service.serviceImpl;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deloitte.OrderService.dto.FoodItemsDTO;
import com.deloitte.OrderService.dto.OrderDTO;
import com.deloitte.OrderService.dto.UserDTO;
import com.deloitte.OrderService.dto.response.OrderDTOResponse;
import com.deloitte.OrderService.dto.response.ResturantDTOResponse;
import com.deloitte.OrderService.entity.OrderDetails;
import com.deloitte.OrderService.enums.ORDER_STATUS;
import com.deloitte.OrderService.exception.InvalidCredentialsException;
import com.deloitte.OrderService.exception.OrderCreationException;
import com.deloitte.OrderService.exception.OrderException;
import com.deloitte.OrderService.exception.OrderNotFoundException;
import com.deloitte.OrderService.exception.ResturantNotFoundException;
import com.deloitte.OrderService.repository.OrderServiceRepository;
import com.deloitte.OrderService.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Value("${service.gateway.url}")
	private String apiUrl;

	@Autowired
	private OrderServiceRepository orderRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Optional<OrderDTOResponse> create(OrderDTO order) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);
		OrderDetails orderDetails = mapper.map(order, OrderDetails.class);
		orderDetails.setOrderStatus(ORDER_STATUS.PLACED);
		orderDetails.setUser(userTemp.getId());
		Optional<ResturantDTOResponse> resturantDTOResponse = Optional.ofNullable(
				restTemplate.getForObject(apiUrl + "/resturant/" + order.getResturant(), ResturantDTOResponse.class));
		Optional<List<OrderDetails>> orderList = orderRepository.findAllByResturant(order.getResturant());
		Long orderCount = orderList.get().stream().filter(ordr -> ordr.getOrderStatus().equals(ORDER_STATUS.PLACED))
				.count();
		Map<Long, FoodItemsDTO> map = resturantDTOResponse.get().getFoodItems().stream()
				.filter(item -> item.getStatus().equalsIgnoreCase("AVAILABLE"))
				.collect(Collectors.toMap(FoodItemsDTO::getId, item -> item));

		if (resturantDTOResponse.isPresent() && userTemp.getId() != null) {
			if (orderCount >= resturantDTOResponse.get().getOrderCapacity()) {
				logger.error("Order Details Exceeds fot this Resturant");
				throw new OrderCreationException("Order Details Exceeds fot this Resturant");
			}
			try {
				orderDetails.getOrderSummary().stream().forEach(rst -> {
					Double discountedPrice = map.get(rst.getFoodItemIdCode()).getDiscountedPrice();
					Double originalPrice = map.get(rst.getFoodItemIdCode()).getPrice();
					Double discountedAmount = originalPrice * (1 - discountedPrice * 0.01);
					rst.setDiscountPercantage(Math.ceil(discountedPrice));
					rst.setOriginalItemPrice(Math.ceil(originalPrice));
					rst.setTotalItemPrice(Math.ceil(discountedAmount));
					rst.setDiscountedItemPrice(Math.ceil(originalPrice - discountedAmount));
					rst.setOrder(orderDetails);
				});

				double discountedCost = orderDetails.getOrderSummary().stream()
						.map(detail -> detail.getTotalItemPrice() * detail.getQuantity())
						.mapToDouble(i -> i.doubleValue()).sum();
				double orginalCost = orderDetails.getOrderSummary().stream()
						.map(detail -> detail.getOriginalItemPrice() * detail.getQuantity())
						.mapToDouble(i -> i.doubleValue()).sum();

				orderDetails.setResturant(resturantDTOResponse.get().getId());
				orderDetails.setTotalCost(Math.ceil(discountedCost));
				orderDetails.setOriginalCost(Math.ceil(orginalCost));
			} catch (Exception e) {
				logger.error("Menu Item OUT_OF_STOCK for this Returant");
				throw new OrderCreationException("Menu Item OUT_OF_STOCK for this Returant");
			}
			OrderDetails order1 = orderRepository.save(orderDetails);
			logger.info("Order is successfully created in records: {}", orderDetails.getId());
			return Optional.ofNullable(mapper.map(order1, OrderDTOResponse.class));
		} else {
			logger.error("Error Occured: Order Details already exists");
			throw new ResturantNotFoundException("Resturant Not Found with Id:\t" + order.getResturant());
		}
	}

	@Override
	public Optional<List<OrderDTOResponse>> fetchOrderByResturantId(String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);
		Optional<ResturantDTOResponse> resturantDTOResponse = Optional
				.ofNullable(restTemplate.getForObject(apiUrl + "/resturant/" + id, ResturantDTOResponse.class));

		if (userTemp.getId() == resturantDTOResponse.get().getUserId()
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			if (resturantDTOResponse.isPresent()) {
				Optional<List<OrderDetails>> orderList = orderRepository.findAllByResturant(Long.parseLong(id));
				if (orderList.isPresent()) {
					logger.info("Fetching All Order Details from records {}", orderList.get().toArray());
					return Optional.ofNullable(Arrays.asList(mapper.map(orderList.get(), OrderDTOResponse[].class)));
				} else {
					logger.error("No Order records available for this returant id: {}", id);
					throw new OrderNotFoundException("No Order records available for this returant id:\t" + id);
				}
			} else {
				logger.error("No Resturant found available with requested Id: {}", id);
				throw new OrderNotFoundException("No Resturant found available with requested Id: " + id);
			}
		} else {
			logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
	}

	@Override
	public Optional<List<OrderDTOResponse>> fetchOrderByUserId(String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);

		if (userTemp.getId() == Long.parseLong(id)
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			Optional<List<OrderDetails>> orderList = orderRepository.findAllByUser(Long.parseLong(id));
			if (orderList.isPresent()) {
				logger.info("Fetching All Order Details from records {}", orderList.get().toArray());
				return Optional.ofNullable(Arrays.asList(mapper.map(orderList.get(), OrderDTOResponse[].class)));
			} else {
				logger.error("No Order records available for this returant id: {}", id);
				throw new OrderNotFoundException("No Order records available for this returant id:\t" + id);
			}
		} else {
			logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
	}

	@Override
	public Optional<OrderDTOResponse> deleteOrder(String id) {
		Optional<OrderDetails> order = Optional.ofNullable(orderRepository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new OrderNotFoundException("Order Details not found")));
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);
			boolean isValidUser = (userTemp.getId() == order.get().getUser()
					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

			if (!isValidUser) {
				logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}",
						id);
				throw new InvalidCredentialsException(
						"Access Denied : User is not Authorized to perform this action with requested Id :" + id);

			}
			if (isValidUser && order.isPresent() && order.get().getOrderStatus().equals(ORDER_STATUS.PLACED)) {
				order.get().setOrderStatus(ORDER_STATUS.CANCELLED);
				orderRepository.save(order.get());
				logger.info("Order Details is deleted successfully with requested Id : {}", id);
			} else {
				logger.error("Cannot Cancel Order as the status is already : {}", order.get().getOrderStatus());
				throw new OrderException(
						"Cannot Cancel Order as the status is already :" + order.get().getOrderStatus());
			}
		} catch (Exception ex) {
			logger.error("Cannot Cancel Order as the status is already : {}", order.get().getOrderStatus());
			throw new OrderException("Cannot Cancel Order as the status is already :" + order.get().getOrderStatus());
		}
		return Optional.ofNullable(mapper.map(order, OrderDTOResponse.class));
	}

	@Override
	public Optional<OrderDTOResponse> trackOrderByUserId(String orderId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDTO userTemp = mapper.map(auth.getPrincipal(), UserDTO.class);
		Optional<List<OrderDTOResponse>> fetchAllOrderByUser = fetchOrderByUserId(String.valueOf(userTemp.getId()));
		if (fetchAllOrderByUser.isPresent()) {
			Optional<OrderDTOResponse> userOrder = fetchAllOrderByUser.get().stream()
					.filter(odr -> odr.getId() == Long.parseLong(orderId)).findAny();
			if (userOrder.isPresent()) {
				return userOrder;
			} else {
				logger.error("No Order found with this orderId: {} for the user {}", orderId, userTemp.getId());
				throw new OrderNotFoundException(
						"No Order found with this orderId:" + orderId + " for the user " + userTemp.getId());
			}
		} else {
			logger.error("No Order found with this orderId: {} for the user {}", orderId, userTemp.getId());
			throw new OrderNotFoundException(
					"No Order found with this orderId:" + orderId + " for the user " + userTemp.getId());
		}
	}

	@Override
	public Optional<OrderDTOResponse> updateOrderStatus(String id, String status) {
		Boolean isValidStatus = Arrays.stream(ORDER_STATUS.values()).anyMatch(e -> e.name().equals(status));
		if (!isValidStatus) {
			logger.error("WRONG Order Status recieved: {}", id);
			throw new OrderException("WRONG Order Status recieved for Id:" + id);
		}
		Optional<OrderDetails> order = Optional.ofNullable(orderRepository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new OrderNotFoundException("Order Details not found")));
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			boolean isValidUser = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_RESTURANT_OWNER"))
					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

			if (!isValidUser) {
				logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}",
						id);
				throw new InvalidCredentialsException(
						"Access Denied : User is not Authorized to perform this action with requested Id :" + id);

			}
			if (isValidUser && order.isPresent()) {
				order.get().setOrderStatus(ORDER_STATUS.valueOf(status));
				orderRepository.save(order.get());
				logger.info("Order Details is updated successfully with requested Id : {}", id);
			}
		} catch (Exception ex) {
			logger.error("Access Denied : User is not Authorized to perform this action with requested Id : {}", id);
			throw new InvalidCredentialsException(
					"Access Denied : User is not Authorized to perform this action with requested Id :" + id);
		}
		return Optional.ofNullable(mapper.map(order, OrderDTOResponse.class));
	}

//	@Override
//	public Optional<OrderDTOResponse> deleteOrderByUserId(String id) {
//		Optional<Order> Order = orderRepository.findByUserId(Long.parseLong(id));
//		if (Order.isEmpty())
//			throw new ("Order Details not found");
//		try {
//			orderRepository.delete(Order.get());
//		} catch (Exception ex) {
//			throw new OrderException("Failed to Perform Delete Operation");
//		}
//		return null;
//	}
}
