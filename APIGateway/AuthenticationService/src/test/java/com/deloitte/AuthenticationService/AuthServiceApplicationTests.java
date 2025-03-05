package com.deloitte.AuthenticationService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.deloitte.AuthenticationService.entity.Address;
import com.deloitte.AuthenticationService.entity.User;
import com.deloitte.AuthenticationService.enums.GENDER;
import com.deloitte.AuthenticationService.enums.USER_ROLE;
import com.deloitte.AuthenticationService.exception.UserNotFoundException;
import com.deloitte.AuthenticationService.repository.UserServiceRepository;
import com.deloitte.AuthenticationService.service.serviceImpl.UserServiceImpl;

@SpringBootTest
public class AuthServiceApplicationTests {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserServiceRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void fetchUserById() throws UserNotFoundException {
		when(userRepository.findById(1L)).thenReturn(createUserObject());
		User testedUser = userService.fetchUserDetails(2L);
		assertEquals(testedUser.getFirstName(), "Souvik");
		assertEquals(testedUser.getLastName(), "Mukherjee");
	}

	@Test
	public void fetchUserByIdWhenNotExist() throws UserNotFoundException {
		when(userRepository.findById(1L)).thenReturn(createUserObject());
		assertThatThrownBy(() -> userService.fetchUserDetails(2L)).isInstanceOf(UserNotFoundException.class);
	}

	private Optional<User> createUserObject() {
		Address address = new Address(1L, "42/A Halishahar", "", "", "kolkata", "WB", "70038");
		User user = new User(1L, "123", USER_ROLE.ADMIN, "Souvik", "", "Mukherjee", GENDER.MALE, LocalDate.now(),
				Instant.now(), "souvik@gmail.com", "9123313655", address);
		return Optional.of(user);
	}

}
