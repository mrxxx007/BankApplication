package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

/**
 * Created by Sergey Popov on 4/8/2014.
 */
public interface ClientService {
	void saveClientToDB(Client client) throws DAOException;
	void withdraw(Client client, int accountIndex, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
	void deposit(Client client, int accountIndex, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
	void setActiveAccount(Client client, Account account);
	void addAccount(Client client, Account account);
    float getBalance(Client client, int accountId);

	/**
	 * Serialize and save Client to file
	 * @param client
	 */
	void saveClient(Client client);

	/**
	 * Load serialized Client from file
	 * @return
	 */
	Client loadClient();
}
