package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.ServiceFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Sergey Popov on 4/15/2014.
 */
public class BankServerThreaded {
	static final int PORT = 2014;
	final int POOL_SIZE = 10;
	//static AtomicInteger connectQueueCount = new AtomicInteger(0);

	public static void main(String[] args) {
		BankServerThreaded bankServer = new BankServerThreaded();
		System.out.println("Waiting for connection");

		try {
			Bank bank = ServiceFactory.getBankService().getBank("My Bank");

			ServerSocket serverSocket = new ServerSocket(PORT);
			ExecutorService pool = Executors.newFixedThreadPool(bankServer.POOL_SIZE);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				//connectQueueCount.incrementAndGet();
				pool.execute(new ServerThread(clientSocket, bank));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
