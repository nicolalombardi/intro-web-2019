<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="pageSize" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="selectedPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="showAll" scope="request" type="java.lang.Boolean"/>


<html>
<head>
    <title>Medico Base - Lista Pazienti</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Lista pazienti</h1>
    <div class="container">
        <div class="row">
            <div class="col-sm">
                <c:choose>
                    <c:when test="${showAll}">
                        <a class="btn btn-primary" href="lista?page=${selectedPage}&pageSize=${pageSize}&mostraTutti=false" role="button">Mostra solo i tuoi pazienti</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary" href="lista?page=${selectedPage}&pageSize=${pageSize}&mostraTutti=true" role="button">Mostra tutti i pazienti</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm">
                <nav aria-label="Navigazione lista pazienti">
                    <span class="btn-group"></span>
                    <ul class="pagination justify-content-center">
                        <li class="page-item <c:if test="${selectedPage == 1}">disabled</c:if>">
                            <a class="page-link" href="lista?page=${selectedPage - 1}&pageSize=${pageSize}&mostraTutti=${showAll}"
                               <c:if test="${selectedPage == 1}">tabindex="-1"</c:if> >Precedente</a>
                        </li>

                        <jsp:useBean id="pagesCount" scope="request" type="java.lang.Integer"/>
                        <c:forEach var="i" begin="1" end="${pagesCount}">
                            <li class="page-item <c:if test="${selectedPage == i}">active</c:if> "><a class="page-link"
                                                                                                      href="lista?page=${i}&pageSize=${pageSize}&mostraTutti=${showAll}">${i}</a>
                            </li>
                        </c:forEach>

                        <li class="page-item <c:if test="${selectedPage == pagesCount}">disabled</c:if>">
                            <a class="page-link" href="lista?page=${selectedPage + 1}&pageSize=${pageSize}&mostraTutti=${showAll}"
                               <c:if test="${selectedPage == 1}">tabindex="-1"</c:if> >Successiva</a>
                        </li>
                    </ul>

                </nav>
            </div>
            <div class="col-sm">
                <div class="btn-group">
                    <label class="label" for="pageSizeDropdown">Elementi: </label>
                <button id="pageSizeDropdown" class="btn btn-secondary btn-sm dropdown-toggle" type="button"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${pageSize}
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="lista?page=${selectedPage}&pageSize=10&mostraTutti=${showAll}">10</a>
                    <a class="dropdown-item" href="lista?page=${selectedPage}&pageSize=15&mostraTutti=${showAll}">15</a>
                    <a class="dropdown-item" href="lista?page=${selectedPage}&pageSize=30&mostraTutti=${showAll}">30</a>
                </div>
            </div>
            </div>
        </div>
    </div>

    <div class="table-responsive-md">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tuo</th>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>
                <th scope="col">Sesso</th>
                <th scope="col">Data di nascita</th>
                <th scope="col">Luogo di nascita</th>
                <th scope="col">Codice fiscale</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="listaPazienti" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.Paziente>"/>
            <c:forEach var="p" items="${listaPazienti}">
                <tr data-href="/medico-base/scheda-paziente?id=${p.id}">
                    <td>
                        <c:choose>
                            <c:when test="${user.id == p.medico.id}">
                                <i class="material-icons info-icon">check_box</i>
                            </c:when>
                            <c:otherwise>
                                <i class="material-icons info-icon">check_box_outline_blank</i>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td scope="row">${p.nome}</td>
                    <td>${p.cognome}</td>
                    <td>${p.sesso}</td>
                    <td>${p.dataNascita}</td>
                    <td>${p.luogoNascita}</td>
                    <td>${p.codiceFiscale}</td>
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
