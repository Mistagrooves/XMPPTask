package com.xmpptask.commands;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.xmpptask.models.Tag;

public class TagDelete extends Command {

	private List<Tag> tags;
	
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public TagDelete(List<Tag> tags){
		this.tags = tags;
	}
	
	@Override
	public CommandResult execute(PersistenceManager pm) {
		// TODO Auto-generated method stub
		return new CommandResult();
	}

}
