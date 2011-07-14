A simple bot that will record tasks for you.

Inspired by [todo.txt](http://todotxt.com/ "todo.txt")

Syntax is unstable and subject to change at my whims.

Currently supported commands (in short-bastardized EBNF)
========================================================
1. Help
	- ("h" | "help" | "?")
2. List Tasks
	- ("l" | "list")
3. Add a task
	- ("+" | "a" | "add") <task>
4. Add a sub-task
	- @<id> <task>
5. Delete a task
	- ( "-" | "d" | "delete") @<id>
6. task = all characters
	- Any text string, can be tagged with #<tag>
7. id = "@" , 0-9[,{ .0-9 }]
	- Unique id for the task. 
8. tag = "#" , {all characters - space}
	- Tags for the task

Future
======
1. Search on tags
	- "#", {all characters}
2. Completion:
	- ( "c" | "complete" ) @id
3. Increase Priority
	- ( ">" ), <id>
4. Decrease Priority
	- ( "<" ), <id>
5. Automatic Sub Tagging
	- #<tag>-<tag> becomes #<tag> and #<tag>-<tag>
6. Due Date
	- <id> is due on <date>
	- + <task> 
7. Lists
	- ":", {all characters - space}
	- tags work cross list
8. Multi-User/Collabrative Lists
	- Users of the same domain can access lists on the same domain, black list certain domains (ie. gmail)
	- Teams of people can share a list
		- eric@company.com:list1 + <task>
		- eric:list1 + <task>
		- e:list1 + <task>
	- smallest possible unique id for users
	- Notifications sent to users on list additions, task assignment, task completion
	- List Lists (ll) -> list all the lists you have access to.
	- add observers of lists, tasks -> owner of list is automatically notified on changes
	- Batch up notifications when offline.
	
Questionable
============
1. Dropping @id, making it just 0-9[,{.0-9}]
2. Making more natural. "Add fix bug 3233 to 3"
3. full text search -> tougher to do GAE

Integration with existing PM systems. Rally, FogBugz
