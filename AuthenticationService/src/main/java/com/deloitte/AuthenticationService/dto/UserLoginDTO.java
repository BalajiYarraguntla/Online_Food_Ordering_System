package com.deloitte.AuthenticationService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserLoginDTO {

	@Email(message = "Please enter a valid Email ", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	@NotNull(message = "Email : Field cannot be blank")
	private String email;

	@NotNull(message = "Password : Field cannot be blank")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
