<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="java.util.*"%>
   <%@page import="com.movie.bean.MovieBean"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>the newest movies from mp4ba</title>
</head>
<body>
	<table>
		<caption>newest movies update</caption>
		<tr>
			<td>the name of movie</td>
			<td>the download site</td>
		</tr>
		<%
			List<MovieBean> moveList = (List<MovieBean>)request.getAttribute("list");
			for(MovieBean mb : moveList){
		%>
		<tr>
			<td border="1" bgcolor="gray"><%=mb.getName() %></td>
			<td><a href='<%=mb.getUrl() %>'>download site</a></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>