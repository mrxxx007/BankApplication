package com.luxoft.bankapp.service.networking;

import java.util.*;

/**
 * Created by Sergey Popov on 4/1/2014.
 */
public class ClientServerCommands {
	private static Map<String, String> commands = new HashMap<>();
	private static int cmdCounter;

	static {
		commands.put(Integer.toString(cmdCounter++), "authorize");
		commands.put(Integer.toString(cmdCounter++), "withdraw");
		commands.put(Integer.toString(cmdCounter++), "getBalance");
		commands.put(Integer.toString(cmdCounter++), "exit");
	}

	public void addCommand(String cmdName) {
		commands.put(Integer.toString(cmdCounter++), cmdName);
	}

	public static String getCmdName(String cmdKey) {
		return commands.get(cmdKey);
	}

	public static Map<String, String> getAllCommands() {
		return Collections.unmodifiableMap(commands);
	}
}
