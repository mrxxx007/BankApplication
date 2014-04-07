package com.luxoft.bankapp.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Sergey Popov on 4/7/2014.
 */
public class BankInfo {
	/**
	 * Total number of clients of the bank
	 */
	int numberOfClients;
	/**
	 * The sum of all accounts of all clients
	 */
	double totalAccountSum;
	/**
	 * List of clients by the city
	 */
	Map<String, List<Client>> clientsByCity;


	public int getNumberOfClients() {
		return numberOfClients;
	}

	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}

	public Map<String, List<Client>> getClientsByCity() {
		return clientsByCity;
	}

	public void setClientsByCity(Map<String, List<Client>> clientsByCity) {
		this.clientsByCity = clientsByCity;
	}

	public double getTotalAccountSum() {
		return totalAccountSum;
	}

	public void setTotalAccountSum(double totalAccountSum) {
		this.totalAccountSum = totalAccountSum;
	}
}
