package com.luxoft.bankapp.data;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sergey Popov on 4/4/2014.
 */
public interface ClientDAO {
	/**
	 * Return client by its name, initialize client accounts.
	 * @param bank
	 * @param name
	 * @return
	 */
	Client findClientByName(Bank bank, String name) throws ClientNotFoundException;

	/**
	 * Return client by its name, initialize client accounts.
	 * @param clientId
	 * @return
	 */
	Client findClientById(int clientId) throws ClientNotFoundException;

	/**
	 * Returns the list of all clients of the Bank
	 * and their accounts
	 * @param bankId
	 * @return
	 */
	List<Client> getAllClients(Bank bankId) throws SQLException;

	/**
	 * Method should insert new Client (if id==null)
	 * or update client in database (if id!=null)
	 * @param client
	 */
	void save(Client client) throws SQLException, DAOException;

	/**
	 * Method removes client from Database
	 * @param client
	 */
	void remove(Client client) throws SQLException, DAOException;
}
