package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class AccountDAOImpl implements AccountDAO {
	private static AccountDAOImpl instance;
	static Logger logger = Logger.getLogger(AccountDAOImpl.class.getName());

	private AccountDAOImpl() {

	}

	public static AccountDAOImpl getInstance() {
		return instance == null ?
				instance = new AccountDAOImpl() :
				instance;
	}

    @Override
    public void save(Account account, int clientId) throws DAOException {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
		PreparedStatement stmt = null;

		try (Connection conn = baseDAO.openConnection()) {
			int accId = (account.getId());

			//System.out.println("accId = " + account.getId());
			if (accId <= 0) {
				stmt = conn.prepareStatement("INSERT INTO ACCOUNTS(CLIENT_ID, ACCTYPE, BALANCE, OVERDRAFT) " +
						"VALUES(?, ?, ?, ?)");
				stmt.setInt(1, clientId);
				stmt.setString(2, account.getAccountType());
				stmt.setBigDecimal(3, new BigDecimal(account.getBalance()));
				if (account.getAccountType().toUpperCase().equals("C")) {
					stmt.setFloat(4, ((CheckingAccount) account).getOverdraft());
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
				resultSet.close();

				logger.fine(String.format("The account [%s | %.2f] for client [%d] INSERTED in DB",
						account.getAccountType(), account.getBalance(), clientId));
				//System.out.println("accId after save = " + account.getId());
				//account.setClientId(clientId);
			} else {
				stmt = conn.prepareStatement("UPDATE ACCOUNTS SET " +
						"CLIENT_ID = ?, ACCTYPE = ?, BALANCE = ?, OVERDRAFT = ? WHERE ID = ?");

				stmt.setInt(1, clientId);
				stmt.setString(2, account.getAccountType());
				stmt.setBigDecimal(3, new BigDecimal(account.getBalance()));
				if (account.getAccountType().toUpperCase().equals("C")) {
					stmt.setFloat(4, ((CheckingAccount) account).getOverdraft());
				} else {
					stmt.setObject(4, null);
				}
				stmt.setInt(5, accId);
				stmt.executeUpdate();
				logger.fine(String.format("The account [%s | %.2f] for client [%d] UPDATED in DB",
						account.getAccountType(), account.getBalance(), clientId));
			}

			if (stmt != null) {	stmt.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			baseDAO.closeConnection();
		}
    }

    @Override
    public void removeByClientId(int id) {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
		try (Connection conn = baseDAO.openConnection()) {
			PreparedStatement stmt;
			stmt = conn.prepareStatement("DELETE FROM ACCOUNTS WHERE CLIENT_ID = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			if (stmt != null) {	stmt.close(); }
			logger.fine("All accounts for client [" + id + "] REMOVED from DB");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			baseDAO.closeConnection();
		}
	}

    @Override
    public List<Account> getClientAccounts(int id) {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = baseDAO.openConnection()) {
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
                i++;
            }

			if (resultSet != null) { resultSet.close(); };
			if (stmtAcc != null) { stmtAcc.close(); }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			baseDAO.closeConnection();
		}
        return accounts;
    }

}
