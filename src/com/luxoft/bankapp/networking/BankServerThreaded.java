package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.ServiceFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Sergey Popov on 4/15/2014.
 */
public class BankServerThreaded implements Runnable{
	final int PORT = 2016;
	final int POOL_SIZE = 10;



	boolean shouldStop = false;
	static AtomicInteger connectQueueCount = new AtomicInteger(0);
	//Socket clientSocket;
	ServerSocket serverSocket;

    /*public void runServer() {

    }*/

	public void setShouldStop(boolean shouldStop) {
		this.shouldStop = shouldStop;
	}

    public void stopServer() {
        shouldStop = true;
		try {
			serverSocket.close();
			System.out.println("call close");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new BankServerThreaded().run();
    }

    @Override
    public void run() {
        System.out.println("Waiting for connection");
        BankServerMonitor bankServerMonitor = new BankServerMonitor();
        (new Thread(bankServerMonitor)).start();
		ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

		Socket clientSocket;

        try {
            Bank bank = ServiceFactory.getBankService().getBank("My Bank");

            serverSocket = new ServerSocket(PORT);
			serverSocket.setSoTimeout(1000);

            while (true) {
				clientSocket = serverSocket.accept();

                connectQueueCount.incrementAndGet();
                pool.execute(new ServerThread(clientSocket, bank));

				if (shouldStop) {
					/*while (!future.isDone()) {
						System.out.println(future.isDone());
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}*/
					break;
				}
            }
            System.out.println("stopping server");
        } catch (SocketTimeoutException e) {
            System.out.println("Server socket has been closed");
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
			pool.shutdown();
            System.out.println("stopping monitor");
            bankServerMonitor.stop();
        }
    }
}
