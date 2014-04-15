package com.luxoft.bankapp.exceptions;

/**
 * Created by Sergey Popov on 4/7/2014.
 */
public class BankNotFoundException extends NotFoundException {
	public BankNotFoundException(String bankName) {
		super("Bank " + bankName + " not found");
	}
}
