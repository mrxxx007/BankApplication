package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.io.*;

/**
 * Created by Sergey Popov on 4/8/2014.
 */
public class ClientServiceImpl implements ClientService {
	@Override
	public void saveClientToDB(Client client) throws DAOException {
		new ClientDAOImpl().save(BankCommander.activeClient);
	}

	@Override
	public void withdraw(Client client, int accountIndex, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		new AccountServiceImpl().withdraw(client.getId(), client.getAccounts().get(accountIndex), amount);
	}

	@Override
	public void deposit(Client client, int accountIndex, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		new AccountServiceImpl().deposit(client.getId(), client.getAccounts().get(accountIndex), amount);
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);
	}

	@Override
	public void addAccount(Client client, Account account) {
		client.addAccount(account);
	}

	@Override
	public void saveClient(Client client) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new
					FileOutputStream("dao/client.obj"));
			output.writeObject(client);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Client loadClient() {
		Client client = null;
		try {
			ObjectInputStream input = new ObjectInputStream(new
					FileInputStream("dao/client.obj"));
			client = (Client)input.readObject();
			input.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return client;
	}
}
