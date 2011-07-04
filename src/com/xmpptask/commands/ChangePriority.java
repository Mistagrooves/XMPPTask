package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

public class ChangePriority extends Command {

	public CommandResult execute(PersistenceManager pm){
		return new CommandResult("", "");
	}
}
