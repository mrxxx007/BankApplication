package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.NotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankServiceImpl;

/**
 * Created by Admin on 27.03.2014.
 */
public class GetAccountsCommand implements Command {
	private static String name = "Get Accounts";
	@Override
	public void execute() {
		try {
			if (BankCommander.activeClient == null) {
				throw new NotFoundException("Active client not found. Please, find the client you need first");
			}
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}

		//BankServiceImpl bankService = new BankServiceImpl();
		int i = 0;
		for (Account acc : BankCommander.activeClient.getAccounts())
		{
			System.out.println("Account #" + ++i);
			System.out.println("  Type: " + acc.getAccountType(acc));
			System.out.println("  Balance: " + acc.getBalance());
			if(acc.getClass().getName().equals(CheckingAccount.class.getName()))
				System.out.println("  Overdraft: " + ((CheckingAccount)acc).getOverdraft());
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
