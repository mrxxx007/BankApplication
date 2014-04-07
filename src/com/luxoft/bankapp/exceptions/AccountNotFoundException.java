package com.luxoft.bankapp.exceptions;

/**
 * Created by Sergey Popov on 4/7/2014.
 */
public class AccountNotFoundException extends NotFoundException {
	public AccountNotFoundException(String message) {
		super(message);
	}
}
