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
public interface AccountService {
	/**
	 * Return full account type name from account object
	 * @param account
	 * @return
	 */
	String getAccountTypeName(Account account);
	void withdraw(int clientId, Account account, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
	void deposit(int clientId, Account account, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
	void saveToDB(Account account, int clientId) throws DAOException;
	void transfer(Account from, Account to, float amount) throws NoEnoughFundsException;
    float getBalance(Account account);
}
