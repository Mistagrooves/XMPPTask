package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.User;

public abstract class Command {

	protected User user;
	public abstract CommandResult execute(PersistenceManager pm);
	public void withUser(User user){
		this.user = user;
	}
}
