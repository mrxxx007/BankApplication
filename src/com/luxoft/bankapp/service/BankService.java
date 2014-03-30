package com.luxoft.bankapp.service;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.*;
/**
 * Created by Admin on 26.03.2014.
 */
public interface BankService {
    void addClient(Bank bank, Client client) throws ClientExistsException;
    void removeClient(Bank bank, Client client);
    void addAccount(Client client, Account account);
    void setActiveAccount(Client client, Account account);
	Client getClient(Bank bank, String clientName);
	String getAccountType(Account account);


}
