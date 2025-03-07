package com.deloitte.AuthenticationService.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deloitte.AuthenticationService.service.UserService;
import com.deloitte.AuthenticationService.utility.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterAuthenticationRequest extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(FilterAuthenticationRequest.class);

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtToken;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		String authenticationToken;
		String jwtTokenString = null;

		try {
			if (request.getServletPath().startsWith("/user/register")
					|| request.getServletPath().startsWith("/user/login")
					|| request.getServletPath().startsWith("/swagger-ui")) {
				logger.debug("Accessing public endpoints: {}", request.getServletPath());
				filterChain.doFilter(request, response);
				return;
			} else {
				authenticationToken = request.getHeader("Authorization");
				if (authenticationToken != null && authenticationToken.startsWith("Bearer")) {
					jwtTokenString = authenticationToken.substring(7);
					logger.debug("Validating  Authorization Token {}", jwtTokenString);
					UserDetails userDetail = userService
							.loadUserByUsername(jwtToken.getUsernameFromToken(jwtTokenString));
					if (userDetail != null && jwtToken.validateToken(jwtTokenString, userDetail)) {
						UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
								userDetail, null, userDetail.getAuthorities());
						userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(userToken);
						logger.info("Sucessfully Authenticated User with Authorization Token");
						filterChain.doFilter(request, response);
						return;
					} else {
						logger.error(
								"Error Occured while Authenticating User Details with Authorization Token passed ");
						HttpServletResponse httpResponse = (HttpServletResponse) response;
						httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
								"Required headers not specified in the request");
					}
				} else {
					logger.error("Error Occured while Authenticating User Details with Authorization Token passed ");
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Required headers not specified in the request");
				}
			}
		} catch (Exception e) {
			logger.error("Error Occured while Authenticating User Details with Authorization Token passed ");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error with Authorization Token passed");
		}
	}
}
