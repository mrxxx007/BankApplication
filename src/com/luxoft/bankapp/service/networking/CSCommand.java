package com.luxoft.bankapp.service.networking;

/**
 * Created by Sergey Popov on 01.04.2014.
 */
public class CSCommand {
	String name;
	int accessLevel;

	public CSCommand(String cmdName, int accessLevel) {
		name = cmdName;
		this.accessLevel = accessLevel;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public String getName() {
		return name;
	}

                                                        
}
