package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.ServiceFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sergey Popov on 4/15/2014.
 */
public class ServerThread implements Runnable{
	//Socket clientSocket;
	private Bank activeBank;
	private Client activeClient;

	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;

	String command;
	String commandParam;

	public ServerThread(Socket clientSocket, Bank bank) {
		connection = clientSocket;
		activeBank = bank;
	}

	@Override
	public void run() {
        BankServerThreaded.connectQueueCount.getAndDecrement();

		try {
            if (activeBank == null) {
                throw new BankNotFoundException("null");
            }
			// 1. creating a server socket
			//providerSocket = new ServerSocket(2014, 10);
			// 2. Wait for connection
			//System.out.println("Waiting for connection");
			//connection = providerSocket.accept();

//			System.out.println("Connection received from "
//					+ connection.getInetAddress().getHostName());

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

					//System.out.println("client > " + message);
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
								ServiceFactory.getClientService().withdraw(activeClient, 0, Float.parseFloat(commandParam));

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
							ServiceFactory.getBankService().addClient(activeBank, client);
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
							//BankService bankService = new BankServiceImpl();
							Client findingClient = ServiceFactory.getBankService().findClientByName(activeBank, commandParam);
							if (findingClient == null) {
								throw new ClientNotFoundException(commandParam);
							} else {
								ServiceFactory.getBankService().removeClient(activeBank, findingClient);
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
		} catch (BankNotFoundException e) {
            e.printStackTrace();
        } finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				//providerSocket.close();
				connection.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			//System.out.println("server > " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private boolean findClientAndSetActive(String clientName) throws ClientNotFoundException {
		return (activeClient =
				ServiceFactory.getBankService().findClientByName(activeBank, clientName)) != null;
	}
}
