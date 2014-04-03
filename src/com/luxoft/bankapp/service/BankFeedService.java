package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Admin on 31.03.2014.
 */
public class BankFeedService {
	public static Map<String, String> loadFeed(Bank bank, String folder) {
		String[] parsed = new String[2];
		String str;
		Map<String, String> values = new TreeMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(folder));
			while ((str = reader.readLine()) != null) {
				values.clear();
				for (String splitted : str.split(";")) {
					parsed = splitted.split("=");
					values.put(parsed[0], parsed[1]);
					//System.out.printf("%s = %s%n", parsed[0], parsed[1]);
				}

				try {
					bank.parseFeed(values);
				} catch (ClientExistsException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}
}
