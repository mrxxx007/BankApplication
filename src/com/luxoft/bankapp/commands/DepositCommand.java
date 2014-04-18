package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.ServiceFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class DepositCommand implements Command {

	@Override
	public void execute() throws BankException, DAOException, IOException{
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		if (BankCommander.activeClient == null) {
			throw new NotFoundException("Active client not found. Please, find the client you need first");
		}
		System.out.print("Enter amount to deposit:\n -> ");

		float amount = Float.parseFloat(bufferedReader.readLine());
		ServiceFactory.getClientService().deposit(BankCommander.activeClient, 0, amount);
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit");
	}
}
