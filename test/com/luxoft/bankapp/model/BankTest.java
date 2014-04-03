package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by user on 4/3/2014.
 */
public class BankTest {
	//private final Bank bank = new Bank("Test bank");
	private final Client client1 = new Client(100f);
	private final Client client2 = new Client(200f);

	@Before
	public void setUp() throws Exception {
		client1.setName("Ivanov");
		client1.setGender(Gender.MALE);
		client1.setCity("Saint-Petersburg");
		client1.setPhone("+78121234567");
		client1.setEmail("ivanov@mail.com");

		client2.setName("Petrova");
		client2.setGender(Gender.FEMALE);
		client2.setCity("Saint-Petersburg");
		client2.setPhone("+78128901234");
		client2.setEmail("petrova@mail.com");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGetClientsList() throws Exception {
		Bank bank = new Bank("Test bank");
		bank.addClient(client1);
		bank.addClient(client2);

		List<Client> clientList = bank.getClientsList();
		assertNotNull(clientList);
		assertEquals(2, clientList.size());
		assertTrue(clientList.contains(client1));
		assertTrue(clientList.contains(client2));
	}

	@Test(expected = ClientExistsException.class)
	public void testAddClient() throws Exception {
		Bank bank = new Bank("Test bank");
		bank.addClient(client1);
		assertTrue(bank.getClientsList().contains(client1));

		// ClientExistsException
		bank.addClient(client1);
	}

	@Test
	public void testRemoveClient() throws Exception {
		Bank bank = new Bank("Test bank");

		bank.addClient(client1);
		assertTrue(bank.getClientsList().contains(client1));

		bank.removeClient(client1);
		assertFalse(bank.getClientsList().contains(client1));
	}

	@Test
	public void testFindClientByName() throws Exception {
		Bank bank = new Bank("Test bank");
		assertNull(bank.findClientByName("Ivanov"));
		bank.addClient(client1);
		assertNotNull(bank.findClientByName("Ivanov"));
	}

	@Test
	public void testParseFeed() throws Exception {

	}
}
