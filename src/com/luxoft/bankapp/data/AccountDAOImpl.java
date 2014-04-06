package com.luxoft.bankapp.data;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class AccountDAOImpl implements AccountDAO {
    @Override
    public void save(Account account, int clientId) throws SQLException, DAOException {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();
        PreparedStatement stmt;
        int accId = (account.getId());

        if (accId == -1) {
            stmt = conn.prepareStatement("INSERT INTO ACCOUNTS(CLIENT_ID, ACCTYPE, BALANCE, OVERDRAFT) " +
                    "VALUES(?, ?, ?, ?)");
            stmt.setInt(1, clientId);
            stmt.setString(2, account.getAccountType());
            stmt.setBigDecimal(3, new BigDecimal(account.getBalance()));
            if (account.getAccountType().toUpperCase().equals("C")) {
                stmt.setFloat(4, ((CheckingAccount)account).getOverdraft());
            } else {
                stmt.setObject(4, null);
            }

            if (stmt.executeUpdate() == 0) {
                throw new DAOException("Impossible to save Account in DB. Transaction is rolled back");
            }
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet == null || !resultSet.next()) {
                throw new DAOException("Impossible to save in DB. Can't get accountID.");
            }
            account.setId(resultSet.getInt(1));
            account.setClientId(clientId);
        } else {
            stmt = conn.prepareStatement("UPDATE ACCOUNTS SET " +
                    "CLIENT_ID = ?, ACCTYPE = ?, BALANCE = ?, OVERDRAFT = ?");
            stmt.setInt(1, clientId);
            stmt.setString(2, account.getAccountType());
            stmt.setBigDecimal(3, new BigDecimal(account.getBalance()));
            if (account.getAccountType().toUpperCase().equals("C")) {
                stmt.setFloat(4, ((CheckingAccount)account).getOverdraft());
            } else {
                stmt.setObject(4, null);
            }
        }
        baseDAO.closeConnection();
    }

    @Override
    public void removeByClientId(int id) throws SQLException {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();
        PreparedStatement stmt;
        stmt = conn.prepareStatement("DELETE FROM ACCOUNTS WHERE CLIENT_ID = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<Account> getClientAccounts(int id) {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement stmtAcc =
                    conn.prepareStatement("SELECT * FROM ACCOUNTS WHERE CLIENT_ID = ?");
            stmtAcc.setInt(1, id);

            ResultSet resultSet = stmtAcc.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal("BALANCE");
                if (resultSet.getString("ACCTYPE").toUpperCase().equals("C")) {
                    accounts.add(new CheckingAccount(balance.floatValue(),
                            resultSet.getFloat("OVERDRAFT")));
                } else {
                    accounts.add(new SavingAccount(balance.floatValue()));
                }
                accounts.get(i).setId(resultSet.getInt("ID"));
                accounts.get(i).setClientId(id);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        baseDAO.closeConnection();
        return accounts;
    }
}
