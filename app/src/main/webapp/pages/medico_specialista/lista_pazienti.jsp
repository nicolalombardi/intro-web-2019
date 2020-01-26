<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaPazientiSpecialista" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.Paziente>"/>


<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Medico Specialista - Lista Pazienti</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.css"/>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Lista pazienti</h1>

    <table class="datatable table table-striped table-hover">
        <thead class="thead-dark">
        <tr>
            <th class="all" scope="col">Nome</th>
            <th class="all" scope="col">Cognome</th>
            <th class="min-sm" scope="col">Sesso</th>
            <th class="min-md" scope="col">Data di nascita</th>
            <th class="min-md" scope="col">Luogo di nascita</th>
            <th class="min-lg" scope="col">Codice fiscale</th>
            <th class="min-lg" scope="col">Scheda</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${listaPazientiSpecialista}">
            <tr>
                <td><c:out value="${p.nome}"/></td>
                <td><c:out value="${p.cognome}"/></td>
                <td><c:out value="${p.sesso}"/></td>
                <td><c:out value="${p.dataNascita}"/></td>
                <td><c:out value="${p.luogoNascita}"/></td>
                <td><c:out value="${p.codiceFiscale}"/></td>
                <td><a class="btn btn-primary mb-3 icon-white"
                       href="scheda-paziente?id=<c:out value="${p.id}"/>"
                       role="button"><i class="material-icons">open_in_new</i></a></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
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
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.js"></script>

<script src="../js/init_datatables.js"></script>
</body>
</html>
