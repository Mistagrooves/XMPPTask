package com.xmpptask.commands;

import com.xmpptask.models.Id;
import com.xmpptask.models.IdsTags;
import com.xmpptask.models.Tag;
import com.xmpptask.models.Task;
import com.xmpptask.models.User;

import net.sf.jsr107cache.*;

/**
 * Task to build a command out of discrete steps. Meant to be almost a DSL.
 * May be re-factored at a later date, the builder pattern didn't work as well as I thought it would
 * @author Eric
 *
 */
public class CommandBuilder {
	
	private CompositeCommand commands;
	
	private Task parent;
	
	
	
	/** 
	 * constructs a blank command builder
	 */
	public CommandBuilder(){
		commands = new CompositeCommand();
	}
	
	/**
	 * give the command builder the previous command
	 * when something is a state based command or undoing the previous command
	 *
	 * @param session
	 */

	public CommandBuilder(CommandBuilder cb){
		
	}
	
	/**
	 * executes the currently built up command
	 * 
	 * @return
	 */
	public Command build(){
		
		return commands;
	}
	
	/**
	 * Adds the list command to the builder
	 * @return
	 */
	public CommandBuilder list(){
		
		commands.add(new ListCommand());
		return this;
	}
	
	public CommandBuilder list(Tag tag){
		commands.add(new ListCommand(tag));
		return this;
	}
	
	/**
	 * Adds the help command to the builder
	 * 
	 * @return
	 */
	public CommandBuilder help(){
		commands.add(new HelpCommand());
		return this;
	}
	
	
	/**
	 * add a task to the global scope
	 * 
	 * @return
	 */
	public CommandBuilder add(Task taskInfo){
		commands.add(new AddTask(taskInfo));
		return this;
	}
	
	/**
	 * add a task to already existing task
	 * @param taskinfo - the task info to save
	 * @param id - the id of the parent
	 * @return
	 */
	public CommandBuilder add(Task taskinfo, Id id){
		commands.add(new AddChildTask(taskinfo, id));
		return this;
	}
	
	/**
	 * marks the task as complete
	 * currently just deletes the tasks
	 * TODO: implement proper completion
	 * @param subjects - the ids/tags to operate on
	 * @return
	 */
	public CommandBuilder complete(IdsTags subjects){
		commands.add(new IdDelete(subjects.getIds()));
		commands.add(new TagDelete(subjects.getTags()));
		return this;
	}
	
	/**
	 * Deletes the task, can be a sub-task
	 * @param subjects - ids/tags to operate on
	 * @return
	 */
	public CommandBuilder delete(IdsTags subjects){
		commands.add(new IdDelete(subjects.getIds()));
		commands.add(new TagDelete(subjects.getTags()));
		return this;
	}

}
