package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Client;

/**
 * Created by Sergey Popov on 4/8/2014.
 */
public class ClientServiceImpl implements ClientService {
	@Override
	public void saveClientToDB(Client client) throws DAOException {
		new ClientDAOImpl().save(BankCommander.activeClient);
	}
}
