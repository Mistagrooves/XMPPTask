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
	
	public CommandResult(String resourceString){
		initialize(resources.getString(resourceString), 
				resources.getString(resourceString + ".html"));
	}
	
	public CommandResult(){
		initialize("", "");
	}
	
	public void append(CommandResult cr){
		if(cr == null) return;
		this.plaintext.append("\n" + cr.getPlainText());
		this.html.append("<br/>" + cr.getHTML());
	}
	
	/*public void append(String resourceId){
		this.append(resources.getString(resourceId), resources.getString(resourceId + ".html"));
	}*/
	
	public void append(String resourceString, String... id){
		
		String newlinept = this.plaintext.length() == 0 ? "" : "\n";
		String newlinehtml = this.plaintext.length() == 0 ? "" : "<br/>";
		
		this.plaintext.append(newlinept + String.format(resources.getString(resourceString), id)); 
		this.html.append(newlinehtml + String.format(resources.getString(resourceString + ".html"), id));
	}
	
	public String getPlainText(){
		return this.plaintext.toString();
	}
	
	public String getHTML(){
		return this.html.toString();
	}


}
