package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.ServiceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class ClientDAOImpl implements ClientDAO {
	private static ClientDAOImpl instance;

	private ClientDAOImpl() {

	}

	public static ClientDAOImpl getInstance() {
		return instance == null ?
				instance = new ClientDAOImpl() :
				instance;
	}

    @Override
    public Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
        BaseDAO baseDAO = new BaseDAOImpl();
        Client client = null;
        try (Connection conn = baseDAO.openConnection()) {
            PreparedStatement stmtClient =
                    conn.prepareStatement("SELECT * FROM CLIENTS WHERE NAME = ? AND BANK_ID = ?");
            stmtClient.setString(1, name);
            stmtClient.setInt(2, bank.getId());
            ResultSet resultSet = stmtClient.executeQuery();

            if (!resultSet.first()) {
                throw new ClientNotFoundException(name);
            }

            client = extractClientFromResultSet(resultSet);

			List<Account> clientAccounts = ServiceFactory.getAccountDAO().getClientAccounts(client.getId());
			if (clientAccounts.size() > 0) {
				client.addAccounts(clientAccounts);
				client.setActiveAccount(clientAccounts.get(0));
			}
			if (resultSet != null) { resultSet.close(); }
			if (stmtClient != null) { stmtClient.close(); }
		} catch (SQLException e) {
            e.printStackTrace();
        } finally {
			baseDAO.closeConnection();
		}

        return client;
    }

    @Override
    public Client findClientById(int clientId) throws ClientNotFoundException {
        BaseDAO baseDAO = new BaseDAOImpl();
        Client client = null;
        try (Connection conn = baseDAO.openConnection()) {
            PreparedStatement stmtClient =
                    conn.prepareStatement("SELECT * FROM CLIENTS WHERE ID = ?");
            stmtClient.setInt(1, clientId);
            ResultSet resultSet = stmtClient.executeQuery();

            if (!resultSet.first()) {
                throw new ClientNotFoundException("(id = " + clientId + ")");
            }

            client = extractClientFromResultSet(resultSet);

			List<Account> clientAccounts = ServiceFactory.getAccountDAO().getClientAccounts(client.getId());
            if (clientAccounts.size() > 0) {
				client.addAccounts(clientAccounts);
				client.setActiveAccount(clientAccounts.get(0));
			}

			if (resultSet != null) { resultSet.close(); }
			if (stmtClient != null) { stmtClient.close(); }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			baseDAO.closeConnection();
		}

        return client;
    }

    @Override
    public List<Client> getAllClients(Bank bank) {
        BaseDAO baseDAO = new BaseDAOImpl();
        List<Client> clients = new ArrayList<>();

		try (Connection conn = baseDAO.openConnection()) {
			int bankId = bank.getId();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CLIENTS WHERE BANK_ID = ?");
			stmt.setInt(1, bankId);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("NAME");
				Gender gender = Gender.valueOf(resultSet.getString("GENDER").toUpperCase());
				String email = resultSet.getString("EMAIL");
				String phone = resultSet.getString("PHONE");
				String city = resultSet.getString("CITY");
				int id = resultSet.getInt("ID");

				Client client = new Client(id, name, 0f);
				client.setGender(gender);
				client.setId(id);
				client.setEmail(email);
				client.setPhone(phone);
				client.setCity(city);
				client.setBankId(bankId);

				List<Account> clientAccounts = ServiceFactory.getAccountDAO().getClientAccounts(client.getId());
				if (clientAccounts.size() > 0) {
					client.addAccounts(clientAccounts);
					client.setActiveAccount(clientAccounts.get(0));
				}

				clients.add(client);
			}

			if (resultSet != null) { resultSet.close(); }
			if (stmt != null) { stmt.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			baseDAO.closeConnection();
		}
        return clients;
    }

    @Override
    public void save(Client client) throws DAOException {
        BaseDAO baseDAO = new BaseDAOImpl();
        PreparedStatement stmtClient = null;
		try (Connection conn = baseDAO.openConnection()) {
			if (client.getId() == -1) {
				stmtClient = getQuery(QueryType.INSERT, client, conn);
				if (stmtClient.executeUpdate() == 0) {
					throw new DAOException("Impossible to save Client in DB. Transaction is rolled back");
				}
				ResultSet resultSet = stmtClient.getGeneratedKeys();
				if (resultSet == null || !resultSet.next()) {
					throw new DAOException("Impossible to save in DB. Can't get clientID.");
				}
				client.setId(resultSet.getInt(1));
				if (resultSet != null) { resultSet.close(); }
			} else {
				stmtClient = getQuery(QueryType.UPDATE, client, conn);
				if (stmtClient.executeUpdate() == 0) {
					throw new DAOException("Impossible to update Client in DB. Transaction is rolled back");
				}

			}
			if (stmtClient != null) { stmtClient.close(); }

			for (Account acc : client.getAccounts()) {
				//System.out.println("Exec save acc");
				ServiceFactory.getAccountDAO().save(acc, client.getId());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			baseDAO.closeConnection();
		}
    }

    @Override
    public void remove(Client client) throws DAOException {
        BaseDAO baseDAO = new BaseDAOImpl();
		try (Connection conn = baseDAO.openConnection()) {
			PreparedStatement stmtClient = getQuery(QueryType.DELETE, client, conn);
			if (stmtClient.executeUpdate() == 0) {
				throw new DAOException("Impossible to delete Client from DB. Transaction is rolled back");
			}
			ServiceFactory.getAccountDAO().removeByClientId(client.getId());
			if (stmtClient != null) { stmtClient.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			baseDAO.closeConnection();
		}
	}

    private enum QueryType {
        INSERT, UPDATE, DELETE
    }

    private Client extractClientFromResultSet(ResultSet resultSet) throws SQLException {
        Client client = new Client(resultSet.getInt("ID"), resultSet.getString("NAME"), 0f);
        Gender gender = Gender.valueOf(resultSet.getString("GENDER").toUpperCase());
		client.setGender(gender);
        client.setEmail(resultSet.getString("EMAIL"));
        client.setPhone(resultSet.getString("PHONE"));
        client.setCity(resultSet.getString("CITY"));
		client.setBankId(resultSet.getInt("BANK_ID"));
        return client;
    }

    private PreparedStatement getQuery(QueryType queryType, Client client, Connection conn) throws SQLException {
        PreparedStatement stmtClient = null;
        switch (queryType) {
            case UPDATE:
                stmtClient = conn.prepareStatement("UPDATE CLIENTS " +
                        "SET NAME = ?, GENDER = ?, EMAIL = ?, PHONE = ?, CITY = ?, BANK_ID = ? " +
                        "WHERE ID = ?");
                stmtClient.setString(1, client.getName());
                stmtClient.setString(2, client.getGender());
                stmtClient.setString(3, client.getEmail());
                stmtClient.setString(4, client.getPhone());
                stmtClient.setString(5, client.getCity());
                stmtClient.setInt(6, client.getBankId());
                stmtClient.setInt(7, client.getId());
                break;
            case INSERT:
                stmtClient = conn.prepareStatement("INSERT INTO CLIENTS(NAME, GENDER, EMAIL, PHONE, CITY, BANK_ID) " +
                        "VALUES(?, ?, ?, ?, ?, ?)");
                stmtClient.setString(1, client.getName());
                stmtClient.setString(2, client.getGender());
                stmtClient.setString(3, client.getEmail());
                stmtClient.setString(4, client.getPhone());
                stmtClient.setString(5, client.getCity());
                stmtClient.setInt(6, client.getBankId());
                break;
            case DELETE:
                stmtClient = conn.prepareStatement("DELETE FROM CLIENTS WHERE ID = ?");
                stmtClient.setInt(1, client.getId());
                break;
        }
        return stmtClient;
    }
}
