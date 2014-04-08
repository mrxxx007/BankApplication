package com.luxoft.bankapp.service;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.*;
/**
 * Created by Sergey Popov on 26.03.2014.
 */
public interface BankService {
	Bank getBank(String bankName) throws DAOException;
	//void deposit(Client client, float amount)
	//		throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;
	Client findClientByName(Bank bank, String name) throws ClientNotFoundException;
	void removeClient(Bank bank, Client client) throws DAOException;
	BankInfo getBankInfo(Bank bank) throws DAOException;
	//void withdraw(Client client, float amount) throws DataVerifyException, AccountNotFoundException, NoEnoughFundsException, DAOException;

    void addClient(Bank bank, Client client) throws ClientExistsException;

    void addAccount(Client client, Account account);
    void setActiveAccount(Client client, Account account);
	Client getClient(Bank bank, String clientName);
	void saveClient(Client client);
	Client loadClient();


}
