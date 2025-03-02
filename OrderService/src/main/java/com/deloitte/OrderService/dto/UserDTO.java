package com.deloitte.OrderService.dto;

public class UserDTO {

	private Long id;
	private String email;
	private String roles;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", roles=" + roles + "]";
	}
	
	
}
