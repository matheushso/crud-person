package com.person.crud.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.person.crud.domain.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	Person findBycpfCnpj(String cpfCnpj);
}
