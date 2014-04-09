package com.luxoft.bankapp.service;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.*;
/**
 * Created by Sergey Popov on 26.03.2014.
 */
public interface BankService {
	Bank getBank(String bankName) throws DAOException;
	Client findClientByName(Bank bank, String name) throws ClientNotFoundException;
	void removeClient(Bank bank, Client client) throws DAOException;
	BankInfo getBankInfo(Bank bank) throws DAOException;
    void addClient(Bank bank, Client client) throws ClientExistsException;
	Client getClient(Bank bank, String clientName);
}
