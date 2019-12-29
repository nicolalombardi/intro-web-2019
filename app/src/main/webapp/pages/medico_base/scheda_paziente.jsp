<%--<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>--%>
<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>
<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Scheda di ${paziente.nome}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Scheda del paziente</h1>
    <div class="container">
        <div class="row">
<%--            Dati paziente--%>
            <div class="col">
                <div class="row">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <c:choose>
                                    <c:when test="${not empty paziente.foto}">
                                        <img src="${paziente.foto}" height="300px" width="300px">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/images/profile_placeholder.svg" height="300px" width="300px">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th><b>Nome</b></th>
                            <td>${paziente.nome}</td>
                        </tr>
                        <tr>
                            <th><b>Cognome</b></th>
                            <td>${paziente.cognome}</td>
                        </tr>
                        <tr>
                            <th><b>Email</b></th>
                            <td>${paziente.username}</td>
                        </tr>
                        <tr>
                            <th><b>Provincia di appartenenza</b></th>
                            <td>${paziente.provinciaAppartenenza}</td>
                        </tr>
                        <tr>
                            <th><b>Data di nascita</b></th>
                            <td>${paziente.dataNascita}</td>
                        </tr>
                        <tr>
                            <th><b>Luogo di nascita</b></th>
                            <td>${paziente.luogoNascita}</td>
                        </tr>
                        <tr>
                            <th><b>Codice fiscale</b></th>
                            <td>${paziente.codiceFiscale}</td>
                        </tr>
                        <tr>
                            <th><b>Sesso</b></th>
                            <td>${paziente.sesso}</td>
                        </tr>
                        <tr>
                            <th><b>Medico di base</b></th>
                            <c:choose>
                                <c:when test="${empty medico}">
                                    <td>Non ancora scelto.</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${medico.nome} ${medico.cognome}</td>
                                </c:otherwise>
                            </c:choose>

                        </tr>
                        </tbody>
                    </table>

                </div>


            </div>
<%--            Funzioni--%>
            <div class="col funzioni-col">
                <h2>Funzioni</h2>
                <div class="row funzioni-row">
                    <a class="btn btn-primary" href="#" role="button">Eroga visita base</a>
                </div>
                <div class="row funzioni-row">
                    <a class="btn btn-primary" href="#" role="button">Visualizza elenco visite base</a>
                </div>
                <div class="row funzioni-row">
                    <a class="btn btn-primary" href="#" role="button">Prescrivi esame specialistico</a>
                </div>
                <div class="row funzioni-row">
                    <a class="btn btn-primary" href="#" role="button">Visualizza elenco esami specialistici prescritti</a>
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
