package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;

import java.io.IOException;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public interface Command {
	void execute() throws IOException, ClientNotFoundException, DAOException;
	void printCommandInfo();
}
