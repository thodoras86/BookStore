<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Books Information</title>
</head>
<body>

	<center>
		<table border="0">

			<tr>
				<td><img height="140" width="100"
					src="/BookStore/pics/${sessionScope.bookInfo.icoName}${sessionScope.bookInfo.icoExt}"></td>
				<td><c:out value="${sessionScope.bookInfo.title}" /> <br /> <c:out
						value="${sessionScope.bookInfo.description}" /> <br /> <c:out
						value="Price: ${sessionScope.bookInfo.price}$" /> <br />
					<form action="Connector" method="get">
						<input type="hidden" name="act" value="buyItems"> <input
							type="text" name="${sessionScope.bookInfo.id}"
							value="${sessionScope.Copies}" /> <input type="submit"
							value="Add to Cart" />
					</form> <a href="Connector?act=incI"><button type="button">+</button></a>
					<a href="Connector?act=decI"><button type="button">-</button></a></td>
			</tr>

		</table>
		Related Products: <br />

		<c:forEach var="book" items="${sessionScope.relBooks}">
			<a href="Connector?act=relBook&bId=${book.id}"> <img height="140"
				width="100" src="/BookStore/pics/${book.icoName}${book.icoExt}">
			</a>
		</c:forEach>

		<br /> <a href="Connector?act=showProducts"> Back</a>
	</center>
</body>
</html>