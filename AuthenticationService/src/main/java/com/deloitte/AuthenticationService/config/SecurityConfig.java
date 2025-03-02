package com.deloitte.AuthenticationService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.deloitte.AuthenticationService.enums.USER_ROLE;
import com.deloitte.AuthenticationService.service.UserService;

@Configuration
public class SecurityConfig {

	@Autowired
	private UserService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers("swagger-ui/**", "/user/login", "/user/register")
						.permitAll().requestMatchers("/user/**").hasAnyRole(USER_ROLE.ADMIN.toString(),
								USER_ROLE.CUSTOMER.toString(), USER_ROLE.RESTURANT_OWNER.toString())
						.anyRequest().authenticated());

		return http.addFilterBefore(authenticationRequest(), UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;

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
