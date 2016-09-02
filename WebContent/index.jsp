<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Database manipulations</title>
</head>
<body>

	<c:import url="/header.jsp"></c:import>
	<c:choose>
		<c:when test="${cookie.user.value != null}">
			Hello Again!
		</c:when>
		<c:otherwise>
			<%
				Cookie cook = new Cookie("user", "1");

						cook.setMaxAge(60);
						response.addCookie(cook);
			%>
			Hello Newcomer!
		</c:otherwise>
	</c:choose>

	<center>
		<a href="/BookStore/login.jsp">User Login</a> <br /> <a
			href="/BookStore/createAccount.jsp">Create new account</a> <br /> <a
			href="/BookStore/adminLogin.jsp">Administrator Login</a>
	</center>

	<c:import url="/footer.jsp"></c:import>
</body>
</html>