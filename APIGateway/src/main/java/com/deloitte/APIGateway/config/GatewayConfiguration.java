package com.deloitte.APIGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.deloitte.APIGateway.config.AuthFilter.Config;

@Configuration
public class GatewayConfiguration {
//Author: Balaji Yarraguntla
	@Autowired
	private AuthFilter authFilter;

	@Value("${service.authentication.name}")
	private String authenticationUrl;

	@Value("${service.resturant.name}")
	private String resturantServiceUrl;

	@Value("${service.order.name}")
	private String orderServiceUrl;

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

		Config config = new Config();

		return routeLocatorBuilder.routes().route("order-service", route -> route.path("/order/**").filters(filter -> {
			filter.filter(authFilter.apply(config));
			return filter;
		}).uri("lb://" + orderServiceUrl)).route("auth-service", route -> route.path("/user/**").filters(filter -> {
			return filter;
		}).uri("lb://" + authenticationUrl)).route("resturant-service",
				route -> route.path("/resturant/**").and().method(HttpMethod.GET).filters(filter -> {
					return filter;
				}).uri("lb://" + resturantServiceUrl))
				.route("resturant-service", route -> route.path("/resturant/**").filters(filter -> {
					filter.filter(authFilter.apply(config));
					return filter;
				}).uri("lb://" + resturantServiceUrl)).build();
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}