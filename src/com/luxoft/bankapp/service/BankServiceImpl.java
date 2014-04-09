package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.*;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class BankServiceImpl implements BankService {
	@Override
	public Bank getBank(String bankName) throws DAOException {
		return new BankDAOImpl().getBankByName(bankName);
	}

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

	@Override
	public Client getClient(Bank bank, String clientName) {
		return bank.findClientByName(clientName);
	}
}

