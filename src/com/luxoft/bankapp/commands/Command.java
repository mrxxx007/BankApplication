package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;

import java.io.IOException;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public interface Command {
	void execute() throws IOException, BankException, DAOException;
	void printCommandInfo();
}
