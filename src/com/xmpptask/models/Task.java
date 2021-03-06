package com.xmpptask.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * 
 * Task represents one discrete task.
 * Tasks have one id, many tags, can have a parent, an optional due date,
 * an optional priority, and prerequisite tasks.
 * 
 * @author Eric
 *
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Task {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	@Embedded
	private Id id;
	
	@Persistent
	private String taskText;
	
	@Persistent
	private Set<String> tags;
	
	@Persistent
	@Element(dependent = "true")
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="id asc"))
	private List<Task> children;
	
	@Persistent
	private Key parentKey;
	
	@Persistent
	private Key user;
	
	@Persistent
	private Date dueOn;
	
	@Persistent
	private Date createdOn;
	
	@Persistent
	private boolean completed;
	
	@Persistent
	private Date completedOn;
	
	@Persistent
	private int priority;
	
	@Persistent
	private Set<String> prerequisites;
	
	public Task(){
		tags = new HashSet<String>();
		prerequisites = new HashSet<String>();
		children = new ArrayList<Task>();
	}
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		if(this.id == id)
			return;
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Date getCompletedOn() {
		return completedOn;
	}
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}
	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	public Set<String> getTags() {
		return tags;
	}
	public void addTag(String tag){
		this.tags.add(tag);
	}
	public void setContexts(Set<String> tags) {
		this.tags = tags;
	}
	/*public Task getParentId() {
		return parentId;
	}
	public void setParentId(Task parentId) {
		this.parentId = parentId;
	}*/
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
	public Set<String> getPrerequisites() {
		return prerequisites;
	}
	public void setPrerequisites(Set<String> prerequisites) {
		this.prerequisites = prerequisites;
	}
	public void addPrereq(String prereq){
		this.prerequisites.add(prereq);
	}
	public Key getUser() {
		return user;
	}
	public void setUser(Key user) {
		this.user = user;
	}
	public List<Task> getChildren(){
		return this.children;
	}
	public Key getParentKey() {
		return parentKey;
	}
	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
}
