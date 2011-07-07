package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import org.apache.commons.lang.NotImplementedException;

import com.xmpptask.models.Id;
import com.xmpptask.models.Tag;
import com.xmpptask.models.Task;

public class ListCommand extends Command {

	private Tag tag;
	private Id id;
	/**
	 * list all tasks for the appropriate tag
	 * @param tag
	 */
	public ListCommand(Tag tag){
		throw new NotImplementedException("");
	}
	
	/**
	 * list all sub-tasks for the id 
	 * @param id
	 */
	public ListCommand(Id id){
		throw new NotImplementedException("");
	}
	
	/**
	 * list all tasks
	 */
	public ListCommand(){
		
	}
	
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
			cr.append("ListTask.none");
			return cr;
		}else{
			return printList(tasks, cr);
		}
	}

	
	
}
