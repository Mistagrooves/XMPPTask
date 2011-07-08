package com.xmpptask.commands;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.lang.NotImplementedException;

import com.google.appengine.api.datastore.Key;
import com.xmpptask.models.Id;
import com.xmpptask.models.Tag;
import com.xmpptask.models.Task;
import com.xmpptask.models.User;

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
	
	private Query getChildrenQuery(PersistenceManager pm, Key parent){
		Query q = pm.newQuery(Task.class);
		q.setFilter("user == userParam && parentKey = parentParam");
		q.declareParameters(User.class.getName() + " userParam, " + Key.class.getName() + " parentParam");
		q.setOrdering("id asc");
		return q;
	}
	
	public CommandResult printList(Iterable<Task> tasks, CommandResult cr){
		for(Task t : tasks){
			//String taskStr = String.format("%s %s\n", t.getId(), t.getTaskText());
			cr.append("ListTask.standard", t.getId().toString(), t.getTaskText());
			//iterate over the children
			printList(t.getChildren(), cr);
		}
		
		return cr;
	}
	
	@Override
	public CommandResult execute(PersistenceManager pm) {
		CommandResult cr = new CommandResult();
		
		List<Task> tasks = this.user.getTasks();
		if(tasks.isEmpty()){
			cr.append("ListTask.none");
			return cr;
		}else{
			return printList(tasks, cr);
		}
	}

	
	
}
