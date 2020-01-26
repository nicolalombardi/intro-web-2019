<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Medico Specialista - Scheda paziente</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Scheda del paziente</h1>
    <div class="container">
        <div class="row">
            <%--            Dati paziente--%>
            <div class="col">
                <div class="row">
                    <table class="table profile-table">
                        <tbody>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <c:choose>
                                    <c:when test="${not empty paziente.foto}">
                                        <img class="profile-picture" src="<c:out value="${paziente.foto}"/>">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="profile-picture" src="/images/profile_placeholder.svg">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th><b>Nome</b></th>
                            <td><c:out value="${paziente.nome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Cognome</b></th>
                            <td><c:out value="${paziente.cognome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Email</b></th>
                            <td><c:out value="${paziente.username}"/></td>
                        </tr>
                        <tr>
                            <th><b>Provincia di appartenenza</b></th>
                            <td><c:out value="${paziente.provinciaAppartenenza}"/></td>
                        </tr>
                        <tr>
                            <th><b>Data di nascita</b></th>
                            <td><c:out value="${paziente.dataNascita}"/></td>
                        </tr>
                        <tr>
                            <th><b>Luogo di nascita</b></th>
                            <td><c:out value="${paziente.luogoNascita}"/></td>
                        </tr>
                        <tr>
                            <th><b>Codice fiscale</b></th>
                            <td><c:out value="${paziente.codiceFiscale}"/></td>
                        </tr>
                        <tr>
                            <th><b>Sesso</b></th>
                            <td><c:out value="${paziente.sesso}"/></td>
                        </tr>
                        <tr>
                            <th><b>Medico di base</b></th>
                            <td><c:out value="${medico.nome} ${medico.cognome}"/></td>
                        </tr>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>
