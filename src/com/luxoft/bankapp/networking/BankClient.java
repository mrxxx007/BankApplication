package com.luxoft.bankapp.networking;

/**
 * Created by Sergey Popov on 4/1/2014.
 */
public class BankClient extends BankClientBase {

	public static void main(final String args[]) {
		BankClient client = new BankClient();
		client.commands = ClientServerCommands.getBankClientCommands();
		client.run();
	}
}
