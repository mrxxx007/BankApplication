package com.luxoft.bankapp.main;

import com.luxoft.bankapp.DAO.BankDAO;
import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.commands.*;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by Sergey Popov on 3/28/2014.
 */
public class BankCommander {
	public static Client activeClient;
	public static Bank activeBank;
    // Default bank
    private static String bankName = "My Bank";

	private static int commandNum;
	private static Map<String, Command> commands = new TreeMap<>(new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return Integer.parseInt(o1) - Integer.parseInt(o2);
		}
	});
	static {
		registerCommand(new AddClientCommand());
		registerCommand(new FindClientCommand());
		registerCommand(new RemoveClientCommand());
		registerCommand(new GetAccountsCommand());
		registerCommand(new WithdrawCommand());
		registerCommand(new DepositCommand());
		registerCommand(new TransferCommand());
		registerCommand(new ReportCommand());
		registerCommand(new Command() {
			@Override
			public void execute() throws IOException {
				if (activeClient == null)
					System.out.println("No active client");
				else
					System.out.println(activeClient);
			}

			@Override
			public void printCommandInfo() {
				System.out.println("Print active client info");
			}
		});
		registerCommand(new Command() {
			@Override
			public void execute() throws IOException {
				System.exit(0);
			}

			@Override
			public void printCommandInfo() {
				System.out.println("Exit");
			}
		});
	}

	public static void printMenu() {
		if (activeClient == null)
			System.out.println("\n= CLIENT: unknown");
		else
			System.out.println("\n= CLIENT: " + activeClient.getName());
		System.out.println("========== Menu ==========");

		for (Map.Entry entry : commands.entrySet()) {
			System.out.print(entry.getKey() + ") ");
			((Command)entry.getValue()).printCommandInfo();
		}

		System.out.println("==========================");
	}

	public static void startBankCommander(Bank bank) {
		activeBank = bank;

		String commandString;
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		while (true) {
			printMenu();
			System.out.print(" -> ");
			try {
				commandString = bufferedReader.readLine();

				if (commands.get(commandString) == null)
					throw new NumberFormatException();

				commands.get(commandString).execute();
			} catch (NumberFormatException e) {
				System.out.println("Please, enter correct number");
				continue;
			} catch (IndexOutOfBoundsException e) {
				System.out.println("The command not found");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClientNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (DAOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void registerCommand(Command command) {
		commands.put(Integer.toString(commandNum++), command);
	}

	public static void removeCommand(String name) {
		commands.remove(name);
	}

	public static void main(String[] args) {

		try {
			startBankCommander(new BankDAOImpl().getBankByName(bankName));
		} catch (DAOException e) {
			System.out.println(e.getMessage());
		}
	}

}