package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sergey Popov on 4/8/2014.
 */
public class AccountServiceImpl implements AccountService {
	private static AccountServiceImpl instance;
    private static Object monitor;

	private AccountServiceImpl() {
        monitor = new Object();
	}

	public static AccountServiceImpl getInstance() {
		return instance == null ?
				instance = new AccountServiceImpl() :
				instance;
	}

	@Override
	public String getAccountTypeName(Account account) {
		if (account.getClass().getName().equals(CheckingAccount.class.getName()))
			return "Checking account";
		else
			return "Saving account";
	}

	@Override
	synchronized public void withdraw(int clientId, Account account, float amount)
            throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		account.withdraw(amount);
		ServiceFactory.getAccountDAO().save(account, clientId);
	}

	@Override
	synchronized public void deposit(int clientId, Account account, float amount)
			throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException {
		account.deposit(amount);
		ServiceFactory.getAccountDAO().save(account, clientId);
	}

	@Override
	public void saveToDB(Account account, int clientId) throws DAOException {
		ServiceFactory.getAccountDAO().save(account, clientId);
	}

    @Override
    public Account getAccount(int clientId, int accountId) {
        return ServiceFactory.getAccountDAO().getClientAccounts(clientId).get(accountId);
    }

    @Override
	public void transfer(Account from, Account to, float amount) throws NoEnoughFundsException {
		from.withdraw(amount);
		to.deposit(amount);
	}

    @Override
    public float getBalance(Account account) {
        return account.getBalance();
    }
}
