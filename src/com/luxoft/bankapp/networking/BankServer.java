package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.AccountServiceImpl;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

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
								//activeClient.withdraw(Float.parseFloat(commandParam));
								new AccountServiceImpl().withdraw(activeClient.getAccounts().get(0),
										Float.parseFloat(commandParam));
								sendMessage("Operation complete successfully");
							} catch (DataVerifyException e) {
								sendMessage(e.getMessage());
							} catch (NoEnoughFundsException e) {
								sendMessage(e.getMessage());
							} catch (AccountNotFoundException e) {
								e.printStackTrace();
							}
							break;
						case "addClient":
							Client client = (Client)in.readObject();
							//activeBank.addClient(client);
							new BankServiceImpl().addClient(activeBank, client);
							sendMessage("The client was successfully added");
							break;
						case "getClientInfo":
							if (activeClient == null) {
								sendMessage("You have to authorize for this operation");
							} else {
								sendMessage(activeClient.toString());
							}
							break;
						case "deleteClient":
							//Client findingClient = activeBank.findClientByName(commandParam);
							BankService bankService = new BankServiceImpl();
							Client findingClient = bankService.findClientByName(activeBank, commandParam);
							if (findingClient == null) {
								throw new ClientNotFoundException(commandParam);
							} else {
								bankService.removeClient(activeBank, findingClient);
								sendMessage("The client removed successfully");
							}
							break;
						default:
							sendMessage("Unknown command");
					}

				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				} catch (ClientExistsException e) {
					sendMessage(e.getMessage());
				} catch (ClientNotFoundException e) {
					sendMessage(e.getMessage());
				} catch (DAOException e) {
					sendMessage(e.getMessage());
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

	private boolean findClientAndSetActive(String clientName) throws ClientNotFoundException {
		return (activeClient =
				new BankServiceImpl().findClientByName(activeBank, clientName)) != null;

	}

	public static void main(final String args[]) {
		BankServer server = new BankServer();
		try {
			server.activeBank = new BankServiceImpl().getBank("My Bank");
		} catch (DAOException e) {
			System.out.println(e.getMessage());
		}

		//BankApplication.initialize(server.activeBank);

		while (true) {
			server.run();
		}
	}



}
