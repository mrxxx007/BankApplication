package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.*;

import java.io.*;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class BankServiceImpl implements BankService {
	@Override
	public Bank getBank(String bankName) throws DAOException {
		return new BankDAOImpl().getBankByName(bankName);
	}

	/*@Override
	public void deposit(Client client, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		client.deposit(amount);
		new ClientDAOImpl().save(client);
	}*/

	@Override
	public Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
		return new ClientDAOImpl().findClientByName(bank, name);
	}

	@Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);
//        for (ClientRegistrationListener listener : bank.getListeners())
//            listener.onClientAdded(client);
    }

    @Override
    public void removeClient(Bank bank, Client client) throws DAOException {
		new ClientDAOImpl().remove(client);
    }

	@Override
	public BankInfo getBankInfo(Bank bank) throws DAOException {
		return new BankDAOImpl().getBankInfo(bank);
	}

	/*@Override
	public void withdraw(Client client, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		client.withdraw(amount);
		new ClientDAOImpl().save(client);
	}*/

	@Override
    public  void addAccount(Client client, Account acc) {
        client.getAccounts().add(acc);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }



	@Override
	public void saveClient(Client client) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new
					FileOutputStream("DAO/client.obj"));
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
					FileInputStream("DAO/client.obj"));
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

	@Override
	public Client getClient(Bank bank, String clientName) {
		return bank.findClientByName(clientName);
	}
}

