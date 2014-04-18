package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.ServiceFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class FindClientCommand implements Command {

	@Override
	public void execute() throws ClientNotFoundException, IOException {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		System.out.print("Enter client name:\n -> ");
		String clientName = bufferedReader.readLine();
		BankCommander.activeClient =
				ServiceFactory.getBankService().findClientByName(BankCommander.activeBank, clientName);

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find Client");
	}
}
