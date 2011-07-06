package com.xmpptask.commands;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.PMF;
import com.xmpptask.models.Task;
import javax.jdo.Query;

import org.apache.commons.lang.StringUtils;

public class AddTask extends Command {

	/**
	 * task to persist
	 */
	private Task task;

	public AddTask(Task task){
		this.task = task;
	}
	
	public CommandResult execute(PersistenceManager pm){
				
		//TODO need to retrieve id and increment and assign
		
		Query q = pm.newQuery(Task.class);
		q.setOrdering("id desc");
		
		java.util.List<Task> results = (java.util.List<Task>)q.execute();
		if(results.isEmpty())
			this.task.setId("1");
		else{
			String[] ids = results.get(0).getId().split("\\.");
			ids[ids.length - 1] = String.valueOf((Integer.parseInt(ids[ids.length - 1]) + 1)); 
			task.setId(StringUtils.join(ids, '.'));
		}
		
		pm.makePersistent(this.task);
		this.user.getTasks().add(this.task);
		
		return new CommandResult("AddTask.success", this.task.getId());
	}
}
