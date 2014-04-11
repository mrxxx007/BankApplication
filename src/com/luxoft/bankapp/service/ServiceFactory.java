package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.*;

/**
 * Created by Sergey Popov on 4/11/2014.
 */
public class ServiceFactory {
	public static BankDAO getBankDAO() {
		return BankDAOImpl.getInstance();
	}

	public static AccountDAO getAccountDAO() {
		return AccountDAOImpl.getInstance();
	}

	public static ClientDAO getClientDAO() {
		return ClientDAOImpl.getInstance();
	}

	public static AccountService getAccountService() {
		return AccountServiceImpl.getInstance();
	}

	public static BankService getBankService() {
		return BankServiceImpl.getInstance();
	}

	public static ClientService getClientService() {
		return ClientServiceImpl.getInstance();
	}

}
