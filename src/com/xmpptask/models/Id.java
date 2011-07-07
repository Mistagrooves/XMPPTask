package com.xmpptask.models;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Embedded;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a static id of a task in the form a.b.c.d
 * Allows users to refer to them using the @ symbol (@1.2.3.4)
 * 
 * @author Eric
 *
 */
@PersistenceCapable
@EmbeddedOnly
public class Id{

	@Persistent
	private String id;
	
	public Id(String id){
		this.id = id;
	}
	
	@Override
	public String toString(){
		return id;
	}
	
	/**
	 * gets the first child of this id
	 * @return
	 */
	public Id getFirstChildId(){
		return new Id(this.id + ".1");
	}
	
	/**
	 * takes an id object and increments the lowest ordinal by one 1.2.3 -> 1.2.4
	 * @param id
	 * @return
	 */
	public static Id incrementId(Id id){
		String[] ids = id.toString().split("\\.");
		//get the last number, parse to int, increment, and assign back to the appropriate index
		ids[ids.length - 1] = String.valueOf((Integer.parseInt(ids[ids.length - 1]) + 1)); 
		return new Id(StringUtils.join(ids, '.'));
	}
	
}
