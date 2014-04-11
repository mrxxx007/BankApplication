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
    Client client1 = new Client(100f);
	Bank bank;

    @Before
    public void setUp() throws Exception {
		bank = ServiceFactory.getBankDAO().getBankByName("My Bank");

		client1.setName("IvanovTestName");
        client1.setGender(Gender.MALE);
        client1.setCity("Saint-Petersburg");
        client1.setPhone("+78121234567");
        client1.setEmail("ivanov@mail.com");
		client1.setBankId(bank.getId());
    }

    @Test (expected = ClientNotFoundException.class)
    public void testFindClientByName() throws Exception {
        ClientDAO clientDAO = ServiceFactory.getClientDAO();
        Bank bank = ServiceFactory.getBankDAO().getBankByName("My Bank");
		assertNotNull(bank);

        clientDAO.save(client1);
        Client fromDB = clientDAO.findClientByName(bank, "IvanovTestName");
        assertNotNull(fromDB);
        assertEquals(client1.getCity(), fromDB.getCity());

		clientDAO.remove(client1);
		// should be exception
		fromDB = clientDAO.findClientByName(bank, "IvanovTestName");
		assertNull(fromDB);


    }

    @Test
    public void testFindClientById() throws Exception {

    }

    @Test
    public void testGetAllClients() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
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
		assertTrue(TestService.isEquals(client1, client2));

		clientDAO.remove(client1);
	}

    @Test
    public void testRemove() throws Exception {

    }
}
