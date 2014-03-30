package com.luxoft.bankapp.exceptions;

/**
 * Created by Admin on 26.03.2014.
 */
public class ClientExistsException extends BankException {
	public ClientExistsException() {
		super("The client is already exists");
	}
}
