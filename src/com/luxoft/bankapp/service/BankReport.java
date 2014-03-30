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
		Set<Client> result = new TreeSet<>(new Comparator<Client>() {
			@Override
			public int compare(Client o1, Client o2) {
				if (o1.getBalance() > o2.getBalance()) {
					return 1;
				} else if (o1.getBalance() < o2.getBalance()) {
					return -1;
				} else
					return 0;
			}
		});
		result.addAll(bank.getClients());
		System.out.println(result);
	}

	public static void getBankCreditSum(Bank bank) {
		int sum = 0;
		for (Client client : bank.getClients()) {
			for (Account acc : client.getAccounts()) {
				if (acc.getBalance() < 0)
					sum -= acc.getBalance();
			}
		}
		System.out.println("Total bank credit sum: " + sum);
	}

	public static void getClientsByCity(Bank bank) {
		Map<String, List<Client>> result = new TreeMap<String, List<Client>>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (o1.compareTo(o2));
			}
		});

		for (Client client : bank.getClients()) {
			if (!result.containsKey(client.getCity())) {
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
