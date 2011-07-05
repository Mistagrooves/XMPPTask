package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.Task;

public class List extends Command {

	public CommandResult printList(Iterable<Task> tasks, CommandResult cr, String prepend){
		int count = 1;
		for(Task t : tasks){
			String taskStr = String.format("%s%d. %s\n", prepend, count, t.getTaskText());
			cr.append(taskStr, taskStr);
			//iterate over the children
			printList(t.getChildren(), cr, String.format("%s%d.", prepend, count));
			
			count++;
		}
		
		return cr;
	}
	
	@Override
	public CommandResult execute(PersistenceManager pm) {
		CommandResult cr = new CommandResult("", ""); 
		java.util.List<Task> tasks = this.user.getTasks();
		if(tasks.isEmpty()){
			cr.append("No tasks! Hurra", "No tasks! Hurra");
			return cr;
		}else{
			return printList(tasks, cr, "");
		}
	}

	
	
}
