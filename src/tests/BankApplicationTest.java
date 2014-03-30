package tests;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admin on 27.03.2014.
 */
public class BankApplicationTest {
	Client client1;
	Client client2;
	Client client3;

	@Before
	public void setUp() throws Exception {
		client1 = new Client(500);
		client1.setName("Ivanov");
		client1.setGender(Gender.MALE);
		client1.getAccounts().add(new SavingAccount(500));
		client1.getAccounts().add(new CheckingAccount(1000));
		((CheckingAccount) client1.getAccounts().get(1)).setOverdraft(300f);

		client2 = new Client(500);
		client2.setName("Ivanov");
		client2.setGender(Gender.MALE);
		client2.getAccounts().add(new SavingAccount(500));
		client2.getAccounts().add(new CheckingAccount(1000));
		((CheckingAccount) client2.getAccounts().get(1)).setOverdraft(300f);

		client3 = new Client(500);
		client3.setName("Ivanov");
		client3.setGender(Gender.FEMALE);
		client3.getAccounts().add(new SavingAccount(600));
		client3.getAccounts().add(new CheckingAccount(1000));
		((CheckingAccount) client3.getAccounts().get(1)).setOverdraft(100f);

	}

	@Test
	public void testClientEquals() {
		assertTrue(client1.equals(client2));
		assertFalse(client1 == client2);
		assertFalse(client1.equals(client3));
	}
	
	//add test comment 2

	/*@Test
	public void testClientHashCode() {
		assertTrue(client1.hashCode() > 0);
		assertTrue(client1.hashCode() == client2.hashCode());
		assertFalse(client2.hashCode() == client3.hashCode());
	}*/

	@Test
	public void testAccountsEquals() {
		// Saving accounts
		assertTrue(client1.getAccounts().get(0).equals(client2.getAccounts().get(0)));
		assertFalse(client1.getAccounts().get(0).equals(client3.getAccounts().get(0)));

		// Checking accounts
		assertTrue(client1.getAccounts().get(1).equals(client2.getAccounts().get(1)));
		assertFalse(client1.getAccounts().get(1).equals(client3.getAccounts().get(1)));
	}
}
