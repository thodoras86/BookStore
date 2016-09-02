<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
</head>
<body>
	<center>
		<form action="Connector?act=doLogin" method="post">
			<input type="hidden" name="act" value="doLogin">

			<%-- Access bean created by Controller with actions --%>
			<jsp:useBean id="userBean" class="model.User" scope="request">
				<%-- set bean properties with jstl --%>
				<c:set target="${requestScope.userBean}" var="email" value=""></c:set>
				<c:set target="${requestScope.userBean}" var="password" value=""></c:set>
				<c:set target="${requestScope.userBean}" var="message" value=""></c:set>
			</jsp:useBean>

			<%-- use of EL --%>
			Email: <input type="text" name="email" value="${userBean.email}" />
			<br /> <br /> Password: <input type="password" name="password"
				value="${userBean.password}" /> <br /> <br /> <input
				type="submit" value="Submit" />
		</form>

		<h2>
			<b>${requestScope.userBean.message}</b>
		</h2>
	</center>
	<a href="/BookStore/index.jsp">Home Page</a>
</body>
</html>