package com.person.crud.domain.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.person.crud.domain.entity.Person;
import com.person.crud.domain.exception.CpfCnpjAlreadyExistsException;
import com.person.crud.domain.repository.AddressRepository;
import com.person.crud.domain.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	AddressRepository addressRepository;

	public Person findById(Long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("No Person with ID %d was found.", id)));
	}

	public Person save(Person person) {
		if (personRepository.findBycpfCnpj(person.getCpfCnpj()) != null) {
			throw new CpfCnpjAlreadyExistsException("CNPJ/CPF already registered.");
		}

		return personRepository.save(person);
	}

	public Person update(Long id, Person personUpdated) {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("No Person with ID %d was found.", id)));

		BeanUtils.copyProperties(personUpdated, person, "id");

		return save(person);
	}

	public void delete(Long id) {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("No Person with ID %d was found.", id)));

		personRepository.delete(person);
	}
}
