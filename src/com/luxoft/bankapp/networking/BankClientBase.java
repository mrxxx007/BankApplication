package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.commands.AddClientCommand;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Admin on 01.04.2014.
 */
public class BankClientBase {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	String value;
	int cmdNumber;
	protected static final String SERVER = "localhost";
    protected static final int PORT = 2014;
	protected List<String> commands;

	void run() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);

		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket(SERVER, PORT);
			System.out.println("Connected to localhost in port " + PORT);
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {
					message = (String) in.readObject();
					System.out.println("server > " + message);

					// print commands menu
					printMenu();
					System.out.print(" -> ");

					// read cmd number and put cmd name to message string and send message
					cmdNumber = Integer.parseInt(bufferedReader.readLine());
					generateAnsSendCmd(cmdNumber, bufferedReader);
					System.out.println("");
				} catch (ClassNotFoundException classNot) {
					System.err.println("dao received in unknown format");
				} catch (DataVerifyException e) {
					System.out.println(e.getMessage());
				}
			} while (!message.equals("exit"));
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void printMenu() {
		System.out.println("\n====== Menu ======");

		if (commands == null) {
			System.out.println("Null commands");
			return;
		}
		int i = 0;
		for (String str : commands) {
			System.out.println(i++ + ") " + str);
		}
	}

    protected void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client > " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	protected void generateAnsSendCmd(int cmdNumber, BufferedReader inputStream)
			throws IOException, ClassNotFoundException, DataVerifyException {
		String value;
		String cmdName = commands.get(cmdNumber);
		switch (cmdName) {
			case "authorize":
				System.out.print("Enter your full name: ");
				value = inputStream.readLine();
				message = cmdName + " " + value;
				break;
			case "exit":
			case "getClientInfo":
			case "getBalance":
				message = cmdName;
				break;
			case "withdraw":
				System.out.print("Enter sum to withdraw: ");
				value = inputStream.readLine();
				message = cmdName + " " + value;
				break;
			case "addClient":
				Client client = new AddClientCommand().getClientInfoDialog();
				sendMessage(cmdName);
				out.writeObject(client);
				// if not return we duplicate command to server
				return;
			case "deleteClient":
				System.out.print("Enter full client name: ");
				value = inputStream.readLine();
				message = cmdName + " " + value;
				break;
			default:
				//TODO Add new exception CmdNotFound
				break;
		}

		sendMessage(message);
	}
}
