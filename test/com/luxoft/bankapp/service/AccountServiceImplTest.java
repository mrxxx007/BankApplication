package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Sergey Popov on 12.04.14.
 */
public class AccountServiceImplTest {
    Bank bank;
    Client client1 = new Client("IvanovTestName", 0f);
    Client client2 = new Client("PetrovTestName", 0f);

    @Before
    public void setUp() throws Exception {
        bank = ServiceFactory.getBankDAO().getBankByName("My Bank");

        client1.setGender(Gender.MALE);
        client1.setCity("Saint-Petersburg");
        client1.setPhone("+78121234567");
        client1.setEmail("ivanov@mail.com");
        client1.setBankId(bank.getId());
        ServiceFactory.getClientService().addAccount(client1, new CheckingAccount(1000, 5000));
        ServiceFactory.getClientDAO().save(client1);

        client2.setGender(Gender.MALE);
        client2.setCity("Moscow");
        client2.setPhone("+74951234567");
        client2.setEmail("petrov@mail.com");
        client2.setBankId(bank.getId());
        ServiceFactory.getClientService().addAccount(client2, new CheckingAccount(0, 1000));
        ServiceFactory.getClientDAO().save(client2);
    }

    @After
    public void tearDown() throws Exception {
        ServiceFactory.getBankService().removeClient(bank, client1);
    }

    @Test
    public void testTransfer() throws Exception {
        float startBalanceClient1 = ServiceFactory.getClientService().getBalance(client1, 0);
        float startBalanceClient2 = ServiceFactory.getClientService().getBalance(client2, 0);
        float amount = 100f;
        ServiceFactory.getAccountService().transfer(client1.getAccounts().get(0),
                client2.getAccounts().get(0), amount);

        assertEquals(startBalanceClient1 - amount, ServiceFactory.getClientService().getBalance(client1, 0), 0f);
        assertEquals(startBalanceClient2 + amount, ServiceFactory.getClientService().getBalance(client2, 0), 0f);
    }

    @Test (expected = NoEnoughFundsException.class)
    public void testTransfer2() throws Exception {
        float amount = 3000f;
        ServiceFactory.getAccountService().transfer(client2.getAccounts().get(0),
                client1.getAccounts().get(0), amount);
    }

    @Test
    public void testGetAccountTypeName() throws Exception {
        assertEquals("CHECKING ACCOUNT",
                ServiceFactory.getAccountService().getAccountTypeName(new CheckingAccount(100f, 0f)).toUpperCase());
        assertEquals("SAVING ACCOUNT",
                ServiceFactory.getAccountService().getAccountTypeName(new SavingAccount(100f)).toUpperCase());
    }
}
