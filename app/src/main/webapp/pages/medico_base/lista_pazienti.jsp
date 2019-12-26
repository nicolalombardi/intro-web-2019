<%@ page import="com.icecoldbier.persistence.entities.Paziente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Medico Base - Lista Pazienti</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Lista pazienti</h1>

    <nav aria-label="Navigazione lista pazienti">
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a class="page-link" href="/medico-base/lista?page=${page - 1}" <c:if test="${page == 1}">tabindex="-1"</c:if> >Precedente</a>
            </li>

            <c:forEach var = "i" begin = "1" end = "${pagesCount}">
                <li class="page-item <c:if test="${page == i}">active</c:if> "><a class="page-link" href="/medico-base/lista?page=${i}">${i}</a></li>
            </c:forEach>

            <li class="page-item <c:if test="${page == pagesCount}">disabled</c:if>">
                <a class="page-link" href="/medico-base/lista?page=${page + 1}" <c:if test="${page == 1}">tabindex="-1"</c:if> >Successiva</a>
            </li>
        </ul>
    </nav>

    <div class="table-responsive-md">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>
                <th scope="col">Sesso</th>
                <th scope="col">Data di nascita</th>
                <th scope="col">Luogo di nascita</th>
                <th scope="col">Codice fiscale</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${listaPazienti}">
                    <tr data-href="/medico-base/scheda-paziente?id=${p.getId()}">
                        <th scope="row">${p.getNome()}</th>
                        <th>${p.getCognome()}</th>
                        <th>${p.getSesso()}</th>
                        <th>${p.getDataNascita()}</th>
                        <th>${p.getLuogoNascita()}</th>
                        <th>${p.getCodiceFiscale()}</th>
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
