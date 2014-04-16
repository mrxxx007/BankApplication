package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Admin on 15.04.14.
 */
public class BankServerThreadedTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testWithdrawWithManyThreads() {

        Client client = new Client("Test Ivanov 123", 0f);
        try {
            Bank bank = ServiceFactory.getBankDAO().getBankByName("My Bank");
            client.setBankId(bank.getId());

            Account acc = new SavingAccount(4000f);
            ServiceFactory.getClientService().addAccount(client, acc);
            ServiceFactory.getClientDAO().save(client);
			client = ServiceFactory.getClientDAO().findClientByName(bank, client.getName());
			float balance = ServiceFactory.getClientService().getBalance(client, 0);

            BankServerThreaded bankServerThreaded = new BankServerThreaded();

            //bankServerThreaded.runServer();
            Thread srvThread = new Thread(bankServerThreaded);
            srvThread.start();

//            BankClientMock bankClientMock = new BankClientMock(client);
//			Thread clientThread = new Thread(bankClientMock);
//			clientThread.start();

            int threadsNum = 10;
            List<Thread> threads = new ArrayList<>(threadsNum);
            for (int i = 0; i < threadsNum; i++) {
                threads.add(new Thread(new BankClientMock(client)));
            }
            for (int i = 0; i < threadsNum; i++) {
                threads.get(i).start();
            }
            for (int i = 0; i < threadsNum; i++) {
                threads.get(i).join();
            }

            bankServerThreaded.stopServer();
			//System.out.println("join client");
			//clientThread.join();

			System.out.println("join srv");
			srvThread.join();

			System.out.println("after join");

            client = ServiceFactory.getClientDAO().findClientByName(bank, client.getName());
			float newBalance = ServiceFactory.getClientService().getBalance(client, 0);
			ServiceFactory.getClientDAO().remove(client);

            System.out.println("Balance: " + newBalance);
            assertEquals(balance-threadsNum, newBalance, 0f);

			//srvThread.interrupt();
			//bankServerThreaded.serverSocket.accept().close();



        } catch (DAOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
			e.printStackTrace();
		}
	}

}
