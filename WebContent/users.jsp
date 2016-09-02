<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registered Users</title>
</head>
<body>
	<center>
		<h1>Users Registered</h1>

		<table border="1">
			<tr>
				<th>A/A</th>
				<th>Email</th>
				<th>Password</th>
			</tr>

			<c:forEach var="usr" items="${sessionScope.Users}"
				varStatus="vStatus">
				<tr>
					<td>${vStatus.count}</td>
					<td>${usr.email}</td>
					<td>${usr.password}</td>
				</tr>
				<tr>
			</c:forEach>

		</table>
		<a href="/BookStore/Connector?act=showAdminOperations">Admin Menu</a> <br /> <a
			href="/BookStore/Connector?act=logOff">Log Off</a>
	</center>
</body>
</html>