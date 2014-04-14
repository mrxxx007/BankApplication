package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NoEnoughFundsException;
import com.luxoft.bankapp.model.*;
import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admin on 12.04.14.
 */
public class ClientServiceImplTest {
    static Bank bank;
    static Client client1 = new Client("IvanovTestName", 0f);


    @BeforeClass
    public static void setUp() throws Exception {
        bank = ServiceFactory.getBankDAO().getBankByName("My Bank");

        client1.setGender(Gender.MALE);
        client1.setCity("Saint-Petersburg");
        client1.setPhone("+78121234567");
        client1.setEmail("ivanov@mail.com");
        client1.setBankId(bank.getId());
        ServiceFactory.getClientService().addAccount(client1, new CheckingAccount(1000, 5000));
        ServiceFactory.getClientDAO().save(client1);


    }

    @AfterClass
    public static void tearDown() throws Exception {
        ServiceFactory.getBankService().removeClient(bank, client1);
    }

    @Test
    public void testWithdraw() throws Exception {
        float balance = ServiceFactory.getClientService().getBalance(client1, 0);
        ServiceFactory.getClientService().withdraw(client1, 0, 200f);
        assertEquals(balance - 200f, ServiceFactory.getClientService().getBalance(client1, 0), 0.001);
    }

	@Test (expected = NoEnoughFundsException.class)
	public void testWithdrawWithNoEnoughFounds() throws Exception {
		ServiceFactory.getClientService().withdraw(client1, 0, 20000f);
	}

    @Test
    public void testDeposit() throws Exception {
        float balance = ServiceFactory.getClientService().getBalance(client1, 0);
        ServiceFactory.getClientService().deposit(client1, 0, 200f);
        assertEquals(balance + 200f, ServiceFactory.getClientService().getBalance(client1, 0), 0.001);
    }

    @Test
    public void testSetActiveAccount() throws Exception {
        if (client1.getActiveAccount() != null) {
            ServiceFactory.getClientService().setActiveAccount(client1, null);
            assertNull(client1.getActiveAccount());
        } else {
            ServiceFactory.getClientService().setActiveAccount(client1, new SavingAccount(500f));
            assertNotNull(client1.getActiveAccount());
            assertEquals(500f, ServiceFactory.getAccountService().getBalance(client1.getActiveAccount()), 0f);
        }
    }

    @Test
    public void testSaveLoadClient() throws Exception {
		Client testSaveClient = new Client("Test Client", 0f);
		testSaveClient.setGender(Gender.MALE);
		testSaveClient.setCity("Moscow");
		testSaveClient.setPhone("+74951234567");
		testSaveClient.setEmail("test@mail.com");
		testSaveClient.setBankId(bank.getId());
		ServiceFactory.getClientService().addAccount(testSaveClient, new CheckingAccount(100f, 100f));
		ServiceFactory.getClientService().addAccount(testSaveClient, new SavingAccount(9000f));

        ServiceFactory.getClientService().saveClient(testSaveClient);
        Client readedClient = ServiceFactory.getClientService().loadClient();

        assertNotNull(readedClient);
        assertEquals(testSaveClient.getName(), readedClient.getName());
        assertEquals(testSaveClient.getGender(), readedClient.getGender());
        assertEquals(2, readedClient.getAccounts().size());
        assertEquals(ServiceFactory.getClientService().getBalance(testSaveClient, 0),
                ServiceFactory.getClientService().getBalance(readedClient, 0), 0f);
    }
}
