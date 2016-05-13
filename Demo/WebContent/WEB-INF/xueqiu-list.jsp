<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>show me the photos</title>

</head>
<body>
	<h1>
		show all photos and source from
		<%=request.getAttribute("source")%></h1>
	<%-- 
<%=request.getAttribute("result") %>
 --%>
	<%
		String str = (String) request.getAttribute("result");
		String[] arr = str.split(";");
		request.setAttribute("index", 3);
	%>
	${index}
	<%=arr.length%>
	<%=arr[0]%>
	<br>
	<br>
	<br>

	<c:forEach var="age" items="<%=arr%>">
		<c:out value="${age}" />
	</c:forEach>


	<%
		for (int i = 0; i < arr.length; i++) {
			request.setAttribute("index", i);
	%>
	<span>test</span>
	<span>${arr[index]}</span>
	<br>
	<%
		}
	%>
</body>

</html>