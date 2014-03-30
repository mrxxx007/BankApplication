package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.NotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class FindClientCommand implements Command {
	private static String name = "Find Client";

	@Override
	public void execute() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		try {
			System.out.print("Enter client name:\n -> ");
			String clientName = bufferedReader.readLine();

			Client c = BankCommander.activeBank.findClientByName(clientName);
			if (c == null) {
				throw new NotFoundException("The client " + clientName + " not found");
			}
			BankCommander.activeClient = c;
			System.out.println("The client " + BankCommander.activeClient.getName() + " was set as active");
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return name;
	}
	@Override
	public void printCommandInfo() {
		System.out.println(name);
	}
}
