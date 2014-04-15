package com.luxoft.bankapp.model;

import com.luxoft.bankapp.annotations.NoDB;

import java.io.Serializable;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public abstract class AbstractAccount implements Account, Serializable {
	@NoDB protected int id = -1;
	@NoDB protected String accountType;
	protected float balance;

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    synchronized public void deposit(float x) {
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
    public int getId() {
        return id;
    }

    public String getAccountType() {
        return accountType;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

   	/*@Override
	public String getAccountType(Account account) {
		if (account.getClass().getName().equals(CheckingAccount.class.getName()))
			return "Checking account";
		else
			return "Saving account";
	}*/
}

