package com.deloitte.AuthenticationService.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.deloitte.AuthenticationService.enums.GENDER;
import com.deloitte.AuthenticationService.enums.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO{
	
	@NotNull(message = "Password : Field cannot be blank")
	private String password;

	@NotNull(message = "Roles : Field cannot be blank")
	private USER_ROLE roles;

	@NotNull(message = "First Name : Field cannot be blank")
	private String firstName;

	private String middleName;

	@NotNull(message = "Last Name : Field cannot be blank")
	private String lastName;

	@NotNull(message = "Gender : Field cannot be blank")
	private GENDER gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull(message = "Date of Birth : Field cannot be blank")
	private LocalDate dob;

	@Email(message = "Please enter a valid Email ", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	@NotNull(message = "Email : Field cannot be blank")
	private String email;

	@Size(min = 10, max = 10, message = "Please enter a valid Phone Number of 10 digits")
	@NotNull(message = "Phone Number : Field cannot be blank")
	private String phoneNumber;

	@Valid
	private AddressDTO address;

	private Instant accountCreatedAt;

	public Instant getAccountCreatedAt() {
		return accountCreatedAt;
	}

	public void setAccountCreatedAt(Instant accountCreatedAt) {
		this.accountCreatedAt = accountCreatedAt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public GENDER getGender() {
		return gender;
	}

	public void setGender(GENDER gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public USER_ROLE getRoles() {
		return roles;
	}

	public void setRoles(USER_ROLE roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDTO [password=" + password + ", roles=" + roles + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", gender=" + gender + ", dob=" + dob + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}

}
