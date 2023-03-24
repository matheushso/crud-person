package com.person.crud.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.person.crud.domain.entity.Address;
import com.person.crud.domain.repository.AddressRepository;
import com.person.crud.domain.service.AddressService;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressService addressService;

	@GetMapping
	private List<Address> findAll() {
		return addressRepository.findAll();
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			Address address = addressService.findById(id);

			return ResponseEntity.ok(address);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/byPersonId/{personId}")
	private ResponseEntity<?> findByPersonId(@PathVariable Long personId) {
		try {
			List<Address> address = addressService.findByPersonId(personId);

			return ResponseEntity.ok(address);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	private ResponseEntity<?> create(@RequestBody Address address) {
		try {
			Address addressCreated = addressService.save(address);

			return ResponseEntity.status(HttpStatus.CREATED).body(addressCreated);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	private ResponseEntity<?> updatePrincipalAddress(@PathVariable Long id, @RequestBody Address address) {
		try {
			address = addressService.update(id, address);

			return ResponseEntity.ok(address);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			addressService.delete(id);
			return ResponseEntity.ok().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
