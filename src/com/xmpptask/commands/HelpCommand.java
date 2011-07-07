package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

public class HelpCommand extends Command {

	@Override
	public CommandResult execute(PersistenceManager pm) {
		return new CommandResult("HELP!", "HELP!");
	}

}
