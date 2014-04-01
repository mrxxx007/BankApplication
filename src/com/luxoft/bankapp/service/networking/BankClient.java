package com.luxoft.bankapp.service.networking;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

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
