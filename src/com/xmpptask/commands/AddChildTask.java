package com.xmpptask.commands;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.lang.StringUtils;

import com.xmpptask.models.Id;
import com.xmpptask.models.Task;

public class AddChildTask extends Command {

	/**
	 * task to persist
	 */
	private Task task;
	private Id id;

	public AddChildTask(Task task, Id id){
		this.task = task;
		this.id = id;
	}
	
	public CommandResult execute(PersistenceManager pm){
				
		Query q = pm.newQuery(Task.class);
		q.setFilter("id == idparam");
		q.declareParameters("String idparam");
				
		java.util.List<Task> result = (java.util.List<Task>)q.execute(this.id.toString()); 
		if(result.isEmpty()){
			
		}else{
			Task parent = result.get(0);
			parent.getChildren().add(this.task);
			Query q2 = pm.newQuery(Task.class);
			q2.setFilter("parentKey == parentKeyParam");
			q2.setOrdering("id desc");
			q2.declareParameters("com.google.appengine.api.datastore.Key parentKeyParam");
			java.util.List<Task> childrenResult = (java.util.List<Task>)q2.execute(parent.getKey());
			
			if(childrenResult.isEmpty()){
				task.setId(parent.getId() + ".1");
			}else{
				String[] ids = childrenResult.get(0).getId().split("\\.");
				ids[ids.length - 1] = String.valueOf((Integer.parseInt(ids[ids.length - 1]) + 1)); 
				task.setId(StringUtils.join(ids, '.'));
				task.setParentKey(parent.getKey());
			}
		}
		
		//TODO need to retrieve id and increment and assign
		pm.makePersistent(this.task);
		this.user.getTasks().add(this.task);
		
		return new CommandResult(String.format("Success: Added new task"), String.format("<span style='color:green'>Success</span>: Added new task as @%s", task.getId()));
	}
}