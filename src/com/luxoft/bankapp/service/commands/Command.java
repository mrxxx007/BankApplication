package com.luxoft.bankapp.service.commands;

import java.io.IOException;

/**
 * Created by Sergey Popov on 27.03.2014.
 */
public interface Command {
	void execute() throws IOException;
	void printCommandInfo();
}
