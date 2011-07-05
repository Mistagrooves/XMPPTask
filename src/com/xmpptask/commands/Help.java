package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

public class Help extends Command {

	@Override
	public CommandResult execute(PersistenceManager pm) {
		return new CommandResult("HELP!", "HELP!");
	}

}
