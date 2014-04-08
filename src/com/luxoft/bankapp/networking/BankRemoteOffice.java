package com.luxoft.bankapp.networking;

/**
 * Created by Sergey Popov on 01.04.2014.
 */
public class BankRemoteOffice extends BankClientBase {

	public static void main(String[] args) {
		BankRemoteOffice client = new BankRemoteOffice();
		client.commands = ClientServerCommands.getBankRemoteOfficeCommands();
		client.run();
	}
}
