package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;

/**
 * Created by Sergey Popov on 4/8/2014.
 */
public class AccountServiceImpl implements AccountService {
	@Override
	public String getAccountTypeName(Account account) {
		if (account.getClass().getName().equals(CheckingAccount.class.getName()))
			return "Checking account";
		else
			return "Saving account";
	}

	/*@Override
	public void withdraw(Client client, float amount) throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		client.withdraw(amount);
		new ClientDAOImpl().save(client);
	}*/
	@Override
	public void withdraw(Account account, float amount) throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		account.withdraw(amount);
		//client.withdraw(amount);
		new ClientDAOImpl().save(client);
		new AccountDAOImpl().save();
	}

	@Override
	public void deposit(Client client, float amount) throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		client.deposit(amount);
		new ClientDAOImpl().save(client);
	}
}
