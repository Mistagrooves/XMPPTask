package com.xmpptask.commands;

public class CommandResult {

	private StringBuilder plaintext;
	private StringBuilder html;
	
	public CommandResult(String plaintext, String html){
		this.plaintext = new StringBuilder(plaintext);
		this.html = new StringBuilder(html);
	}
	
	public void append(CommandResult cr){
		this.plaintext.append("\n" + cr.getPlainText());
		this.html.append("<br/>" + cr.getHTML());
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
