<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.ArrayList,model.Books"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Books Store</title>
</head>
<body>

	<center>
		<h1>Available Products</h1>

		<table border="1">
			<tr>
				<td>Id</td>
				<td>Title</td>
				<td>Price</td>
			</tr>

			<%-- iterate with jstl tag --%>
			<c:forEach var="item" items="${sessionScope.booksList}">
				<tr>
					<td>${item.id}</td>
					<td><a href="/BookStore/Connector?act=itemInf&bId=${item.id}">${item.title}</a></td>
					<td>${item.price}</td>
				</tr>
			</c:forEach>

		</table>

		<c:if test="${not empty sessionScope.CurrentItemId}">
			<c:remove var="CurrentItemId" />
		</c:if>
		<c:if test="${not empty sessionScope.Copies}">
			<c:remove var="Copies" />
		</c:if>
		<br /> <a href="/BookStore/Connector?act=showCart">View Cart</a> <br /> <a
			href="/BookStore/Connector?act=orderHist">Show your older
			transactions</a> <br /> <a href="/BookStore/Connector?act=logOff">Log
			Off</a>

	</center>

</body>
</html>