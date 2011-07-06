package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.Task;

public class ListCommand extends Command {

	public CommandResult printList(Iterable<Task> tasks, CommandResult cr){
		for(Task t : tasks){
			String taskStr = String.format("%s %s\n", t.getId(), t.getTaskText());
			cr.append(taskStr, taskStr);
			//iterate over the children
			printList(t.getChildren(), cr);
		}
		
		return cr;
	}
	
	@Override
	public CommandResult execute(PersistenceManager pm) {
		CommandResult cr = new CommandResult(); 
		java.util.List<Task> tasks = this.user.getTasks();
		if(tasks.isEmpty()){
			cr.append("No tasks! Hurra", "No tasks! Hurra");
			return cr;
		}else{
			return printList(tasks, cr);
		}
	}

	
	
}
