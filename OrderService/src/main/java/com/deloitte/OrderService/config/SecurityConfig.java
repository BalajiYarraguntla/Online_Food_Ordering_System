package com.deloitte.OrderService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(
				(requests) -> requests.requestMatchers("swagger-ui/**").permitAll().anyRequest().authenticated());

		return http.addFilterBefore(authenticationRequest(), UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	FilterAuthenticationRequest authenticationRequest() {
		return new FilterAuthenticationRequest();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
