package com.luxoft.bankapp.model;
/**
 * Created by Sergey Popov on 3/25/2014.
 */

import com.luxoft.bankapp.annotations.NoDB;
import com.luxoft.bankapp.exceptions.FeedException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Client implements Report, Serializable {
	@NoDB private int id = -1;
    private int bankId;
	private String name;
	private List<Account> accounts = new ArrayList<Account>();
	@NoDB private Account activeAccount;
	@NoDB private float initialOverdraft;
	private Gender gender;
	private String email;
	private String phone;
	private String city;

	//region Constructors
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
    public Client (int id, String name, float initialOverdraft) {
        this(name, initialOverdraft);
        this.id = id;
    }
	//endregion

	@Override
	public void printReport() {
		System.out.println(this);
	}

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
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

	public List<Account> getAccounts() {
		//return Collections.unmodifiableList(accounts);
		return accounts;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

    public void addAccounts(List<Account> accounts) {
        this.accounts.addAll(accounts);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender == null ? null : gender.name();
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

	public void parseFeed(Map<String, String> feed) {
        gender = feed.get("gender").equals("m") ?
                Gender.MALE : Gender.FEMALE;
        //client.setGender(feed.get("gender").equals("m") ?
        //        Gender.MALE : Gender.FEMALE);
        city = feed.get("city");
        //client.setCity(feed.get("city"));
        phone = feed.get("phone");
        //client.setPhone();
        email = feed.get("email");
        //client.setEmail(feed.get("email"));

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
        boolean result;

		final Client other = (Client) obj;
		if (!name.equals(other.name)) return false;
		if (gender != other.gender) return false;
		//if (!email.equals(other.email)) return false;
        result = email == null ? other.email == null : email.equals(other.email);
		if (!result) return result;

        //if (!phone.equals(other.phone)) return false;
        result = phone == null ? other.phone == null : phone.equals(other.phone);
        if (!result) return result;

        //if (!city.equals(other.city)) return false;
        result = city == null ? other.city == null : city.equals(other.city);
        if (!result) return result;
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
		sb.append("\nInfo for client: ")
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

    /**
     * This method finds account by its type or create a new one
     */
    private Account getAccount(String accountType) {
        for (Account acc : accounts) {
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
            throw new FeedException("Account type not found " + accountType);
        }
        accounts.add(acc);
        return acc;
    }
}

