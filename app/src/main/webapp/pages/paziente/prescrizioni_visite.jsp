<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
<jsp:useBean id="elencoVisiteFuture" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaSpecialisticaOrSSP>"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Elenco Prescrizioni Visite</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.css"/>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Elenco visite prescritte</h1>

        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th class="all" scope="col">Tipo Visita</th>
                <th class="all default-sort" scope="col">Data Prescrizione</th>
                <th class="min-sm" scope="col">Prescritta da</th>
                <th class="min-md" scope="col">Erogante</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${elencoVisiteFuture}">
                <c:choose>
                    <c:when test="${v.SSP}">
                        <tr>
                            <td><c:out value="${v.visitaSSP.tipo_visita.nome}"/></td>
                            <td><c:out value="${v.visitaSSP.prettyDataPrescrizione}"/></td>
                            <td><mp:miniProfileTag user="${v.visitaSSP.medicoBase}"/></td>
                            <td><mp:miniProfileTag ssp="${v.visitaSSP.ssp}"/></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td><c:out value="${v.visitaSpecialistica.tipo_visita.nome}"/></td>
                            <td><c:out value="${v.visitaSpecialistica.prettyDataPrescrizione}"/></td>
                            <td><mp:miniProfileTag user="${v.visitaSpecialistica.medicoBase}"/></td>
                            <td><mp:miniProfileTag user="${v.visitaSpecialistica.medicoSpecialista}"/></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
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
