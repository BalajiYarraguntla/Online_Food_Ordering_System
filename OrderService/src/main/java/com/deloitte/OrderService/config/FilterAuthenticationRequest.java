package com.deloitte.OrderService.config;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deloitte.OrderService.dto.UserDTOResponse;
import com.deloitte.OrderService.exception.MissingAuthorizationTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterAuthenticationRequest extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(FilterAuthenticationRequest.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.gateway.url}")
	private String gateWayUrl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getServletPath().startsWith("/swagger-ui")) {
			filterChain.doFilter(request, response);
			return;
		}

		Optional<String> authenticationToken = Optional.ofNullable(request.getHeader("token"));
		if (authenticationToken.isPresent()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + authenticationToken.get());

			HttpEntity<String> httpReq = new HttpEntity<String>(headers);

			Optional<UserDTOResponse> val = Optional
					.ofNullable(restTemplate.postForObject(gateWayUrl + "/user/auth", httpReq, UserDTOResponse.class));

			if (val.isEmpty()) {
				logger.error("Missing Authorization token in header");
				throw new MissingAuthorizationTokenException("Missing Authorization token");
			}
			UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(val, null,
					val.get().getRoles());
			userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(userToken);
			logger.info("Sucessfully Authenticated user");
			filterChain.doFilter(request, response);

		} else {
			logger.error("Missing Authorization token");
			throw new MissingAuthorizationTokenException("Missing Authorization token");
		}
	}
}