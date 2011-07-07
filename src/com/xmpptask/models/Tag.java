package com.xmpptask.models;

/**
 * Represents a tag string that tasks can be tagged with to categorize
 * Takes the form: #<string>
 * 
 * @author Eric
 *
 */
public class Tag {

	private String tag;
	
	public Tag(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String toString(){
		return tag;
	}
	
}
