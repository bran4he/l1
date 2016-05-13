<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Dark Login Form</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <form method="post" action="login" class="login">
    
    <p>
      <label for="login">Email:</label>
      <input type="text" name="username" id="login" placeholder="your username">
    </p>

    <p>
      <label for="password">Password:</label>
      <input type="password" name="password" id="password" placeholder="your password">
    </p>

    <p class="login-submit">
      <button type="submit" class="login-button">Login</button>
    </p>

    <p class="forgot-password"><a href="index.jsp">Forgot your password?</a></p>
  </form>
  
</body>
</html>