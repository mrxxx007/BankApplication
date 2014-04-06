package com.luxoft.bankapp.model;

import java.io.Serializable;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public abstract class AbstractAccount implements Account, Serializable {
    //protected static int accountsAmt;
	protected int id = -1;
    protected int clientId;
    protected String accountType;
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

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
	/*@Override
	public String getAccountType(Account account) {
		if (account.getClass().getName().equals(CheckingAccount.class.getName()))
			return "Checking account";
		else
			return "Saving account";
	}*/
}

