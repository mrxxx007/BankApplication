package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public interface AccountDAO {
    /**
     * Save Account in database
     * @param account
     */
    public void save(Account account, int clientId) throws SQLException, DAOException;

    /**
     * Remove all accounts of client
     * @param id Id of the client
     */
    public void removeByClientId(int id) throws SQLException;

    /**
     * Get all accounts of the client
     * @param id Id of the client
     */
    public List<Account> getClientAccounts(int id);
}
