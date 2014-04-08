package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankInfo;

/**
 * Created by Sergey Popov on 4/4/2014.
 */
public interface BankDAO {
	/**
	 * Finds Bank by its name.
	 * Do not load the list of the clients.
	 * @param name
	 * @return
	 */
	Bank getBankByName(String name) throws DAOException;

	/**
	 * Should fill the BankInfo
	 */
	BankInfo getBankInfo(Bank bank) throws DAOException;
}
