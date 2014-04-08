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
	 *
	 * @param account
	 * @return Full account name
	 */
	String getAccountTypeName(Account account);
	void withdraw(Account account, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
	void deposit(Client client, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
}
