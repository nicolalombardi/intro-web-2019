<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="statusCode" value="${requestScope['javax.servlet.error.status_code']}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Errore <c:out value="${statusCode}"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

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

        .error-box{
            font-size: 15px;
            text-align: left;
            padding: 2px;
            border: solid 1px #3e3e3e;
            color: black;
            border-radius: 3px;
        }
        .errorBoxWrapper{
            max-width: 50%;
            margin: auto;
            color: #ff483f;
            font-size: 16px;
            margin-bottom: 10px;
            margin-top: 30px;
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
    <div class="errorBoxWrapper">
        Messaggio di errore
        <div id="errorBox" class="error-box">
            <c:out value="${requestScope['javax.servlet.error.message']}"/>
        </div>
    </div>

    <a href="/login">Torna alla home</a>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script>
    $('.collapse').collapse();
</script>
</body>
</html>