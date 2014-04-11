package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.ServiceFactory;

/**
 * Created by Sergey Popov on 4/7/2014.
 */
public class RemoveClientCommand implements Command {
	@Override
	public void execute() throws ClientNotFoundException, DAOException {
		if (BankCommander.activeClient == null) {
			throw new ClientNotFoundException("Active client not found. Please, find the client you need first");
		}

		ServiceFactory.getBankService().removeClient(BankCommander.activeBank, BankCommander.activeClient);
		BankCommander.activeClient = null;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Remove client");
	}
}
