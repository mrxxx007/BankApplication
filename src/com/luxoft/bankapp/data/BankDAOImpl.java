package com.luxoft.bankapp.data;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class BankDAOImpl implements BankDAO {
    @Override
    public Bank getBankByName(String name) throws SQLException, DAOException {
        BaseDAOImpl baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

        Bank bank;

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BANKS WHERE NAME = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs == null || !rs.next()) {
            throw new DAOException("Bank not found: " + name);
        }

        bank = new Bank(rs.getString("NAME"));
        bank.setId(rs.getInt("ID"));

        baseDAO.closeConnection();

        return bank;
    }
}
