<%@ page import="com.icecoldbier.persistence.entities.User" %>
<!DOCTYPE html>






<!-- TODO: DELETE THIS PAGE AT SOME POINT -->





<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restricted page</title>
</head>
<body>
If you see this you are now logged in :)
User object dump:
<%
User u = (User) request.getSession().getAttribute("user");
out.println(u.toString());
%>

</body>
</html>