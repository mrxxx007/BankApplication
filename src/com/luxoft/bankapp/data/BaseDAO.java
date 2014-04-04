package com.luxoft.bankapp.data;

import java.sql.Connection;

/**
 * Created by Sergey Popov on 4/4/2014.
 */
public interface BaseDAO {
	/**
	 * Opens connection with the database
	 * @return
	 */
	public Connection openConnection();

	/**
	 * Close connection with the database
	 */
	public void closeConnection();
}
