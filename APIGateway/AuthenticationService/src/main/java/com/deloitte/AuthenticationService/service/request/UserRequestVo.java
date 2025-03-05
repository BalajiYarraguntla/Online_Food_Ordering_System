package com.deloitte.AuthenticationService.service.request;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deloitte.AuthenticationService.entity.Address;
import com.deloitte.AuthenticationService.enums.GENDER;
import com.deloitte.AuthenticationService.enums.USER_ROLE;


public class UserRequestVo implements UserDetails {
	

		private static final long serialVersionUID = 1L;

		
		private long id;

		
		private String password;

		
		private USER_ROLE roles;


		private String firstName;

	
		private String middleName;

		
		private String lastName;

	
		private GENDER gender;

	
		private LocalDate dob;

		private Instant createdAt;

		private String email;

		private String phoneNumber;


		private Address address;

		

		
		public USER_ROLE getRoles() {
			return roles;
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

		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}



