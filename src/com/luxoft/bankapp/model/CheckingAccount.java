package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class CheckingAccount extends AbstractAccount {
    private float overdraft;
	//private String accountType = "c";

    public CheckingAccount() {
        balance = 0f;
        accountType = "c";
        //id = ++accountsAmt;
    }
    public CheckingAccount(float balance, float overdraft) {
        this.balance = balance;
        this.overdraft = overdraft;
        accountType = "c";
        //id = ++accountsAmt;
    }

    public void setOverdraft(float x){
        if (x < 0)
            throw new IllegalArgumentException();
        overdraft = x;
    }

	public float getOverdraft() {
		return overdraft;
	}

	@Override
	public void parseFeed(Map<String, String> feed) {
		try {
			balance = Float.parseFloat(feed.get("balance"));
			overdraft = Float.parseFloat(feed.get("overdraft"));
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
    public void printReport() {
        System.out.println("  - " + this.getClass().getName());
        System.out.println("    Balance: " + balance);
        System.out.println("    Overdraft: " + overdraft);
    }

    @Override
    public void withdraw(float x) throws OverDraftLimitExceededException {
        if (x < 0)
            throw new IllegalArgumentException();

        if (overdraft + balance < x)
            throw new OverDraftLimitExceededException(overdraft + balance);
        balance -= x;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;

		final CheckingAccount other = (CheckingAccount)obj;
		if (balance != other.balance) return false;
		if (overdraft != other.overdraft) return false;

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Type: Checking account\n")
			.append("   Balance: ").append(balance)
			.append("\n   Overdraft: ").append(overdraft)
			.append("\n");
		return sb.toString();
	}
}

