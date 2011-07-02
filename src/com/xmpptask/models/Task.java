package com.xmpptask.models;

import java.util.Date;
import java.util.List;

/**
 * 
 * Task represents one discrete task.
 * Tasks have one id, many tags, can have a parent, an optional due date,
 * an optional priority, and prerequisite tasks.
 * 
 * @author Eric
 *
 */
public class Task {

	private String id;
	private String taskText;
	private List<String> tags;
	private String parentId;
	private Date dueOn;
	private int priority;
	private List<String> prerequisites;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setContexts(List<String> tags) {
		this.tags = tags;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Date getDueOn() {
		return dueOn;
	}
	public void setDueOn(Date dueOn) {
		this.dueOn = dueOn;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public List<String> getPrerequisites() {
		return prerequisites;
	}
	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}
}
