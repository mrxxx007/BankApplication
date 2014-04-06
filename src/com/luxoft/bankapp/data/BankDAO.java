package com.luxoft.bankapp.data;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.SQLException;

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
	Bank getBankByName(String name) throws SQLException, DAOException;
}
