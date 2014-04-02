package com.luxoft.bankapp.service.networking;

import java.util.*;

/**
 * Created by Sergey Popov on 4/1/2014.
 */
public class ClientServerCommands {
	public static List<CSCommand> commands = new ArrayList<>(4);

	static {
		commands.add(new CSCommand("authorize", 0));
		commands.add(new CSCommand("withdraw", 0));
		commands.add(new CSCommand("getBalance", 0));
		commands.add(new CSCommand("addClient", 1));
		commands.add(new CSCommand("deleteClient", 1));
		commands.add(new CSCommand("getClientInfo", 1));
		commands.add(new CSCommand("exit", 0));
	}

	public static List<String> getBankClientCommands() {
		return getCommandsByAccessLevel(0);
	}

	public static List<String> getBankRemoteOfficeCommands() {
		return getCommandsByAccessLevel(1);
	}

	private static List<String> getCommandsByAccessLevel(int accessLevel) {
		List<String> values = new ArrayList<>();
		for (CSCommand cmd : commands) {
			if (cmd.accessLevel <= accessLevel) {
				values.add(cmd.name);
			}
		}
		return values;
	}
}