package com.deloitte.AuthenticationService.response;

import java.util.Date;

import com.deloitte.AuthenticationService.dto.AddressDTO;
import com.deloitte.AuthenticationService.enums.GENDER;
import com.deloitte.AuthenticationService.enums.USER_ROLE;

public class UserDTOResponse {

	private long id;
	
	private USER_ROLE roles;

	private String firstName;

	private String middleName;

	private String lastName;

	private GENDER gender;

	private Date dob;

	private String email;

	private String phoneNumber;

	private AddressDTO address;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
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

	public USER_ROLE getRoles() {
		return roles;
	}

	public void setRoles(USER_ROLE roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDTO [roles=" + roles + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", gender=" + gender + ", dob=" + dob + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}

	
}
