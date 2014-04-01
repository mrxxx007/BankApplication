package com.luxoft.bankapp.service.networking;

import java.util.*;

/**
 * Created by Sergey Popov on 4/1/2014.
 */
public class ClientServerCommands {

	public static List<CSCommand> commands = new ArrayList<>(4);
	//private static Map<String, String> commands = new HashMap<>();
	private static int cmdCounter;

	static {
		/*commands.put(Integer.toString(cmdCounter++), "authorize");
		commands.put(Integer.toString(cmdCounter++), "withdraw");
		commands.put(Integer.toString(cmdCounter++), "getBalance");
		commands.put(Integer.toString(cmdCounter++), "exit");*/

		commands.add(new CSCommand("authorize", 0));
		commands.add(new CSCommand("withdraw", 0));
		commands.add(new CSCommand("getBalance", 0));
		commands.add(new CSCommand("addClient", 1));
		commands.add(new CSCommand("deleteClient", 1));
		commands.add(new CSCommand("getClientInfo", 1));
		commands.add(new CSCommand("exit", 0));
	}

	public void addCommand(String cmdName) {
		//commands.put(Integer.toString(cmdCounter++), cmdName);
	}

	/*public static String getCmdName(String cmdKey) {
		//return commands.get(cmdKey);
		return null;
	}*/

	public static List<String> getBankClientCommands() {
		return getCommandByAccessLevel(0);
	}

	public static List<String> getBankRemoteOfficeCommands() {
		return getCommandByAccessLevel(1);
	}

	private static List<String> getCommandByAccessLevel(int accessLevel) {
		List<String> values = new ArrayList<>();
		for (CSCommand cmd : commands) {
			if (cmd.accessLevel <= accessLevel) {
				values.add(cmd.name);
			}
		}
		return values;
	}

	/*public static Map<String, String> getAllCommands() {
		return Collections.unmodifiableMap(commands);
	}*/

	/*public static List<CSCommand> getAllCommands() {
		return Collections.unmodifiableList(commands);
	}*/
}