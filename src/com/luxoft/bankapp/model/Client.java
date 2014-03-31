package com.luxoft.bankapp.model; /**
 * Created by user on 3/25/2014.
 */
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.FeedException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.sun.org.apache.xpath.internal.operations.Equals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client implements Report {
    private String name;
    private List<Account> accounts = new ArrayList<Account>();
    private Account activeAccount;
    private float initialOverdraft;
    private Gender gender;
	private String email;
	private String phone;
	private String city;

    public Client() {
        initialOverdraft = 300;
    }

    public Client(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }
	public Client(String name, float initialOverdraft) {
		this(initialOverdraft);
		this.name = name;
	}


	/**
	 * This method finds account by its type or create a new one
	 */
	private Account getAccount(String accountType) {
		for (Account acc: accounts) {
			if (acc.getAccountType().equals(accountType)) {
				return acc;
			}
		}
		return createAccount(accountType);
	}

	/**
	 * This method creates account by its type
	 */
	private Account createAccount(String accountType) {
		Account acc;
		if ("s".equals(accountType)) {
			acc = new SavingAccount();
		} else if ("c".equals(accountType)) {
			acc = new CheckingAccount();
		} else {
			throw new FeedException("Account type not found "+accountType);
		}
		accounts.add(acc);
		return acc;
	}

    @Override
    public void printReport() {
		System.out.println(this);
	}

    public void setActiveAccount(Account a) {
    	activeAccount = a;
    }
	public Account getActiveAccount() {
		return activeAccount;
	}
    public float getBalance() {
        return activeAccount.getBalance();
    }
	public void deposit(float amount) throws DataVerifyException, NoEnoughFundsException {
		if (amount < 0) {
			throw new DataVerifyException("You can not deposit a negative amount");
		}
		activeAccount.deposit(amount);
	}
	public void withdraw(float amount) throws DataVerifyException, NoEnoughFundsException {
		if (amount < 0) {
			throw new DataVerifyException("You can not withdraw a negative amount");
		}
		activeAccount.withdraw(amount);
	}
    public List<Account> getAccounts() {
        return accounts;
    }
	public void addAccount(Account account) {
		accounts.add(account);
	}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getClientSalutation() {
        return gender == null ? "" : gender.getSalut();
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}



	public void parseFeed(Map<String, String> feed) {
		String accountType = feed.get("accounttype");
		Account acc = getAccount(accountType);

		/**
		 * This method should read all account info from the feed.
		 * There will be different implementations for
		 * CheckingAccount and SavingAccount.
		 */
		acc.parseFeed(feed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;

		final Client other = (Client)obj;
		if (name != other.name) return false;
		if (gender != other.gender) return false;
		if (email != other.email) return false;
		if (phone != other.phone) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Math.abs(31 * name.hashCode() +
			email.hashCode() + gender.name().hashCode() + phone.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Info for client: ")
			.append(gender.getSalut())
			.append(" ").append(name)
			.append("\n")
			.append("Accounts:\n");
		int i = 0;
		if (accounts != null)
			for (Account acc : accounts)
				sb.append("#")
					.append(++i)
					.append(" ")
					.append(acc);
		return sb.toString();
	}


}

