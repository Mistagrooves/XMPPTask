package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.Id;
import com.xmpptask.models.Tag;

public class Delete extends Command {

	public Delete(Id id){
		
	}
	
	public Delete(Tag tag){
		
	}
	
	public CommandResult execute(PersistenceManager pm){
		return new CommandResult("", "");
	}
}
