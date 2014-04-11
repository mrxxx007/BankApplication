package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.ServiceFactory;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.exceptions.NotFoundException;
import com.luxoft.bankapp.main.BankCommander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Admin on 27.03.2014.
 */
public class WithdrawCommand implements Command {
	@Override
	public void execute() {
		try {
			if (BankCommander.activeClient == null) {
				throw new NotFoundException("Active client not found. Please, find the client you need first");
			}

			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			System.out.print("Enter amount you want to withdraw: ");
			float amount = Float.parseFloat(bufferedReader.readLine());

			ServiceFactory.getClientService().withdraw(BankCommander.activeClient, 0, amount);
			ServiceFactory.getClientDAO().save(BankCommander.activeClient);
		} catch (DataVerifyException e) {
			System.out.println(e.getMessage());
		} catch (NoEnoughFundsException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw");
	}
}
