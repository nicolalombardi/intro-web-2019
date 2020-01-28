<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
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
            <th class="all default-sort" scope="col">Paziente</th>
            <th class="all" scope="col">Sesso</th>
            <th class="min-md" scope="col">Data di nascita</th>
            <th class="min-md" scope="col">Luogo di nascita</th>
            <th class="min-lg" scope="col">Codice fiscale</th>
            <th class="min-lg" scope="col">Scheda</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${listaPazientiSpecialista}">
            <tr>
                <td><mp:miniProfileTag paziente="${p}"/></td>
                <td><c:out value="${p.sesso}"/></td>
                <td><c:out value="${p.prettyDataNascita}"/></td>
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

<script>
    var specialista = true;
</script>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.0/dist/latest/bootstrap-autocomplete.min.js"></script>

<script src="../js/init_datatables.js"></script>
<script src="../js/ricerca_pazienti.js"></script>
</body>
</html>
