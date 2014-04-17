package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.junit.Assert.*;

/**
 * Created by Sergey Popov on 15.04.14.
 */
public class BankServerThreadedTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testWithdrawWithManyThreads() throws Exception{
        Client client = new Client("Test Ivanov 123", 0f);

		Bank bank = ServiceFactory.getBankDAO().getBankByName("My Bank");
		client.setBankId(bank.getId());

		Account acc = new SavingAccount(4000f);
		ServiceFactory.getClientService().addAccount(client, acc);
		ServiceFactory.getClientDAO().save(client);
		client = ServiceFactory.getBankService().findClientByName(bank, client.getName());
		float balance = ServiceFactory.getClientService().getBalance(client, 0);

		BankServerThreaded bankServerThreaded = new BankServerThreaded();

		Thread srvThread = new Thread(bankServerThreaded);
		srvThread.start();

		int threadsNum =30;
		List<Future<Long>> threadsFuture = new ArrayList<>();
		List<Thread> threads = new ArrayList<>(threadsNum);
		for (int i = 0; i < threadsNum; i++) {
			Callable<Long> callable = new BankClientMock(client);
			FutureTask<Long> task = new FutureTask<>(callable);

			threads.add(new Thread(task));
			threadsFuture.add(task);
			threads.get(i).setName("thr-" + i);
		}
		for (int i = 0; i < threadsNum; i++) {
			threads.get(i).start();
		}
		for (int i = 0; i < threadsNum; i++) {
			threads.get(i).join();
		}

		bankServerThreaded.stopServer();
		srvThread.join();

		client = ServiceFactory.getClientDAO().findClientByName(bank, client.getName());

		float newBalance = ServiceFactory.getClientService().getBalance(client, 0);
		ServiceFactory.getClientDAO().remove(client);

		//System.out.println("Balance: " + newBalance);

		long avgTime = 0;
		for (Future future : threadsFuture) {
			avgTime += ((Long)future.get()).longValue();
		}
		avgTime /= threadsNum;
		System.out.println("Average time: " + avgTime + " ms");

		assertEquals(balance-threadsNum, newBalance, 0f);
	}

}
