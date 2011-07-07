package com.xmpptask.commands;

import java.util.ResourceBundle;

public class CommandResult {

	private static final ResourceBundle resources = ResourceBundle.getBundle("com.xmpptask.resources");
	
	private StringBuilder plaintext;
	private StringBuilder html;
	
	private void initialize(String plaintext, String html){
		this.plaintext = new StringBuilder(plaintext);
		this.html = new StringBuilder(html);
	}
	
	public CommandResult(String resourceString, String id){
		initialize(String.format(resources.getString(resourceString), id), 
				String.format(resources.getString(resourceString + ".html"), id));
	}
	
	public CommandResult(){
		initialize("", "");
	}
	
	public void append(CommandResult cr){
		this.plaintext.append("\n" + cr.getPlainText());
		this.html.append("<br/>" + cr.getHTML());
	}
	
	public void append(String resourceId){
		this.append(resources.getString(resourceId), resources.getString(resourceId + ".html"));
	}
	
	public void append(String plaintext, String html){
		this.plaintext.append(plaintext);
		this.html.append(html);
	}
	
	public String getPlainText(){
		return this.plaintext.toString();
	}
	
	public String getHTML(){
		return this.html.toString();
	}
	
}
