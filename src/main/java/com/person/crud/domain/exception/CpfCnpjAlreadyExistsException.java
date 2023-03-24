package com.person.crud.domain.exception;

public class CpfCnpjAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfCnpjAlreadyExistsException(String message) {
		super(message);
	}
}
