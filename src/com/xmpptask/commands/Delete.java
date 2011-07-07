package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.Id;
import com.xmpptask.models.Tag;

public class Delete extends Command {

	private Id id;
	private Tag tag;
	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Delete(Id id){
		
	}
	
	public Delete(Tag tag){
		
	}
	
	public CommandResult execute(PersistenceManager pm){
		return new CommandResult();
	}
}
