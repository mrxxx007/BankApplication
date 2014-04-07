package com.luxoft.bankapp.data;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankInfo;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.commands.BankCommander;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class BankDAOImpl implements BankDAO {
    @Override
    public Bank getBankByName(String name) throws DAOException {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
		Bank bank = new Bank(name);
        try (Connection conn = baseDAO.openConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BANKS WHERE NAME = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs == null || !rs.next()) {
				throw new DAOException("Bank not found: " + name);
			}

			bank.setId(rs.getInt("ID"));
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			baseDAO.closeConnection();
		}

        return bank;
    }

	@Override
	public BankInfo getBankInfo(Bank bank) throws DAOException {
		BankInfo bankInfo = new BankInfo();

		BaseDAOImpl baseDAO = new BaseDAOImpl();
		try (Connection conn = baseDAO.openConnection()) {
			PreparedStatement stmtClientsNum = conn.prepareStatement("SELECT COUNT(ID) AS TOTAL FROM CLIENTS");
			PreparedStatement stmtAccSum = conn.prepareStatement("SELECT SUM(BALANCE) AS TOTAL_BAL FROM ACCOUNTS;");
			ResultSet resultSet = stmtClientsNum.executeQuery();
			if (resultSet == null || !resultSet.next()) {
				throw new DAOException("Can't calculate total number of clients");
			}
			bankInfo.setNumberOfClients(resultSet.getInt(1));

			resultSet = stmtAccSum.executeQuery();
			if (resultSet == null || !resultSet.next()) {
				throw new DAOException("Can't calculate sum of all accounts of clients");
			}
			bankInfo.setTotalAccountSum(resultSet.getDouble(1));

			List<Client> clients = new ClientDAOImpl().getAllClients(bank);
			Map<String, List<Client>> clientsByCity = new TreeMap<>();
			for (Client client : clients) {
				if (!clientsByCity.containsKey(client.getCity())) {
					clientsByCity.put(client.getCity(), new ArrayList<Client>());
				}
				clientsByCity.get(client.getCity()).add(client);
			}

			bankInfo.setClientsByCity(clientsByCity);



		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			baseDAO.closeConnection();
		}
		return bankInfo;
	}
}
