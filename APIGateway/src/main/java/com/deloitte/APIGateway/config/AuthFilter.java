package com.deloitte.APIGateway.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deloitte.APIGateway.dto.UserDTOResponse;
import com.deloitte.APIGateway.exception.MissingAuthorizationTokenException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

	Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Value("${service.authentication.url}")
	private String authenticationUrl;
	
	@Autowired
	private RestTemplate template;

	public static class Config {

	}

	public AuthFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request;
			Optional<String> header = Optional
					.ofNullable(exchange.getRequest().getHeaders().get("Authorization").get(0));
			if (header.isPresent()) {
				String token = header.get().substring(7);
				logger.info("Sucessfully passed Authorization token {}", token);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Authorization", "Bearer " + token);

				HttpEntity<String> httpRequest = new HttpEntity<String>(headers);

				Optional<UserDTOResponse> val = Optional.ofNullable(
						template.postForObject(authenticationUrl+"/user/auth", httpRequest, UserDTOResponse.class));

				if (val.isEmpty()) {
					logger.error("Missing Authorization token in header");
					throw new RuntimeException("Missing Authorization token");
				}
				logger.info("Sucessfully Authenticated user");
				request = exchange.getRequest().mutate().header("token", token).build();
				return chain.filter(exchange.mutate().request(request).build());

			} else {
				logger.error("Missing Authorization token");
				throw new RuntimeException("Missing Authorization token");
			}
		});

	}
}
