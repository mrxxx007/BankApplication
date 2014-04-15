package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Before;
import org.junit.Test;
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

        Client client = new Client("Test Ivanov Ivan", 0f);
        try {
            Bank bank = ServiceFactory.getBankDAO().getBankByName("My Bank");
            client.setBankId(bank.getId());

            Account acc = new SavingAccount(5000f);
            ServiceFactory.getClientService().addAccount(client, acc);
            ServiceFactory.getClientDAO().save(client);
            //BankServerThreaded.main(null);

            BankServerThreaded bankServerThreaded = new BankServerThreaded();
            //bankServerThreaded.runServer();
            Thread srvThread = new Thread(bankServerThreaded);
            srvThread.start();

            BankClientMock bankClientMock = new BankClientMock(client);
            bankClientMock.runWithdrawCommand(1f);

            srvThread.join();

            assertEquals(4999f, client.getBalance(), 0f);

            ServiceFactory.getClientDAO().remove(client);
            bankServerThreaded.stopServer();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
