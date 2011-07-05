<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Register to record your tasks</title>
</head>
<body>
	<form action="/user/" method="POST">
		XMPP Email Address: <input name="email" type="text"/><br/>
		Password: <input name="password" type="password"/><br/>
		Confirm Password: <input name="passwordConfirm" type="password"/><br/>
		<input text="Submit" type="submit"/>
	</form>
</body>
</html>