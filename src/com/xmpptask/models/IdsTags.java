package com.xmpptask.models;

import java.util.ArrayList;
import java.util.List;

public class IdsTags {

	private List<Id> ids;
	private List<Tag> tags;
	
	public IdsTags(){
		ids = new ArrayList<Id>();
		tags = new ArrayList<Tag>();
	}

	public void addId(Id id){
		ids.add(id);
	}
	
	public void addTag(Tag t){
		tags.add(t);
	}

	public List<Id> getIds() {
		return ids;
	}

	public List<Tag> getTags() {
		return tags;
	}
	
}
