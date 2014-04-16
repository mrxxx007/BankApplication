package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Sergey Popov on 15.04.14.
 */
public class BankClientMock extends BankClientBase implements Runnable {
	Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	String message;
	String value;
	int cmdNumber;
	protected static final String SERVER = "localhost";
	protected static final int PORT = 2016;
	protected List<String> commands;
	private Client client;

    public BankClientMock(Client client) {
        this.client = client;
    }

	@Override
	public void run() {
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		String[] cmdPerform = {"authorize " + client.getName(), "withdraw 500.00", "exit"};
		int k = 0;

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
					//printMenu();
					System.out.print(" -> ");

					// read cmd number and put cmd name to message string and send message
					//cmdNumber = Integer.parseInt(bufferedReader.readLine());
					//generateAnsSendCmd(cmdNumber, bufferedReader);
					//sendMessage(cmdPerform[k++]);
					message = cmdPerform[k++];
					out.writeObject(message);
					out.flush();
					System.out.println("");
				} catch (ClassNotFoundException classNot) {
					System.err.println("dao received in unknown format");
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
}
