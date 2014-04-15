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
public class BankServerThreaded implements Runnable{
	final int PORT = 2014;
	final int POOL_SIZE = 10;
    boolean shouldStop = false;
	static AtomicInteger connectQueueCount = new AtomicInteger(0);

    /*public void runServer() {

    }*/

    public void stopServer() {
        shouldStop = true;
    }

	public static void main(String[] args) {
		new BankServerThreaded().run();
    }

    @Override
    public void run() {
        System.out.println("Waiting for connection");
        BankServerMonitor bankServerMonitor = new BankServerMonitor();
        (new Thread(bankServerMonitor)).start();

        try {
            Bank bank = ServiceFactory.getBankService().getBank("My Bank");

            ServerSocket serverSocket = new ServerSocket(PORT);
            ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

            while (!shouldStop) {
                Socket clientSocket = serverSocket.accept();
                connectQueueCount.incrementAndGet();
                pool.execute(new ServerThread(clientSocket, bank));
                //TODO Stop server
            }
            System.out.println("stopping server");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("stopping monitor");
            bankServerMonitor.stop();
        }
    }
}
