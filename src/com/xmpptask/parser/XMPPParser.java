package com.xmpptask.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.xmpptask.commands.Command;
import com.xmpptask.commands.CommandBuilder;
import com.xmpptask.models.Id;
import com.xmpptask.models.Subject;
import com.xmpptask.models.Tag;
import com.xmpptask.models.Task;

public class XMPPParser {

	private String task;
	private int index;
	private String state;
	private CommandBuilder parsed;
	
	public static void main(String[] args){
		try{
			XMPPParser parse = new XMPPParser("list");
			Command cmd = parse.parse();
			
			XMPPParser parse1 = new XMPPParser("+ new task #tag #tag1");
			Command cmd1 = parse1.parse();
		}catch(ParseException e){
			System.out.println("Error:" + e.getMessage());
		}
	}
	
	public XMPPParser(String task){
		this.task = task;
		this.index = 0;
		this.state = "Init";
		this.parsed = new CommandBuilder();
	}
	
	public XMPPParser(){
		this.task = "";
		this.index = 0;
		this.parsed = new CommandBuilder();
	}
	
	public void setTask(String task){
		this.task = task;
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public Command parse() throws ParseException{
		
		if(task.isEmpty()){
			return parsed.list().build();
		}
		
		parseStepOne();
		
		return parsed.build();
	}

	
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
			parsed.add();
			consumeTask();
		//can be l/list
		}else if(chunk.equalsIgnoreCase("l") || chunk.equalsIgnoreCase("list")){
			//perform the list command
			parsed.list();
		//complete tasks
		}else if(chunk.equals("!")){
			parsed.complete()
				.subjects(consumeMultiple());
		//delete task
		}else if(chunk.equals("-") || chunk.equalsIgnoreCase("d") || chunk.equalsIgnoreCase("delete")){
			parsed.delete()
				.subjects(consumeMultiple());
		//increase priority
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
			parsed.subject(consumeId());
			parseTaskSubject();			
			
		//list all tasks in a particular tag
		}else if(look == '#'){
			parsed.subject(consumeTag()).list();
		}else{
			throw new ParseException("Unknown Command");
		}
		
		if(index != task.length()){
			throw new ParseException("Didn't consume all of the parse buffer");
		}
		
	}
	
	private int lengthRemaining(){
		return task.length() - index;
	}
	
	//parse an Id from the string
	private Id consumeId(){
		int spaceIndex = task.indexOf(' ');
		int origIndex = index;
		index = spaceIndex == -1 ? task.length() : spaceIndex + 1;
		return new Id(task.substring(origIndex, spaceIndex - 1));
	}
	
	private Tag consumeTag(){
		int spaceIndex = task.indexOf(' ');
		int origIndex = index;
		index = spaceIndex == -1 ? task.length() : spaceIndex + 1;
		return new Tag(task.substring(origIndex, spaceIndex - 1));
	}
	
	private java.util.List<Subject> consumeMultiple(){
		boolean hasNext = true;
		ArrayList<Subject> ret = new ArrayList();
		while(hasNext){
			int spaceIndex = task.indexOf(" ");
			
			if(task.charAt(index) == '@'){
				ret.add(new Id(task.substring(index + 1, spaceIndex)));
			}else{
				ret.add(new Tag(task.substring(index + 1, spaceIndex)));
			}
			
			index = spaceIndex + 1;
			hasNext = lengthRemaining() >= 1 && (task.charAt(index + 1) == '@' || task.charAt(index + 1) == '#'); 
			
		}
		return ret;
	}
	
	private void consumeTask(){
		String taskStr = task.substring(index);
		//add a task using the rest of the string as the task
		parsed.task(taskStr);

		Pattern tags = Pattern.compile("#([^ ]+)");
		Matcher match = tags.matcher(taskStr);
		while(match.find()){
			parsed.tag(match.group());
			match.region(match.end(), taskStr.length());
		}
		
		Pattern prereq = Pattern.compile("@([^ ]+)");
		match = prereq.matcher(taskStr);
		while(match.find()){
			parsed.prereq(match.group());
			match.region(match.end(), taskStr.length());
		}
		
		Pattern date = Pattern.compile("=([^ ]+)");
		match = prereq.matcher(taskStr);
		while(match.find()){
			parsed.date(match.group());
			match.region(match.end(), taskStr.length());
		}
		
		
		index = task.length();
	}
	
}
