package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.*;

/**
 * Created by user on 3/25/2014.
 */
public class BankServiceImpl implements BankService {
    @Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);
//        for (ClientRegistrationListener listener : bank.getListeners())
//            listener.onClientAdded(client);
    }
    @Override
    public void removeClient(Bank bank, Client client) {
        bank.getClients().remove(client);
    }
    @Override
    public  void addAccount(Client client, Account acc) {
        client.getAccounts().add(acc);
    }
    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

	@Override
	public String getAccountType(Account account) {
		if (account.getClass().getName().equals(CheckingAccount.class.getName()))
			return "Checking account";
		else
			return "Saving account";
	}

	@Override
	public Client getClient(Bank bank, String clientName) {
		return bank.findClientByName(clientName);
	}
}

