package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.BankInfo;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Sergey Popov on 4/7/2014.
 */
public class ReportCommand implements Command {
	@Override
	public void execute() throws IOException, ClientNotFoundException, DAOException {
		BankInfo bankInfo = new BankServiceImpl().getBankInfo(BankCommander.activeBank);
		System.out.println("--------= BANK INFO =--------");
		System.out.println("Number of clients: " + bankInfo.getNumberOfClients());
		System.out.println("Total accounts sum: " + bankInfo.getTotalAccountSum());
		System.out.println("Clients, sorted by cities:");
		for (Map.Entry entry : bankInfo.getClientsByCity().entrySet()) {
			System.out.println("  == " + entry.getKey() + " ==");
			System.out.println(entry.getValue());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Print bank report");
	}
}
