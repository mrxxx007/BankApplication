import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;
import org.junit.*;
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
		} catch (ClientExistsException ex) {
			System.out.println(ex.getMessage());
		}

		bankService.addAccount(client1, new CheckingAccount(200));
		bankService.addAccount(client1, new SavingAccount(700));
		bankService.setActiveAccount(client1, client1.getAccounts().get(0));
		bankService.saveClient(client1);
		Client readedClient = bankService.loadClient();

		assertNotNull(readedClient);
		assertEquals("Ivanov I.P.", readedClient.getName());
		assertEquals(Gender.MALE.getSalut(), readedClient.getClientSalutation());
		assertEquals(2, readedClient.getAccounts().size());
		assertEquals(client1.getAccounts().get(0).getBalance(),
				readedClient.getAccounts().get(0).getBalance(), 0f);
	}
}
