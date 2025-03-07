package com.deloitte.AuthenticationService.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String address1;

	@Column
	private String address2;

	@Column
	private String landMark;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String pincode;

	@OneToOne(mappedBy = "address")
	private User user;

	public Address() {

	}

	public Address(long id, String address1, String address2, String landMark, String city, String state,
			String pincode) {
		super();
		this.id = id;
		this.address1 = address1;
		this.address2 = address2;
		this.landMark = landMark;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", address1=" + address1 + ", address2=" + address2 + ", landMark=" + landMark
				+ ", city=" + city + ", state=" + state + ", pincode=" + pincode + "]";
	}

}
