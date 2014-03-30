package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;

import java.util.*;

/**
 * Created by Admin on 29.03.2014.
 */
public class BankReport {
	public static void getNumberOfClients(Bank bank) {
		System.out.println("Number of clients: " +
				bank.getClients().size());
		//return 0;
	}

	public static void getAccountsNumber(Bank bank) {
		int count = 0;
		for (Client client : bank.getClients()) {
			for (Account acc : client.getAccounts()) {
				count++;
			}
		}
		System.out.println("Total accounts number: " + count);
	}

	public static void getClientsSorted(Bank bank) {
		Map<Float, Client> result = new TreeMap<Float, Client>(new Comparator<Float>() {
			@Override
			public int compare(Float o1, Float o2) {
				if (o1.floatValue() > o2.floatValue())
					return 1;
				else if (o1.floatValue() < o2.floatValue())
					return -1;
				else
					return 0;
			}
		});
		for (Client client : bank.getClients()) {
			result.put(client.getActiveAccount().getBalance(), client);
		}
		System.out.println("Clients sorted by their balance");
		for (Map.Entry entry : result.entrySet())
			System.out.println(entry.getValue() + "\n");
	}

	public static void getBankCreditSum(Bank bank) {
		int sum = 0;
		for (Client client : bank.getClients()) {
			for (Account acc : client.getAccounts()) {
				//TODO Make enum account types
				if (acc.getAccountType(acc).equals("Checking account"))
					sum += ((CheckingAccount)acc).getOverdraft();
			}
		}
		System.out.println("Total overdraft sum: " + sum);
	}

	public static void getClientsByCity(Bank bank) {
		Map<String, List<Client>> result = new TreeMap<String, List<Client>>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (o1.compareTo(o2));
			}
		});

		for (Client client : bank.getClients()) {
			if (result.get(client.getCity()) == null) {
				result.put(client.getCity(), new ArrayList<Client>());
			}
			result.get(client.getCity()).add(client);
		}

		System.out.println("Clients sorted by city:\n");
		for (Map.Entry entry : result.entrySet()) {
			System.out.println(" = " + entry.getKey() + " =\n" + entry.getValue());
		}
	}
}
