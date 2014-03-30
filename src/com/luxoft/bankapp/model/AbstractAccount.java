package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.Account;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public abstract class AbstractAccount implements Account {
    protected static int accountsAmt;
	protected int id;
	protected float balance;

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public void deposit(float x) {
        if (x < 0)
            throw new IllegalArgumentException();
        balance += x;
    }

	@Override
	public int decimalValue() {
		return Math.round(balance);
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public String getAccountType(Account account) {
		if (account.getClass().getName().equals(CheckingAccount.class.getName()))
			return "Checking account";
		else
			return "Saving account";
	}
}

