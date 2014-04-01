package com.luxoft.bankapp.service.networking;

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created by Sergey Popov on 4/1/2014.
 */
public class BankClient {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	String value;
	static final String SERVER = "localhost";

	void run() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		Map<String, String> commands;

		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket(SERVER, 2014);
			System.out.println("Connected to localhost in port 2004");
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
					commands = ClientServerCommands.getAllCommands();
					System.out.println("\n====== Menu ======");
					for (Map.Entry entry : commands.entrySet()) {
						System.out.println(entry.getKey() + ") " + entry.getValue());
					}
					System.out.print(" -> ");
					// read cmd number and put cmd name to message string
					value = commands.get(bufferedReader.readLine());
					message = generateCmdString(value, bufferedReader);
					sendMessage(message);

					System.out.println("");
				} catch (ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
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

	void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client > " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(final String args[]) {
		BankClient client = new BankClient();
		client.run();
	}

	private String generateCmdString(String cmdName, BufferedReader inputStream)
			throws IOException, ClassNotFoundException {
		String value;
		switch (cmdName) {
			case "authorize":
				System.out.print("Enter your full name: ");
				value = inputStream.readLine();
				return cmdName + " " + value;
			case "exit":
			case "getBalance":
				return cmdName;
			case "withdraw":
				System.out.print("Enter sum to withdraw: ");
				value = inputStream.readLine();
				return cmdName + " " + value;
			default:
				//TODO Add new exception CmdNotFound etc.
				return "";

		}
	}
}
