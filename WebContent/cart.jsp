<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="userSpecific.Cart,java.util.*,bean.UniqueItem"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ps"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cart includes</title>
</head>
<body>

	<center>
		<c:choose>
			<c:when test="${Cart.items.size() == 0}">
				<h1>Your cart is empty!</h1>
			</c:when>

			<c:otherwise>
				<h1>Your cart includes:</h1>

				<table border="1">

					<tr>
						<th>Product's Id</th>
						<th>Title</th>
						<th>Quantity</th>
					</tr>


					<%-- iterate with JSTL forEach tag --%>

					<c:forEach var="uitem" items="${Cart.items}">
						<tr>
							<td><c:out value='${uitem.objId}' /></td>
							<td><c:out value='${uitem.b.title}' /></td>
							<%-- <td><c:out value='${uitem.b.description}' /></td> --%>
							<td><c:out value='${uitem.copies}' /></td>
							<td><a href="Connector?act=remove&it=${uitem.objId}"><button
										type="button">Remove item</button></a></td>
						</tr>
					</c:forEach>
				</table>
				<a href="Connector?act=clear"><button type="button">Empty
						Cart</button></a>
				<br />
				<br />
			</c:otherwise>
		</c:choose>


		<a href="Connector?act=showProducts">Continue Shopping</a>
		<c:if test="${Cart.items.size() > 0}">
			<br />
			<a href="Connector?act=doOrder">Proceed with order</a>
		</c:if>
		<br /> <a href="Connector?act=logOff">Log Off</a>
	</center>


</body>
</html>