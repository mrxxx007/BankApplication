package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NoEnoughFundsException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public interface Account extends Report {
    float getBalance();
    void deposit(float x);
    void withdraw(float x) throws NoEnoughFundsException;
	int decimalValue();
	String getAccountType();
	void parseFeed(Map<String, String> feed);
    int getClientId();
    void setClientId(int id);
    void setId(int id);
    int getId();
}

