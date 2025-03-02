package com.deloitte.AuthenticationService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloitte.AuthenticationService.entity.Address;

public interface AddressServiceRepository extends JpaRepository<Address, Long> {

}
