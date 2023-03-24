package com.person.crud.domain.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.person.crud.domain.entity.Address;
import com.person.crud.domain.entity.Person;
import com.person.crud.domain.repository.AddressRepository;
import com.person.crud.domain.repository.PersonRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PersonRepository personRepository;

	public Address findById(Long id) {
		return addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("No Address with ID %d was found.", id)));
	}

	public List<Address> findByPersonId(Long personId) {
		List<Address> addresses = addressRepository.findAllByPersonId(personId);

		if (addresses.isEmpty()) {
			throw new EntityNotFoundException(String.format("No Address was found to Person ID %d", personId));
		}

		return addresses;
	}

	public Address save(Address address) {
		Long personId = address.getPerson().getId();

		Person person = personRepository.findById(personId).orElseThrow(
				() -> new EntityNotFoundException(String.format("No Person was found to Person ID %d", personId)));

		address.setPerson(person);

		return addressRepository.save(address);
	}

	public Address update(Long id, Address addressUpdated) {
		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("No Address was found to ID %d", id)));

		BeanUtils.copyProperties(addressUpdated, address, "id");

		return save(address);

	}

	public void delete(Long id) {
		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("No Address with ID %d was found.", id)));

		addressRepository.delete(address);
	}
}
