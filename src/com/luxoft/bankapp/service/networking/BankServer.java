package com.luxoft.bankapp.service.networking;

import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sergey Popov on 4/1/2014.
 */
public class BankServer {
	private Bank activeBank;
	private Client activeClient;

	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;

	String command;
	String commandParam;

	void run() {
		if (activeBank == null) {
			System.out.println("Active bank not found");
			System.exit(0);
		}

		try {
			// 1. creating a server socket
			providerSocket = new ServerSocket(2014, 10);
			// 2. Wait for connection
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from "
					+ connection.getInetAddress().getHostName());
			// 3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			// 4. The two parts communicate via the input and output streams
			do {
				try {
					message = (String) in.readObject();

					int spaceIndex = message.indexOf(" ");
					if (spaceIndex == -1) {
						// Command without parameters
						command = message;
					} else {
						command = message.substring(0, spaceIndex);
						commandParam = message.substring(spaceIndex + 1);
					}


					System.out.println("client > " + message);
					//String[] splittedCmd = message.split(" ");
					switch (command) {
						case "exit":
							sendMessage("Connection has been closed");
							activeClient = null;
							break;
						case "authorize":
							if(findClientAndSetActive(commandParam)) {
								sendMessage("Authorization OK");
							} else {
								sendMessage("Client " + commandParam + " not found");
							}
							break;
						case "getBalance":
							if (activeClient == null) {
								sendMessage("You have to authorize for this operation");
							} else {
								sendMessage(Float.toString(activeClient.getBalance()));
							}
							break;
						case "withdraw":
							try {
								activeClient.withdraw(Float.parseFloat(commandParam));
								sendMessage("Operation complete successfully");
							} catch (DataVerifyException e) {
								sendMessage(e.getMessage());
							} catch (NoEnoughFundsException e) {
								sendMessage(e.getMessage());
							}
							break;
						default:
							sendMessage("Unknown command");
					}




				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}
			} while (!message.equals("exit"));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server > " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private boolean findClientAndSetActive(String clientName) {
		return (activeClient = activeBank.findClientByName(clientName)) != null;
	}

	public static void main(final String args[]) {
		BankServer server = new BankServer();
		server.activeBank = new Bank("Server bank");

		BankApplication.initialize(server.activeBank);

		while (true) {
			server.run();
		}
	}



}
