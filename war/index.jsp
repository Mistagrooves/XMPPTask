<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Register to record your tasks</title>
</head>
<body>
	<h1>Register for XMPP Task</h1>
	<div id="inset">
		<form action="/user/" method="POST">
			XMPP Email Address: <input name="email" type="text"/><br/>
			Password: <input name="password" type="password"/><br/>
			Confirm Password: <input name="passwordConfirm" type="password"/><br/>
			<input text="Submit" type="submit"/>
		</form>
		<div>
			<h2>What can I do with XMPP Task?</h2>
			<p>Use it to track tasks using any IM client</p>
			<h2>What are the commands?</h2>
			<p>
				<p>Help</p>
				<ul>
					<li>h</li>
					<li>help</li>
					<li>?</li>
				</ul>
				<p>List</p>
				<ul>
					<li>l</li>
					<li>list</li>
				</ul>
				<p>Add a task</p>
				<ul>
					<li>+ &lt;task&gt;</li>
					<li>a &lt;task&gt;</li>
					<li>add &lt;task&gt;</li>
				</ul>
				<p>Add a sub-task</p>
				<ul>
					<li>@&lt;id&gt; &lt;task&gt;</li>
				</ul>
				<p>Delete a task</p>
				<ul>
					<li>- @&lt;id&gt;</li>
					<li>d @&lt;id&gt;</li>
					<li>delete @&lt;id&gt;</li>
				</ul>
			</p>
		</div>
	</div>
</body>
</html>