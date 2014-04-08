package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.DAO.ClientDAO;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class FindClientCommand implements Command {

	@Override
	public void execute() throws ClientNotFoundException {
//		InputStreamReader streamReader = new InputStreamReader(System.in);
//		BufferedReader bufferedReader = new BufferedReader(streamReader);
//
//		try {
//			System.out.print("Enter client name:\n -> ");
//			String clientName = bufferedReader.readLine();
//
//			Client c = BankCommander.activeBank.findClientByName(clientName);
//			if (c == null) {
//				throw new ClientNotFoundException(clientName);
//			}
//			BankCommander.activeClient = c;
//			System.out.println("The client " + BankCommander.activeClient.getName() + " was set as active");
//		} catch (ClientNotFoundException e) {
//			System.out.println(e.getMessage());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		try {
			System.out.print("Enter client name:\n -> ");
			String clientName = bufferedReader.readLine();
			BankCommander.activeClient =
					new BankServiceImpl().findClientByName(BankCommander.activeBank, clientName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find Client");
	}
}
