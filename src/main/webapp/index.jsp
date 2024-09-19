<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page Login</title>
</head>
<body>
<form action="ControlServlet" method="post">
    <label> Username: </label>
    <input type="text" name="username"> <br>
    <br>
    <label> Password: </label>
    <input type="password" name="password"> <br>
    <input type="submit" value="Login" name="submit">
</form>
</body>
</html>