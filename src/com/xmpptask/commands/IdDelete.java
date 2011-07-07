package com.xmpptask.commands;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.Id;

public class IdDelete extends Command {

	private List<Id> ids;
	
	public List<Id> getIds() {
		return ids;
	}
	public void setIds(List<Id> ids) {
		this.ids = ids;
	}
	public IdDelete(List<Id> ids){
		this.ids = ids;
	}
	@Override
	public CommandResult execute(PersistenceManager pm) {
		
		return new CommandResult();
	}

}
