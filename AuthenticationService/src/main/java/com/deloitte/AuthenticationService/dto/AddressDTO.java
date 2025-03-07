package com.deloitte.AuthenticationService.dto;

import jakarta.validation.constraints.NotNull;

public class AddressDTO {

	@NotNull(message = "Address1 : Field cannot be blank")
	private String address1;

	private String address2;

	private String landMark;

	@NotNull(message = "City : Field cannot be blank")
	private String city;

	@NotNull(message = "State : Field cannot be blank")
	private String state;

	@NotNull(message = "PinCode : Field cannot be blank")
	private String pincode;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "AddressDTO [address1=" + address1 + ", address2=" + address2 + ", landMark=" + landMark + ", city="
				+ city + ", state=" + state + ", pincode=" + pincode + "]";
	}

}
