package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by user on 4/3/2014.
 */
public class BankFeedServiceTest {
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testLoadFeed() throws Exception {
		Bank bank = new Bank("Test");
		final String feedStr = "accounttype=c;balance=100;overdraft=50;" +
				"name=John Smith;gender=m;city=London;phone=1234567890;email=john@mail.com";
		BufferedWriter bw = new BufferedWriter(new FileWriter("dao/test.feed"));
		bw.write(feedStr);
		bw.close();

		assertTrue(bank.getClientsList().isEmpty());
		BankFeedService.loadFeed(bank, "dao/test.feed");
		assertFalse(bank.getClientsList().isEmpty());

		Client client = bank.findClientByName("John Smith");
		assertNotNull(client);

		assertEquals(100, client.getAccounts().get(0).getBalance(), 0);
		assertEquals("London", client.getCity());
	}
}
