<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 1/3/20
  Time: 10:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="elencoVisiteSpecialistiche" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaSpecialistica>"/>
<jsp:useBean id="elencoVisiteSSP" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaSSP>"/>
<html>
<head>
    <title>Elenco Prescrizioni Visite</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<%@ include file="navbar.html" %>


<div class="container">
    <h1>Elenco visite prescritte</h1>

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a class="page-link" href="/paziente/elenco-prescrizioni-visite?page=<c:out value="${page - 1}"/>" <c:if test="${page == 1}">tabindex="-1"</c:if> >Previous</a>
            </li>

            <c:forEach var = "i" begin = "1" end = "${pagesCount}">
                <li class="page-item <c:if test="${page == i}">active</c:if> "><a class="page-link" href="/paziente/elenco-prescrizioni-visite?page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
            </c:forEach>

            <li class="page-item <c:if test="${page == pagesCount}">disabled</c:if>">
                <a class="page-link" href="/paziente/elenco-prescrizioni-visite?page=<c:out value="${page + 1}"/>" <c:if test="${page == 1}">tabindex="-1"</c:if> >Next</a>
            </li>
        </ul>
    </nav>

    <div class="table-responsive-md">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tipo Visita</th>
                <th scope="col">Data Prescrizione</th>
                <th scope="col">Medico Base</th>
                <th scope="col">Praticante</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${elencoVisiteSpecialistiche}">
                <tr>
                    <th scope="row"><c:out value="${v.tipo_visita.nome}"/></th>
                    <th scope="row"><c:out value="${v.dataPrescrizione}"/></th>
                    <th scope="row"><c:out value="${v.medicoBase.toStringNomeCognome()}"/></th>
                    <th scope="row"><c:out value="${v.medicoSpecialista.toStringNomeCognome()}"/></th>
                </tr>
            </c:forEach>
            <c:forEach var="v" items="${elencoVisiteSSP}">
                <tr>
                    <th scope="row"><c:out value="${v.tipo_visita.nome}"/></th>
                    <th scope="row"><c:out value="${v.dataPrescrizione}"/></th>
                    <th scope="row"><c:out value="${v.medicoBase.toStringNomeCognome()}"/></th>
                    <th scope="row"><c:out value="${v.ssp.toStringSSP()}"/></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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
<script src="../js/clickable_row.js"></script>
</body>
</html>
