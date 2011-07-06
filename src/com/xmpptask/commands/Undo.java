package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

public class Undo extends Command {

	public CommandResult execute(PersistenceManager pm){
		return new CommandResult();
	}
}
