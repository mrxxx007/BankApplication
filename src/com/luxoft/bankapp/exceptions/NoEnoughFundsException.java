package com.luxoft.bankapp.exceptions;

/**
 * Created by Admin on 26.03.2014.
 */
public class NoEnoughFundsException extends BankException {
	public NoEnoughFundsException() {
		super("You haven't enough funds to perform this operation");
	}

	public NoEnoughFundsException(String message) {
		super(message);
	}
}
