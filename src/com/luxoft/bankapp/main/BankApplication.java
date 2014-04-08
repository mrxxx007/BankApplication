package com.luxoft.bankapp.main;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.service.BankFeedService;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.commands.Command;

import java.io.IOException;

/**
 * Created by Sergey Popov on 3/25/2014.
 */
public class BankApplication {
    public static void main(String[] args) {
		Bank bank = null;
		try {
			bank = new BankServiceImpl().getBank("My Bank");
		} catch (DAOException e) {
			System.out.println(e.getMessage());
		}

		for (String arg : args)
			if (arg.equals("-report")) {
				BankCommander.registerCommand(new Command() {
					@Override
					public void execute() throws IOException {
						BankCommander.activeBank.printReport();
					}

					@Override
					public void printCommandInfo() {
						System.out.println("Print bank report");
					}
				});
				break;
			}

		if (bank != null) {
			BankCommander.startBankCommander(bank);
		}
	}
}

