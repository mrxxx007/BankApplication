package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.service.commands.BankCommander;
import com.luxoft.bankapp.service.commands.Command;

import java.io.IOException;
import java.util.Date;

/**
 * Created by user on 3/25/2014.
 */
public class BankApplication {
    public static void main(String[] args) {
        // Create new bank with DebugListener via new constructor
        /*ClientRegistrationListener[] listenersArray = {
                new ClientRegistrationListener() {
                    @Override
                    public void onClientAdded(Client c) {
                        System.out.println("---- Debug listener ----");
                        System.out.println(c.getName());
                        System.out.println(new Date());
                        System.out.println();
                    }
                }
        };
        Bank bank = new Bank("Luxoft bank", listenersArray);*/
		Bank bank = new Bank("Luxoft Bank");


        initialize(bank);

		BankReport.getClientsSorted(bank);

		for (String arg : args)
			if (arg.equals("-report")) {
				BankCommander.registerCommand("Print bank report", new Command() {
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

		BankCommander.startBankCommander(bank);
        //printBankReport(bank);
        //modifyBank(bank);
        //printBankReport(bank);
	}

    public static void initialize(Bank bank) {
        BankServiceImpl bankService = new BankServiceImpl();

        Client client1 = new Client(500);
        client1.setName("Ivanov I.P.");
        client1.setGender(Gender.MALE);
		client1.setCity("Moscow");

		try {
			bankService.addClient(bank, client1);
			//bankService.addClient(bank, client1);
		}
		catch (ClientExistsException ex) {
			System.out.println("== !");
			System.out.println("== ERROR: The client " + client1.getName() + " is already exists\n");
		}
        bankService.addAccount(client1, new CheckingAccount(200));
        bankService.addAccount(client1, new SavingAccount(700));
		bankService.setActiveAccount(client1, client1.getAccounts().get(0));


        Client client2 = new Client();
        client2.setName("Petrova S.P.");
        client2.setGender(Gender.FEMALE);
		client2.setCity("Saint Petersburg");
		try {
			bankService.addClient(bank, client2);
		} catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
		bankService.addAccount(client2, new SavingAccount(350));
		bankService.setActiveAccount(client2, client2.getAccounts().get(0));
    }

    public static void modifyBank(Bank bank) {

        bank.getClients().get(0).getAccounts().get(0).deposit(100);
		((CheckingAccount) bank.getClients().get(0).getAccounts().get(0)).setOverdraft(300f);
		try {
			//bank.getClients().get(0).getAccounts().get(1).withdraw(5000);
			bank.getClients().get(0).getAccounts().get(0).withdraw(1500);
		}
		catch (OverDraftLimitExceededException ex) {
			System.out.println("== !\n== ERROR: You haven't enough money");
			System.out.println("== You can take: " + ex.getAvailableMoney());
		}
		catch (NoEnoughFundsException ex) {
			System.out.println("== !\n== ERROR: You haven't enough money\n");
		}
		//bank.getClients().get(1).getAccounts().get(0).withdraw(200);

    }

    static void printBankReport(Bank bank) {
        bank.printReport();
    }
}

