<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,model.Order"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>History of Orders</title>
<%-- Common for users and administrators --%>
</head>

<body>
	<%-- Adjust page's title --%>

	<c:set var="tit" scope="page"
		value='${sessionScope.UsersId == null ? "All orders being committed to website" : "Your previous orders"}'></c:set>

	<center>
		<h1>${pageScope.tit}</h1>

		<table border="1">
			<tr>
				<td>OrderId</td>
				<td>BookTitle</td>
				<td>DateCommited</td>
			</tr>

			<c:choose>
				<c:when test="${sessionScope.UsersId == null}">
					<%-- administrator : allOrders --%>
					<c:forEach var="ord" items="${sessionScope.AllOrders}">
						<tr>
							<td><c:out value="${ord.ordId}"></c:out></td>
							<td><ul>
									<c:forEach var="booksOr" items="${ord.books}">
											<li>${booksOr.title}</li>
									</c:forEach>
								</ul></td>
							<td><c:out value="${ord.dateBought}"></c:out></td>
						</tr>
					</c:forEach>
				</c:when>

				<c:otherwise>
					<%-- administrator : userOrders --%>
					<c:forEach var="ord" items="${sessionScope.UserOrders}">
						<tr>
							<td><c:out value="${ord.ordId}"></c:out></td>
							<td><ul>
									<c:forEach var="booksOr" items="${ord.books}">
											<li>${booksOr.title}</li>
									</c:forEach>
								</ul></td>
							<td><c:out value="${ord.dateBought}"></c:out></td>
						</tr>
					</c:forEach>
				</c:otherwise>

			</c:choose>


		</table>
		<c:if test="${sessionScope.UsersId != null}">
			<a href="/BookStore/Connector?act=showProducts">Continue Shopping</a>
			<br />
		</c:if>
		<c:if test="${sessionScope.UsersId == null}">
			<a href="/BookStore/Connector?act=showAdminOperations">Admin Menu</a>
			<br />
		</c:if>
		<a href="/BookStore/Connector?act=logOff">LogOff</a>
	</center>
</body>
</html>