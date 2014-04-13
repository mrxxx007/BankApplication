package com.luxoft.bankapp.model;

import com.luxoft.bankapp.annotations.NoDB;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankReport;
import com.luxoft.bankapp.service.ClientRegistrationListener;

import java.util.*;

/**
 * Created by user on 3/25/2014.
 */
public class Bank implements Report {
    @NoDB private int id = -1;
    private String name;
	private List<Client> clientsList = new ArrayList<Client>();
	@NoDB private Map<String, Client> clients = new HashMap<String, Client>();
	@NoDB List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();

    public Bank(String name) {
        this.name = name;

		//registerListener(new PrintClientListener());
		registerListener(new ClientRegistrationListener() {
			@Override
			public void onClientAdded(Client c) {
				clients.put(c.getName(), c);
			}
		});

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
        for (Client c : clientsList)
            c.printReport();
		System.out.println("---------------------");

		BankReport.getNumberOfClients(this);
		BankReport.getAccountsNumber(this);
		BankReport.getBankCreditSum(this);
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Client> getClientsList() {
        return Collections.unmodifiableList(clientsList);

    }

	public void addClient(Client client) throws ClientExistsException {
		if (clientsList.contains(client))
			throw new ClientExistsException();
		clientsList.add(client);

		for (ClientRegistrationListener listener : listeners)
            listener.onClientAdded(client);
		//clients.put(client.getName(), client);
	}

	public void removeClient(Client client) {
		clientsList.remove(client);
		clients.remove(client.getName());
	}

	/**
	 *
	 * @param name - Client name you want to find
	 * @return Client or null if not found
	 */
	public Client findClientByName(String name) {
		/*for (Client c : clientsList)
			if (c.getName().equals(name)) {
				return  c;
			}
		return null;*/

		return clients.get(name);
	}

    public void registerListener(ClientRegistrationListener listener) {
        listeners.add(listener);
    }

    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

	public void parseFeed(Map<String,String> feed) throws ClientExistsException {
		String name = feed.get("name"); // client name

		Client client = clients.get(name);
		// if no client then create it
		if (client == null) {
			client = new Client(name, 0f);
			addClient(client);
		}



		client.parseFeed(feed);
	}
}

