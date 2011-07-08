package com.xmpptask.commands;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.datastore.JDOConnection;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.xmpptask.models.Id;
import com.xmpptask.models.Task;
import com.xmpptask.models.User;

public class IdDelete extends Command {

	private List<Id> ids;
	
	public List<Id> getIds() {
		return ids;
	}
	public void setIds(List<Id> ids) {
		this.ids = ids;
	}
	public IdDelete(List<Id> ids){
		this.ids = ids;
	}
	
	private void relabelIds(List<Task> tasks, List<Integer> counts){
		if(tasks.isEmpty()) return;
		
		Integer i = new Integer(0);
		counts.add(i);
		
		//int lastindex = counts.size() - 1;
		//iterate over all the children and update their ids
		for(Task t : tasks){
			counts.set(counts.size() - 1, ++i);
			t.setId(new Id(StringUtils.join(counts, '.')));
			relabelIds(t.getChildren(), counts);
		}
		
		counts.remove(counts.size() - 1);
		
	}
	
	@Override
	public CommandResult execute(PersistenceManager pm) {
		
		Query q = pm.newQuery(Task.class);
		q.setFilter("id == idParam && user == userParam");
		q.declareParameters("String idParam, " + Key.class.getName() + " userParam");
		
		List<Key> reid = new ArrayList<Key>();
		
		CommandResult cr = new CommandResult();
		for(Id id : ids){
			//this should only return one task
			List<Task> tasks = (List<Task>)q.execute(id.toString(), this.user.getKey());
			if(tasks.isEmpty()){
				cr.append("IdDeleteCommand.missing", id.toString());
			}else{
				reid.add(tasks.get(0).getParentKey());
				pm.deletePersistent(tasks.get(0));
				cr.append("IdDeleteCommand.success", id.toString());
			}
		}
		q.closeAll();

		List<Task> children = (List<Task>)this.user.getTasks();
		List<Integer> counters = new ArrayList<Integer>();
		relabelIds(children, counters);
		//iterate over all the children and update their ids
		/*for(Task t : children){
			
			if(t.getId() == null) continue;
			
			int count = StringUtils.countMatches(t.getId().toString(), ".");
			if(count + 1 > counters.length){
				Integer[] newcounters = new Integer[count + 1];
				System.arraycopy(counters, 0, newcounters, 0, counters.length);
				counters = newcounters;
				counters[counters.length - 1] = 0;
			}
			
			//increment the current index
			counters[count]++;
			t.setId(new Id(StringUtils.join(counters, '.')));
		}*/
	
		
		
		
		return cr;
	}

}
