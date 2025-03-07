package com.deloitte.AuthenticationService.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deloitte.AuthenticationService.enums.GENDER;
import com.deloitte.AuthenticationService.enums.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_details")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private USER_ROLE roles;

	@Column(nullable = false)
	private String firstName;

	@Column
	private String middleName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private GENDER gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dob;

	@Column
	@CreationTimestamp
	private Instant createdAt;

	@Column(unique = true, nullable = false)
	@Email(message = "Please enter a valid Email ")
	private String email;

	@Column(unique = true, nullable = false)
	@Size(min = 10, max = 10, message = "Please enter a valid Phone Number of 10 digits")
	private String phoneNumber;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	public User() {
	}

	public User(long id, String password, USER_ROLE roles, String firstName, String middleName, String lastName,
			GENDER gender, LocalDate dob, Instant createdAt,
			@Email(message = "Please enter a valid Email ") String email,
			@Size(min = 10, max = 10, message = "Please enter a valid Phone Number of 10 digits") String phoneNumber,
			Address address) {
		super();
		this.id = id;
		this.password = password;
		this.roles = roles;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.createdAt = createdAt;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Stream.of(getRoles()).map(str -> "ROLE_" + str).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + email + ", password=" + password + ", roles=" + roles + "]";
	}

}
