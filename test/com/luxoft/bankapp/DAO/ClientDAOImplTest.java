package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.ServiceFactory;
import com.luxoft.bankapp.service.TestService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class ClientDAOImplTest {
    //Client client1 = new Client(100f);
	Bank bank;

    @Before
    public void setUp() throws Exception {
		bank = ServiceFactory.getBankDAO().getBankByName("My Bank");
    }

    @Test
    public void testFindClientByName() throws Exception {
        ClientDAO clientDAO = ServiceFactory.getClientDAO();
        Bank bank = ServiceFactory.getBankDAO().getBankByName("My Bank");
		assertNotNull(bank);

		Client saveClient = new Client("IvanovTestFind", 0f);
		saveClient.setGender(Gender.MALE);
		saveClient.setCity("Saint-Petersburg");
		saveClient.setPhone("+78121234567");
		saveClient.setEmail("ivanov@mail.com");
		saveClient.setBankId(bank.getId());

        clientDAO.save(saveClient);
        Client fromDB = clientDAO.findClientByName(bank, "IvanovTestFind");
        assertNotNull(fromDB);
        assertEquals(saveClient, fromDB);

		clientDAO.remove(saveClient);
    }

	@Test (expected = ClientNotFoundException.class)
	public void testFindClientByName_clientNotExistsInDB() throws Exception {
		ServiceFactory.getClientDAO().findClientByName(bank, "IvanovNotExists");
	}


    @Test
    public void testSave() throws Exception {
		Client client1 = new Client("IvanovTestSave", 0f);
		client1.setGender(Gender.MALE);
		client1.setCity("Saint-Petersburg");
		client1.setPhone("+78121234567");
		client1.setEmail("ivanov@mail.com");
		client1.setBankId(bank.getId());
		// Testing insert
		ClientDAO clientDAO = ServiceFactory.getClientDAO();
		clientDAO.save(client1);
		assertTrue(client1.getId() != -1);

		Client client2 = clientDAO.findClientById(client1.getId());
		assertTrue(TestService.isEquals(client1, client2));

		// Testing update
		int clientId = client2.getId();
		client1.setCity("Moscow");
		client1.setPhone("+74951234567");
		clientDAO.save(client1);

		client2 = clientDAO.findClientById(clientId);
		assertEquals(client1, client2);

		clientDAO.remove(client1);
	}

    @Test
    public void testRemove() throws Exception {
		Client removeClient = new Client("TestRemoveClient", 0f);

		removeClient.setBankId(bank.getId());
		ServiceFactory.getClientDAO().save(removeClient);

		ServiceFactory.getClientDAO().remove(removeClient);
    }
}
