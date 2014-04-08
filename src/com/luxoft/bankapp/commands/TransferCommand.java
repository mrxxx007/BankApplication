package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.AccountServiceImpl;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class TransferCommand implements Command {
	@Override
	public void execute() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		try {
			if (BankCommander.activeClient == null) {
				throw new NotFoundException("Active client not found. Please, find the client you need first");
			}

			System.out.print("Enter amount you want to transfer:\n -> ");

			float amount = Float.parseFloat(bufferedReader.readLine());

			System.out.print("Enter receiver client name:\n -> ");
			String clientName = bufferedReader.readLine();

			//Client receiver = BankCommander.activeBank.findClientByName(clientName);
			//Client receiver = new ClientDAOImpl().findClientByName(BankCommander.activeBank, clientName);
			AccountService accountService = new AccountServiceImpl();
			Client receiver = new BankServiceImpl().findClientByName(BankCommander.activeBank, clientName);
			if (receiver == null) {
				throw new NotFoundException("The receiver " + clientName + " not found");
			}

			//BankCommander.activeClient.withdraw(amount);
			accountService.withdraw(BankCommander.activeClient.getAccounts().get(0), amount);
			//receiver.deposit(amount);
			accountService.deposit(receiver, amount);

//			ClientDAO clientDAO = new ClientDAOImpl();
//			clientDAO.save(BankCommander.activeClient);
//			clientDAO.save(receiver);
			System.out.printf("Transfer from %s to %s complete",
					BankCommander.activeClient.getName(), receiver.getName());
		}
		catch (DataVerifyException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoEnoughFundsException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Transfer");
	}
}
