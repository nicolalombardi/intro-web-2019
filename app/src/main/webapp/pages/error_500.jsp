<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
Errore 500
<%
    out.println("Causa: " + exception.getCause() + "</br>");
    out.println("Messaggio: " + exception.getMessage());
%>
</html>