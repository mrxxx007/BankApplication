package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotFoundException;
import com.luxoft.bankapp.service.BankReport;
import com.luxoft.bankapp.service.ClientRegistrationListener;
import com.luxoft.bankapp.service.commands.BankCommander;

import java.util.*;

/**
 * Created by user on 3/25/2014.
 */
public class Bank implements Report {
    private String name;
    private List<Client> clients = new ArrayList<Client>();
	private Map<String, Client> mapClients = new HashMap<String, Client>();

    List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();

    public Bank(String name) {
        this.name = name;

		registerListener(new ClientRegistrationListener() {
			@Override
			public void onClientAdded(Client c) {
				mapClients.put(c.getName(), c);
			}
		});
        //registerListener(new PrintClientListener());
        //registerListener(new EmailNotificationListener());
    }

    public Bank(String name, ClientRegistrationListener[] listenerArray) {
        this(name);
        for (ClientRegistrationListener regListener : listenerArray)
            registerListener(regListener);
    }

    private class PrintClientListener implements ClientRegistrationListener{

        @Override
        public void onClientAdded(Client c) {
            System.out.println("---- Print listener ----");
            c.printReport();
        }
    }
    private class EmailNotificationListener implements ClientRegistrationListener{

        @Override
        public void onClientAdded(Client c) {
            System.out.println("---- Email listener ----");
            System.out.println("\n"+"Notification email for client " + c.getName() + " to be sent"+"\n");
        }
    }

    @Override
    public void printReport() {
        System.out.print("Report for ");
        System.out.println(name);
        for (Client c : clients)
            c.printReport();
		System.out.println("---------------------");

		BankReport.getNumberOfClients(this);
		BankReport.getAccountsNumber(this);
		BankReport.getBankCreditSum(this);
	}

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

	public void addClient(Client client) throws ClientExistsException {
		if (clients.contains(client))
			throw new ClientExistsException();
		clients.add(client);

		for (ClientRegistrationListener listener : listeners)
            listener.onClientAdded(client);
	}

	/**
	 *
	 * @param name - Client name you want to find
	 * @return Client or null if not found
	 */
	public Client findClientByName(String name) {
		/*for (Client c : clients)
			if (c.getName().equals(name)) {
				return  c;
			}
		return null;*/

		return mapClients.get(name);
	}

    public void registerListener(ClientRegistrationListener listener) {
        listeners.add(listener);
    }
    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }
}

