package com.xmpptask.models;

public class Tag extends Subject {

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
