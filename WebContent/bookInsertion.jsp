<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.Books"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Book</title>
</head>
<body>
	<center>
		<form action="Connector?act=doInsertBook" method="get">
			<input type="hidden" name="act" value="doInsertBook">

			<jsp:useBean id="bookBean" class="model.Books" scope="request">
				<c:set target="${requestScope.bookBean}" var="title" value=""></c:set>
				<c:set target="${requestScope.bookBean}" var="description" value="ok"></c:set>
				<c:set target="${requestScope.bookBean}" var="price" value="10"></c:set>
				<c:set target="${requestScope.bookBean}" var="icoName" value=""></c:set>
				<c:set target="${requestScope.bookBean}" var="icoExt" value=""></c:set>
				<c:set target="${requestScope.bookBean}" var="family" value=""></c:set>
			</jsp:useBean>


			Title: <input type="text" name="booksTitle" value="${bookBean.title}"><br /><br />
			Description: <input type="text" name="booksDescr"
				value="${bookBean.description}"><br /><br /> Price: <input
				type="text" name="booksPrice" value="${bookBean.price}"><br /><br />
			Family: <input type="text" name="booksFam"
				value="${bookBean.family}"><br /><br /> Icon Extension: <input
				type="text" name="booksIcoExt" value="${bookBean.icoExt}"><br /><br />
			Icon Name: <input type="text" name="booksIcoName"
				value="${bookBean.icoName}"><br /><br /> <input type="submit"
				value="Insert">
		</form>
 		<h2> 
			<b>${requestScope.bookMessage}</b>
 		</h2> 
		<a href="Connector?act=showAdminOperations">Back</a> <br />
	</center>
</body>
</html>