package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
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
		bank = new BankDAOImpl().getBankByName("My Bank");

		client1.setName("IvanovTestName");
        client1.setGender(Gender.MALE);
        client1.setCity("Saint-Petersburg");
        client1.setPhone("+78121234567");
        client1.setEmail("ivanov@mail.com");
		client1.setBankId(bank.getId());
    }

    @Test (expected = ClientNotFoundException.class)
    public void testFindClientByName() throws Exception {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        Bank bank = new BankDAOImpl().getBankByName("My Bank");
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

    }

    @Test
    public void testRemove() throws Exception {

    }
}
