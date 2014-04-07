package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.data.ClientDAO;
import com.luxoft.bankapp.data.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sergey Popov on 4/7/2014.
 */
public class RemoveClientCommand implements Command {
	@Override
	public void execute() throws ClientNotFoundException, DAOException {
		if (BankCommander.activeClient == null) {
			throw new ClientNotFoundException("Active client not found. Please, find the client you need first");
		}

		new ClientDAOImpl().remove(BankCommander.activeClient);
		BankCommander.activeClient = null;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Remove client");
	}
}
