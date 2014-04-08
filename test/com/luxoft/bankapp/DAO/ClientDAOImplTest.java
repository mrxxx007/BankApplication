package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Sergey Popov on 06.04.14.
 */
public class ClientDAOImplTest {
    Client client1 = new Client(100f);
    @Before
    public void setUp() throws Exception {
        client1.setName("Ivanov");
        client1.setGender(Gender.MALE);
        client1.setCity("Saint-Petersburg");
        client1.setPhone("+78121234567");
        client1.setEmail("ivanov@mail.com");
    }

    @Test
    public void testFindClientByName() throws Exception {
        /*ClientDAOImpl clientDAO = new ClientDAOImpl();
        Bank bank = new Bank("Test");
        clientDAO.save(client1);
        Client fromDB = clientDAO.findClientByName(bank, "Ivanov");
        assertNotNull(fromDB);
        assertEquals(client1.getName(), fromDB.getName());
        assertEquals(client1.getCity(), fromDB.getCity());*/
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
