package com.xmpptask.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


import com.xmpptask.commands.Command;
import com.xmpptask.commands.CommandBuilder;

import com.xmpptask.models.Id;
import com.xmpptask.models.IdsTags;
import com.xmpptask.models.Tag;
import com.xmpptask.models.Task;

/**
 * Classes parses a particular formatted string into commands to perform on the task store
 *  
 * @author Eric
 *
 */
public class XMPPParser {

	private String task;
	private int index;
	private CommandBuilder parsed;
	
	private void init(String task){
		this.task = task;
		this.index = 0;
		this.parsed = new CommandBuilder();
	}
	
	public XMPPParser(String task){
		init(task); 
	}
	
	public XMPPParser(){
		init(null);
	}
	
	/**
	 * sets the task string to parse
	 * @param task
	 */
	public void setParseString(String task){
		init(task);
	}
	
	/**
	 * performing the parsing
	 * 
	 * @param task
	 * @return
	 */
	public Command parse() throws ParseException{
		
		//if the string is empty return the list of tasks
		if(StringUtils.isBlank(this.task)){
			return parsed.list().build();
		}
		//proceed to the first parsing step
		parseStepOne();
		//return the command built from all the steps
		return parsed.build();
	}

	/**
	 * parse out the subject of an @ command
	 * @throws ParseException
	 */
	private void parseTaskSubject() throws ParseException{
		char look = Character.toLowerCase(task.charAt(index));

		//add tag to task
		if(look == '#'){
			throw new ParseException("Not implemented yet");
		//add date to task
		}else if(look == '='){
			throw new ParseException("Not Implemented yet");
		//process the rest as adding a sub task
		}else{
			consumeTask();
		}
	}
	
	/*
	 * Gets the next parsing token
	 * Increments the internal pointer to the character after the space
	 * 
	 * @return the text between the current pointer and the next space character
	 * 
	 */
	private String getToken(){
		int spaceIndex = task.indexOf(' ');

		if(spaceIndex == -1){
			//if we can't find a space then use the entire string
			index = task.length();
			return task;
		}else{
			//grab the first chunk, move the index forward.
			String chunk = task.substring(index, spaceIndex);
			index = spaceIndex + 1;
			return chunk;
		}
	}
	
	private void parseStepOne() throws ParseException{
		
		
		String chunk = getToken();
		char look = Character.toLowerCase(chunk.charAt(0));
		//can be a/add/+
		if(chunk.equalsIgnoreCase("a") || chunk.equals("+") || chunk.equalsIgnoreCase("add")){
			//add a task to the global parent
			parsed.add(consumeTask());

		//can be l/list
		}else if(chunk.equalsIgnoreCase("l") || chunk.equalsIgnoreCase("list")){
			//perform the list command
			parsed.list();
		//complete tasks
		}else if(chunk.equals("!")){
			parsed.complete(consumeMultiple());
		//delete task
		}else if(chunk.equals("-") || chunk.equalsIgnoreCase("d") || chunk.equalsIgnoreCase("delete")){
			parsed.delete(consumeMultiple());
		//increase priority
		}else if (chunk.equalsIgnoreCase("help") || chunk.equalsIgnoreCase("h") || chunk.equals("?")){
			parsed.help();
		}else if(look == '>' || look == 'i'){
			throw new ParseException("Not implemented yet");
		//reduce priority
		}else if(look == '<' || look == 'r' ){
			throw new ParseException("Not implemented yet");
		//date filtering
		}else if (look == '>' || look == '<' || look == '='){
			throw new ParseException("Not implemented yet");
		//if it starts with a list of ids or tags
		}else if(look == '@'){
			Id id = new Id(chunk.substring(1));
			parsed.add(consumeTask(), id);
			
		//list all tasks in a particular tag
		}else if(look == '#'){
			Tag tag = new Tag(chunk.substring(1));
			parsed.list(tag);
		}else{
			throw new ParseException("Unknown Command");
		}
		
		if(index != task.length()){
			throw new ParseException("Didn't consume all of the parse buffer");
		}
		
	}
	
	/**
	 * gets the length remaining in the parsed string
	 * @return
	 */
	private int lengthRemaining(){
		return task.length() - index;
	}
	
	//parse an Id from the string
	private Id consumeId(){
		int spaceIndex = task.indexOf(' ', index);
		int origIndex = index;
		index = spaceIndex == -1 ? task.length() : spaceIndex + 1;
		return new Id(task.substring(origIndex, spaceIndex - 1));
	}
	
	private Tag consumeTag(){
		int spaceIndex = task.indexOf(' ', index);
		int origIndex = index;
		index = spaceIndex == -1 ? task.length() : spaceIndex + 1;
		return new Tag(task.substring(origIndex, spaceIndex - 1));
	}
	
	private IdsTags consumeMultiple() throws ParseException{
		boolean hasNext = true;
		IdsTags ret = new IdsTags();
		while(hasNext){
			int spaceIndex = task.indexOf(" ", index);
			int endindex = spaceIndex == -1 ? task.length() : spaceIndex;
			
			if(task.charAt(index) == '@'){
				ret.addId(new Id(task.substring(index + 1, endindex)));
			}else if(task.charAt(index) == '#'){
				ret.addTag(new Tag(task.substring(index + 1, endindex)));
			}else{
				throw new ParseException("Couldn't consume ids or tags");
			}
			
			index = spaceIndex == -1 ? task.length() : spaceIndex + 1;
			hasNext = lengthRemaining() >= 1 && (task.charAt(index) == '@' || task.charAt(index) == '#'); 
			
		}
		return ret;
	}
	
	private Task consumeTask(){
		Task ti = new Task();
		String taskStr = task.substring(index);
		//add a task using the rest of the string as the task
		ti.setTaskText(taskStr);

		Pattern tags = Pattern.compile("#([^ ]+)");
		Matcher match = tags.matcher(taskStr);
		while(match.find()){
			ti.addTag(match.group(1));
			match.region(match.end(), taskStr.length());
		}
		
		Pattern prereq = Pattern.compile("@([^ ]+)");
		match = prereq.matcher(taskStr);
		while(match.find()){
			ti.addPrereq(match.group(1));
			match.region(match.end(), taskStr.length());
		}
		
		Pattern date = Pattern.compile("=([^ ]+)");
		match = prereq.matcher(taskStr);
		while(match.find()){
			ti.setDueOn(new Date()/*match.group()*/);
			match.region(match.end(), taskStr.length());
		}
		
		
		index = task.length();
		return ti;
	}
	
}
