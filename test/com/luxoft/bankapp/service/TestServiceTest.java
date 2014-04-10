package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by user on 4/10/2014.
 */
public class TestServiceTest {
	Bank bank1, bank2;

	@Before
	public void setUp() throws Exception {

		bank1 = new Bank("My Bank");
		bank1.setId(1);
		Client client = new Client ();
		client.setName("Ivan Ivanov");
		client.setCity("Kiev");
        client.addAccount(new CheckingAccount(100f, 700f));

		// add some fields from Client
		// marked as @NoDB, with different values
		// for client and client2
        client.setActiveAccount(new SavingAccount(900f));
        client.setInitialOverdraft(100f);
		bank1.addClient(client);

		bank2 = new Bank("My Bank");
		bank2.setId(2);
		Client client2 = new Client ();
		client2.setName("Ivan Ivanov");
		client2.setCity("Kiev");
		client2.addAccount(new CheckingAccount (100f, 700f));

        client2.setActiveAccount(new SavingAccount(2900f));
        client2.setInitialOverdraft(400f);
		bank2.addClient(client2);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testIsEquals() throws Exception {
		assertTrue(TestService.isEquals(bank1, bank2));



	}
}
