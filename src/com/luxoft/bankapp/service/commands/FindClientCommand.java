package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class FindClientCommand implements Command {

	@Override
	public void execute() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		try {
			System.out.print("Enter client name:\n -> ");
			String clientName = bufferedReader.readLine();

			Client c = BankCommander.activeBank.findClientByName(clientName);
			if (c == null) {
				throw new ClientNotFoundException(clientName);
			}
			BankCommander.activeClient = c;
			System.out.println("The client " + BankCommander.activeClient.getName() + " was set as active");
		} catch (ClientNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find Client");
	}
}
