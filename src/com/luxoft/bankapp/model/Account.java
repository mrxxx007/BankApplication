package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NoEnoughFundsException;

/**
 * Created by user on 3/25/2014.
 */
public interface Account extends Report {
    float getBalance();
    void deposit(float x);
    void withdraw(float x) throws NoEnoughFundsException;
	int decimalValue();
	String getAccountType(Account acc);
}

