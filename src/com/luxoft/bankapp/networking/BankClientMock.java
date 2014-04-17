package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Sergey Popov on 15.04.14.
 */
public class BankClientMock extends BankClientBase implements Callable {
	Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	String message;
	protected static final String SERVER = "localhost";
	protected static final int PORT = 2014;
	protected List<String> commands;
	private Client client;

    public BankClientMock(Client client) {
        this.client = client;
    }

	@Override
	public Object call() {
		String[] cmdPerform = {
				"authorize " + client.getName(),
				"withdraw 1.00",
				"exit"
		};
		int k = 0;
		long startTime = 0;
		long stopTime = 0;
		try {
			startTime = System.currentTimeMillis();

			requestSocket = new Socket(SERVER, PORT);
			//System.out.println("Connected to localhost in port " + PORT);
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			do {
				try {
					message = (String) in.readObject();

					//System.out.println("server > " + message);
					message = cmdPerform[k++];
					out.writeObject(message);
					out.flush();
				} catch (ClassNotFoundException classNot) {
					System.err.println("dao received in unknown format");
				}
			} while (!message.equals("exit"));
			stopTime = System.currentTimeMillis();
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		return stopTime - startTime;
	}
}
