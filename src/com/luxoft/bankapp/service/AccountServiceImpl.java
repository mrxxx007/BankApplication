package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;

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

	@Override
	public void withdraw(int clientId, Account account, float amount) throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		account.withdraw(amount);
		new AccountDAOImpl().save(account, clientId);
	}

	@Override
	public void deposit(int clientId, Account account, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		account.deposit(amount);
		new AccountDAOImpl().save(account, clientId);
	}

	@Override
	public void saveToDB(Account account, int clientId) throws DAOException {
		new AccountDAOImpl().save(account, clientId);
	}
}
