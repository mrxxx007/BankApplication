package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NoEnoughFundsException;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class SavingAccount extends AbstractAccount {
    public SavingAccount() {
        balance = 0f;
		id = ++accountsAmt;
    }
    public SavingAccount(float balance) {
        this.balance = balance;
		id = ++accountsAmt;
    }

    @Override
    public void printReport() {
        //System.out.println("  - " + this.getClass().getName());
        //System.out.println("    Balance: " + balance);
		System.out.println(this);
	}

    @Override
    public void withdraw(float x) throws NoEnoughFundsException {
        if (x < 0)
            throw new IllegalArgumentException();
        if (balance < x)
            throw new NoEnoughFundsException();
        balance -= x;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;

		final SavingAccount other = (SavingAccount)obj;
		if (balance != other.balance) return false;

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: Saving account\n")
			.append("Balance: ").append(balance)
			.append("\n");
		return sb.toString();
	}
}

