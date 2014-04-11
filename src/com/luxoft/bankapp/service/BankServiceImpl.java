package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.*;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class BankServiceImpl implements BankService {
	private static BankServiceImpl instance;

	private BankServiceImpl() {

	}

	public static BankServiceImpl getInstance() {
		return instance == null ?
				instance = new BankServiceImpl() :
				instance;
	}

	@Override
	public Bank getBank(String bankName) throws DAOException {
		return ServiceFactory.getBankDAO().getBankByName(bankName);
	}

	@Override
	public Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
		return ServiceFactory.getClientDAO().findClientByName(bank, name);
	}

	@Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);
//        for (ClientRegistrationListener listener : bank.getListeners())
//            listener.onClientAdded(client);
    }

    @Override
    public void removeClient(Bank bank, Client client) throws DAOException {
		ServiceFactory.getClientDAO().remove(client);
    }

	@Override
	public BankInfo getBankInfo(Bank bank) throws DAOException {
		return ServiceFactory.getBankDAO().getBankInfo(bank);
	}

	@Override
	public Client getClient(Bank bank, String clientName) {
		return bank.findClientByName(clientName);
	}
}

