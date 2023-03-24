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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.person.crud.domain.entity.Person;
import com.person.crud.domain.exception.CpfCnpjAlreadyExistsException;
import com.person.crud.domain.repository.PersonRepository;
import com.person.crud.domain.service.PersonService;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PersonService personService;

	@GetMapping
	private List<Person> findAll() {
		return personRepository.findAll();
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			Person person = personService.findById(id);

			return ResponseEntity.ok(person);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private ResponseEntity<?> create(@RequestBody Person person) {
		try {
			person = personService.save(person);
			return ResponseEntity.status(HttpStatus.CREATED).body(person);

		} catch (CpfCnpjAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable Long id, @RequestBody Person person) {
		try {
			Person personUpdated = personService.update(id, person);
			return ResponseEntity.ok(personUpdated);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (CpfCnpjAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			personService.delete(id);
			return ResponseEntity.ok().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
