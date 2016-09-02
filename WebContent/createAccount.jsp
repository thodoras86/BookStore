<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account creation</title>
</head>
<body>
	<center>
		<form action="Connector?act=doCreation" method="post">
			<input type="hidden" name="act" value="doCreation"> Email: <input
				type="text" name="email" value="${requestScope.email}" /> <br /> <br />
			Password: <input type="password" name="password"
				value="${requestScope.password}" /> <br /> <br /> Retype
			Password: <input type="password" name="password2"
				value="${requestScope.password2}" /> <br /> <br /> <input
				type="submit" value="Submit" />
		</form>
		<h2>
			<b>${requestScope.message}</b>
		</h2>
	</center>
	<a href="/BookStore/index.jsp">Home Page</a>
</body>
</html>