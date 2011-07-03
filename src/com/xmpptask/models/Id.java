package com.xmpptask.models;

public class Id extends Subject{

	private String id;
	
	public Id(String id){
		this.id = id;
	}
	
	@Override
	public String toString(){
		return id;
	}
}
