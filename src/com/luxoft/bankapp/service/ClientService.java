package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;

/**
 * Created by Sergey Popov on 4/8/2014.
 */
public interface ClientService {
	void saveClientToDB(Client client) throws DAOException;

}
