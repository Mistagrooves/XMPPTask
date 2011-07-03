package com.xmpptask.commands;

import java.util.ArrayList;

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
	public CommandResult execute() {
		
		CommandResult cr = new CommandResult("", ""); 
		//aggregate all the results
		for(Command c : commands){
			cr.append(c.execute());
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

}
