<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 1/14/20
  Time: 4:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaVisite" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaPossibile>"/>

<html>
<head>
    <title>Lista esami possibili</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>

<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Lista esami possibili</h1>

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a class="page-link" href="/paziente/esami-possibili?page=<c:out value="${page - 1}"/>" <c:if test="${page == 1}">tabindex="-1"</c:if> >Previous</a>
            </li>

            <c:forEach var = "i" begin = "1" end = "${pagesCount}">
                <li class="page-item <c:if test="${page == i}">active</c:if> "><a class="page-link" href="/paziente/esami-possibili?page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
            </c:forEach>

            <li class="page-item <c:if test="${page == pagesCount}">disabled</c:if>">
                <a class="page-link" href="/paziente/esami-possibili?page=<c:out value="${page + 1}"/>" <c:if test="${page == 1}">tabindex="-1"</c:if> >Next</a>
            </li>
        </ul>
    </nav>

    <div class="table-responsive-md">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Nome</th>
                <th scope="col">Praticante</th>
                <th scope="col">Costo ticket</th>
                <th scope="col">Descrizione</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${listaVisite}">
                <tr>
                    <th scope="row"><c:out value="${v.nome}"/></th>
                    <th>
                        <c:choose>
                            <c:when test="${v.praticante == 'medico_specialista'}">medico specialista</c:when>
                            <c:otherwise>ssp</c:otherwise>
                        </c:choose>
                    </th>
                    <th><c:out value="${v.costo_ticket}"/></th>
                    <th><c:out value="${v.descrizione}"/></th>
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