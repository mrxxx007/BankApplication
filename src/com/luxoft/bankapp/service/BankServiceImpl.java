package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.*;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class BankServiceImpl implements BankService {
	private static BankServiceImpl instance;
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


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
	synchronized public Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
		//lock.readLock().lock();
		Client client = bank.findClientByName(name);
		if (client != null) {
			//lock.readLock().unlock();
			return client;
		}


		//lock.readLock().unlock();
		//lock.writeLock().lock();
		client = ServiceFactory.getClientDAO().findClientByName(bank, name);
		try {
			addClient(bank, client);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		//lock.writeLock().unlock();
		return client;

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

