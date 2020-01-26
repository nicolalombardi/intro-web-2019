<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="statusCode" value="${requestScope['javax.servlet.error.status_code']}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Errore 404</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<%--    <link rel="stylesheet" type="text/css" href="css/style.css">--%>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <style>
        html{
            height: 100%;
            width: 100%;
        }
        body{
            background-color: #f5f5f5 !important;
            font-size: 1.4rem;
            color: #5b5b5b;
            text-align: center;
            display: table;
            height: 100%;
            width: 100%;
        }
        img{
            width: 50%;
            max-width: 500px;
        }
        a{
            font-size: 1rem;
            color: #5b5b5b;
            text-decoration: underline;
        }
        .container{
            display: table-cell;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${statusCode eq 400}">
            <c:set var="image" value="400"/>
            <c:set var="message" value="La richiesta che hai effettuato non è valida"/>
        </c:when>
        <c:when test="${statusCode eq 401}">
            <c:set var="image" value="401"/>
            <c:set var="message" value="Non sei autorizzato a visualizzare la pagina richiesta"/>
        </c:when>
        <c:when test="${statusCode eq 404}">
            <c:set var="image" value="404"/>
            <c:set var="message" value="La pagina che stavi cercando non esiste"/>
        </c:when>
        <c:when test="${statusCode eq 500}">
            <c:set var="image" value="500"/>
            <c:set var="message" value="Il server ha riscontrato un errore, riprova più tardi"/>
        </c:when>
        <c:otherwise>
            <c:set var="image" value="generic"/>
            <c:set var="message" value="È stato riscontrato un errore non identificato, riprova più tardi"/>
        </c:otherwise>
    </c:choose>

    <img src="<c:out value="/images/error_${image}.png"/>" alt="errore <c:out value="${statusCode}"/>">
    <h2>Errore <c:out value="${statusCode}"/></h2>
    <h4><c:out value="${message}"/></h4>
    <a href="/login">Torna alla home</a>
</div>
</body>
</html>