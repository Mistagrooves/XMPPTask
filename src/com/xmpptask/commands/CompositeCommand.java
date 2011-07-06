package com.xmpptask.commands;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.User;

/**
 * 
 * Command that executes multiple sub commands
 * 
 * @author Eric
 *
 */
public class CompositeCommand extends Command {

	java.util.List<Command> commands;
	
	public CompositeCommand(){
		commands = new ArrayList<Command>();
	}
	
	@Override
	public CommandResult execute(PersistenceManager pm) {
		
		CommandResult cr = new CommandResult(); 
		//aggregate all the results
		for(Command c : commands){
			cr.append(c.execute(pm));
		}
		
		return cr;
	}
	
	public CompositeCommand(java.util.List<Command> commands){
		
	}
	
	public int getCount(){
		return commands.size();
	}

	public void add(Command cmd){
		commands.add(cmd);
	}
	
	public java.util.List<Command> getCommands(){
		return commands;
	}
	
	@Override
	public void withUser(User u){
		for(Command c: commands){
			c.withUser(u);
		}
	}

}
