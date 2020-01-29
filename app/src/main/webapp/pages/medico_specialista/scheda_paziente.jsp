<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
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
                                <img class="profile-picture" src="<c:out value="${paziente.foto}"/>">
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
                            <td><c:out value="${paziente.prettyDataNascita}"/></td>
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
                            <td><mp:miniProfileTag user="${paziente.medico}"/></td>
                        </tr>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
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

<script src="../js/init_non_datatable_popover.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.0/dist/latest/bootstrap-autocomplete.min.js"></script>
<script src="../js/ricerca_pazienti.js"></script>
</body>
</html>
