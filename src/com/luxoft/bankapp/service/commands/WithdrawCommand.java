package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.exceptions.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Admin on 27.03.2014.
 */
public class WithdrawCommand implements Command {
	private static String name = "Withdraw";
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

			BankCommander.activeClient.withdraw(amount);
		} catch (DataVerifyException e) {
			System.out.println(e.getMessage());
		} catch (NoEnoughFundsException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
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
