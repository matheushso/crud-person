package com.person.crud;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.person.crud.domain.entity.Address;
import com.person.crud.domain.entity.Person;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressAPITests {

	@LocalServerPort
	private Integer port;

	private String nameCompany = "Case Yapp";
	private String email = "cyapprr@google.com.hk";
	private String cpfCnpj = "75513608390";
	private String addressName = "Street Address";
	private String zipCode = "11111-111";
	private int number = 123;
	private String city = "City";

	private Address address = Address.builder()
			.address(addressName)
			.zipCode(zipCode)
			.number(number)
			.city(city)
			.person(Person.builder().id(1000L).build())
			.build();
	
	@BeforeEach
	private void setUp() {
		createAddress();
	}
	
	@Test
	public void shouldSucceedWhenConsultAddresses() {
		given()
			.basePath("/api/v1/addresses")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("[0].address", is(addressName))
				.body("[0].zipCode", is(zipCode))
				.body("[0].number", is(number))
				.body("[0].city", is(city))
				.body("[0].person.nameCompany", is(nameCompany))
				.body("[0].person.email", is(email))
				.body("[0].person.cpfCnpj", is(cpfCnpj));
	}
	
	@Test
	public void shouldSucceedWhenConsultAddressById() {
		given()
			.pathParam("id", 1)
			.basePath("/api/v1/addresses/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("address", is(addressName))
				.body("zipCode", is(zipCode))
				.body("number", is(number))
				.body("city", is(city))
				.body("person.nameCompany", is(nameCompany))
				.body("person.email", is(email))
				.body("person.cpfCnpj", is(cpfCnpj));
	}
	
	@Test
	public void shouldFaileddWhenConsultAddressByIdDoesNotExist() {
		given()
			.pathParam("id", 9)
			.basePath("/api/v1/addresses/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldSucceedWhenConsultAddressPerPersonId() {
		given()
			.pathParam("personId", 1000)
			.basePath("/api/v1/addresses/byPersonId/{personId}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("[0].address", is(addressName))
				.body("[0].zipCode", is(zipCode))
				.body("[0].number", is(number))
				.body("[0].city", is(city))
				.body("[0].person.nameCompany", is(nameCompany))
				.body("[0].person.email", is(email))
				.body("[0].person.cpfCnpj", is(cpfCnpj));
	}
	
	@Test
	public void shouldFailedWhenConsultAddressPerPersonIdDoesNotExist() {
		given()
			.pathParam("personId", 1009)
			.basePath("/api/v1/addresses/byPersonId/{personId}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldSucceedWhenCreatedAddress() {
		ValidatableResponse addressCreated = createAddress();

		addressCreated
			.body("address", is(addressName))
			.body("zipCode", is(zipCode))
			.body("number", is(number))
			.body("city", is(city))
			.body("person.nameCompany", is(nameCompany))
			.body("person.email", is(email))
			.body("person.cpfCnpj", is(cpfCnpj));
	}

	@Test
	public void shouldSucceedWhenUpdatedAddress() {
		given()
			.pathParam("id", 2)
			.basePath("/api/v1/addresses/{id}")
			.port(port)
			.body(address)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.OK.value())
				.body("address", is(addressName))
				.body("zipCode", is(zipCode))
				.body("number", is(number))
				.body("city", is(city))
				.body("person.nameCompany", is(nameCompany))
				.body("person.email", is(email))
				.body("person.cpfCnpj", is(cpfCnpj));
	}
	
	@Test
	public void shouldFailedWhenUpdatedAddressWithNotExistId() {
		given()
			.pathParam("id", 9)
			.basePath("/api/v1/addresses/{id}")
			.port(port)
			.body(address)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldSuccedWhenDeletedAddress() {
		given()
			.pathParam("id", 2)
			.basePath("/api/v1/addresses/{id}")
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
			.pathParam("id", 9)
			.basePath("/api/v1/addresses/{id}")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.delete()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private ValidatableResponse createAddress() {
		return given()
				.basePath("/api/v1/addresses")
				.port(port)
				.body(address)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
}
