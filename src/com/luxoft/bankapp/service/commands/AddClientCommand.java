package com.luxoft.bankapp.service.commands;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Admin on 27.03.2014.
 */
public class AddClientCommand implements Command {
	@Override
	public void execute() throws IOException {
		boolean isMale;
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		System.out.println("   Adding new client\n");
		try {
			System.out.print("Enter full name:\n -> ");
			String userName = bufferedReader.readLine();
			if (!isValidName(userName)) {
				throw new DataVerifyException("Entered name is incorrect");
			}

			System.out.print("Enter initial overdraft for client:\n -> ");
			float initOverdraft = Float.parseFloat(bufferedReader.readLine());

			System.out.print("Enter client gender (male/female):\n -> ");
			String sgender = bufferedReader.readLine().toUpperCase();
			Gender gender;
			try {
				gender = Gender.valueOf(sgender);
			} catch (IllegalArgumentException ex) {
				throw new DataVerifyException("Incorrect gender information");
			}

			System.out.print("Enter e-mail:\n -> ");
			String userEmail = bufferedReader.readLine();
			if (!isValidEmail(userEmail)) {
				throw new DataVerifyException("The e-mail is incorrect");
			}

			System.out.print("Enter phone number:\n -> ");
			String phoneNumber = bufferedReader.readLine();
			if (!isValidPhone(phoneNumber)) {
				throw new DataVerifyException("Phone number is incorrect");
			}

			System.out.print("Enter your city:\n -> ");
			String userCity = bufferedReader.readLine();
			if (!isValidCityName(userCity)) {
				throw new DataVerifyException("City name is incorrect");
			}

			Account acc = new SavingAccount(0f);

			Client client = new Client(userName, initOverdraft);
			client.setPhone(phoneNumber);
			client.setGender(gender);
			client.setEmail(userEmail);
			client.addAccount(acc);
			client.setActiveAccount(acc);
			client.setCity(userCity);
			BankCommander.activeBank.addClient(client);
		}
		catch (DataVerifyException ex) {
			System.out.println(ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		catch (ClientExistsException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Add Client");
	}

	private boolean isValidPhone(String phone){
		return phone.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
	}
	private boolean isValidEmail(String email){
		return email.matches("^[A-Za-z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}");
	}
	private boolean isValidName(String name){
		return name.matches("[A-Za-zА-Яа-я ]*");
	}
	private boolean isValidCityName(String name){
		return name.matches("[A-Za-zА-Яа-я -]*");
	}
}
