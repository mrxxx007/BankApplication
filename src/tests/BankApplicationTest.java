package tests;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public class BankApplicationTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testSerializableClient() {
		Bank bank = new Bank("Test");
		BankServiceImpl bankService = new BankServiceImpl();

		Client client1 = new Client(500);
		client1.setName("Ivanov I.P.");
		client1.setGender(Gender.MALE);
		client1.setCity("Moscow");

		try {
			bankService.addClient(bank, client1);
		}
		catch (ClientExistsException ex) {
			System.out.println(ex.getMessage());
		}
		bankService.addAccount(client1, new CheckingAccount(200));
		bankService.addAccount(client1, new SavingAccount(700));
		bankService.setActiveAccount(client1, client1.getAccounts().get(0));
		bankService.saveClient(client1);
		Client readedClient = bankService.loadClient();

		assertNotNull(readedClient);
		assertEquals(readedClient.getName(), "Ivanov I.P.");
		assertEquals(readedClient.getClientSalutation(), Gender.MALE.getSalut());
		assertEquals(readedClient.getAccounts().size(), 2);
	}
}
