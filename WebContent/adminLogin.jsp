<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Login page</title>
</head>
<body>
	<center>
		<form action="Connector" method="post">
			<input type="hidden" name="act" value="doAdmin">


			<!-- Using standard actions -->
			<jsp:useBean id="userBean" class="model.User" scope="request">
				<%-- Use c:set to set bean properties --%>
				<c:set target="${requestScope.userBean}" property="email" value=""></c:set>
				<c:set target="${requestScope.userBean}" property="password"
					value=""></c:set>
				<c:set target="${requestScope.userBean}" property="message" value=""></c:set>
			</jsp:useBean>


			<%-- Outputs with EL --%>
			Email: <input type="text" name="email" value="${userBean.email}" />
			<br /> <br /> Password: <input type="password" name="password"
				value="${userBean.password}" /> <br /> <br /> Retype Password: <input
				type="password" name="password2"
				value='<c:out value="${requestScope.password2}"></c:out>' /> <br />
			<br /> <input type="submit" value="Submit" />

		</form>

		<h2>
			<b><c:out value="${requestScope.userBean.message}"></c:out></b>
		</h2>
	</center>
	<a href="/BookStore/index.jsp">Home Page</a>
</body>
</html>