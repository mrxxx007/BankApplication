package com.luxoft.bankapp.exceptions;

/**
 * Created by user on 3/31/2014.
 */
public class ClientNotFoundException extends Exception {
	public ClientNotFoundException(String clientName) {
		super("The client " + clientName + " not found");
	}
}
