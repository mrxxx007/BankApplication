package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.exceptions.NotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Admin on 27.03.2014.
 */
public class DepositCommand implements Command {

	@Override
	public void execute() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		try {
			if (BankCommander.activeClient == null) {
				throw new NotFoundException("Active client not found. Please, find the client you need first");
			}
			System.out.print("Enter amount to deposit:\n -> ");

			float amount = Float.parseFloat(bufferedReader.readLine());
			BankCommander.activeClient.deposit(amount);
		} catch (DataVerifyException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoEnoughFundsException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit");
	}
}
