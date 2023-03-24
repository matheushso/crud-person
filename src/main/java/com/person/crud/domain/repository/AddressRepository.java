package com.person.crud.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.person.crud.domain.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findAllByPersonId(Long personId);
}
