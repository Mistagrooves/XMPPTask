package com.xmpptask.commands;

import com.xmpptask.models.Id;
import com.xmpptask.models.IdsTags;
import com.xmpptask.models.Subject;
import com.xmpptask.models.Tag;
import com.xmpptask.models.Task;
import com.xmpptask.models.User;

import net.sf.jsr107cache.*;

/**
 * Task to build a command out of discrete steps. Meant to be almost a DSL.
 * 
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
	
	public CommandBuilder list(){
		
		commands.add(new ListCommand());
		return this;
	}
	
	public CommandBuilder subject(Id task){
		return this;
	}
	
	public CommandBuilder subject(Tag tag){
		return this;
	}
	
	public CommandBuilder subjects(Id[] ids){
		return this;
	}
	
	public CommandBuilder subjects(Tag[] tags){
		return this;
	}
	
	public CommandBuilder subjects(java.util.List<Subject> subjects){
		return this;
	}
	
	public CommandBuilder withUser(User u){
		for(Command c : this.commands.getCommands()){
			c.withUser(u);
		}
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
	
	public CommandBuilder add(Task taskinfo, Id id){
		commands.add(new AddChildTask(taskinfo, id));
		return this;
	}
	
	/**
	 * adds the current task to a parent based on id
	 * 
	 * @param id
	 * @return
	 */
	public CommandBuilder addTo(Id id){
		return this;
	}
	
	/**
	 * finds a task via Id and is the subject of this command
	 * 
	 * @param id
	 * @return
	 */
	public CommandBuilder task(Id id){
		return this;
	}
	
	//just delete the commands for now
	public CommandBuilder complete(IdsTags subjects){
		commands.add(new IdDelete(subjects.getIds()));
		commands.add(new TagDelete(subjects.getTags()));
		return this;
	}
	
	public CommandBuilder tag(String tag){
		return this;
	}
	
	public CommandBuilder prereq(String prereq){
		return this;
	}
	
	public CommandBuilder date(String date){
		return this;
	}
	/**
	 * constructs a task via the task string
	 * 
	 * @param task
	 * @return
	 */
	public CommandBuilder task(String task){
		return this;
	}
	
	/**
	*the subject of this command is now all of these tasks
	*/
	public CommandBuilder tasks(java.util.List<Id> ids){
		return this;
	}
	
	//
	public CommandBuilder delete(IdsTags subjects){
		commands.add(new IdDelete(subjects.getIds()));
		commands.add(new TagDelete(subjects.getTags()));
		return this;
	}

}
