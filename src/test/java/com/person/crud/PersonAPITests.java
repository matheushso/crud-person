package com.person.crud;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.person.crud.domain.entity.Person;

import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonAPITests {

	@LocalServerPort
	private Integer port;

	private String nameCompany = "Marie-ann Willshee";
	private String email = "mwillshee0@pbs.org";
	private String cpfCnpj = "33416960359";

	private Person person = Person.builder()
			.nameCompany(nameCompany)
			.email(email)
			.cpfCnpj(cpfCnpj)
			.build();
	
	@Test
	public void shouldSucceedWhenConsultPersons() {
		given()
			.basePath("/api/v1/persons")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("[0].nameCompany", is(nameCompany))
				.body("[0].email", is(email))
				.body("[0].cpfCnpj", is(cpfCnpj));
	}
	
	@Test
	public void shouldSucceedWhenConsultPersonById() {
		given()
			.pathParam("id", 1)
			.basePath("/api/v1/persons/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("nameCompany", is(nameCompany))
				.body("email", is(email))
				.body("cpfCnpj", is(cpfCnpj));
	}
	
	@Test
	public void shouldFaileddWhenConsultPersonByIdDoesNotExist() {
		given()
			.pathParam("id", 1009)
			.basePath("/api/v1/addresses/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldSucceedWhenCreatedPerson() {
		Person personCreated = person;
		personCreated.setCpfCnpj("123456789-00");
		
		given()
			.basePath("/api/v1/persons")
			.port(port)
			.body(personCreated)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldFailedWhenCreatedPersonWithCpfCnpjDuplicated() {
		given()
			.basePath("/api/v1/persons")
			.port(port)
			.body(person)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void shouldSucceedWhenUpdatedPerson() {
		Person personUpdated = person;
		personUpdated.setCpfCnpj("987654321-00");
		
		given()
			.pathParam("id", 2)
			.basePath("/api/v1/persons/{id}")
			.port(port)
			.body(personUpdated)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("nameCompany", is(personUpdated.getNameCompany()))
				.body("email", is(personUpdated.getEmail()))
				.body("cpfCnpj", is(personUpdated.getCpfCnpj()));
	}
	
	@Test
	public void shouldFailedWhenUpdatedPersonWithNotExistId() {
		given()
			.pathParam("id", 1009)
			.basePath("/api/v1/persons/{id}")
			.port(port)
			.body(person)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldSuccedWhenDeletedPerson() {
		given()
			.pathParam("id", 10)
			.basePath("/api/v1/persons/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.delete()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldFailedWhenDeletedAddressWithNotExistId() {
		given()
			.pathParam("id", 1009)
			.basePath("/api/v1/persons/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.delete()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
