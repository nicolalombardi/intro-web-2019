<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="pageParams" scope="request" type="com.icecoldbier.utils.pagination.PaginationParameters"/>
<jsp:useBean id="showAll" scope="request" type="java.lang.Boolean"/>


<html>
<head>
    <title>Medico Base - Lista Pazienti</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Lista pazienti</h1>
    <div class="container">
        <div class="row">
            <div class="col-lg-4 div-centered mb-3 mb-lg-0">
                <c:choose>
                    <c:when test="${showAll}">
                        <a class="btn btn-primary"
                           href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>&mostraTutti=false"
                           role="button">Mostra solo i tuoi pazienti</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary"
                           href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>&mostraTutti=true"
                           role="button">Mostra tutti i pazienti</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-4">
                <nav aria-label="Navigazione lista pazienti">
                    <span class="btn-group"></span>
                    <ul class="pagination justify-content-center">
                        <li class="page-item <c:if test="${pageParams.page == 1}">disabled</c:if>">
                            <a class="page-link"
                               href="lista?page=<c:out value="${pageParams.page - 1}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>&mostraTutti=<c:out value="${showAll}"/>"
                               <c:if test="${pageParams.page == 1}">tabindex="-1"</c:if> >Precedente</a>
                        </li>

                        <c:forEach var="i" begin="1" end="${pageParams.pagesCount}">
                            <li class="page-item <c:if test="${pageParams.page == i}">active</c:if> "><a
                                    class="page-link"
                                    href="lista?page=${i}&pageSize=<c:out value="${pageParams.pageSize}"/>&mostraTutti=<c:out value="${showAll}"/>">${i}</a>
                            </li>
                        </c:forEach>

                        <li class="page-item <c:if test="${pageParams.page == pageParams.pagesCount  || pageParams.pagesCount == 0}">disabled</c:if>">
                            <a class="page-link"
                               href="lista?page=<c:out value="${pageParams.page + 1}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>&mostraTutti=<c:out value="${showAll}"/>"
                               <c:if test="${pageParams.page == 1}">tabindex="-1"</c:if> >Successiva</a>
                        </li>
                    </ul>

                </nav>
            </div>
            <div class="col-lg-4 mb-1 mb-lg-0">
                <div class="div-centered">
                    <label class="label" for="pageSizeDropdown">Elementi: </label>
                    <div class="btn-group">
                        <button id="pageSizeDropdown" class="btn btn-secondary btn-sm dropdown-toggle" type="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${pageParams.pageSize}
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item"
                               href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=10&mostraTutti=<c:out value="${showAll}"/>">10</a>
                            <a class="dropdown-item"
                               href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=15&mostraTutti=<c:out value="${showAll}"/>">15</a>
                            <a class="dropdown-item"
                               href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=30&mostraTutti=<c:out value="${showAll}"/>">30</a>
                        </div>
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
                <th scope="col">Foto</th>
                <th scope="col">Nome e cognome</th>
                <th scope="col">Sesso</th>
                <th scope="col">Data di nascita</th>
                <th scope="col">Luogo di nascita</th>
                <th scope="col">Provincia</th>
                <th scope="col">Codice fiscale</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="listaPazienti" scope="request"
                         type="java.util.List<com.icecoldbier.persistence.entities.Paziente>"/>
            <c:forEach var="p" items="${listaPazienti}">
                <tr data-href="/medico-base/scheda-paziente?id=<c:out value="${p.id}"/>">
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
                    <td>
                        <c:choose>
                            <c:when test="${not empty p.foto}">
                                <img class="profile-picture" src="<c:out value="${p.foto}"/>" height="48" width="48">
                            </c:when>
                            <c:otherwise>
                                <img src="/images/profile_placeholder.svg" height="48" width="48">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${p.nome} ${p.cognome}"/></td>
                    <td><c:out value="${p.sesso}"/></td>
                    <td><c:out value="${p.dataNascita}"/></td>
                    <td><c:out value="${p.luogoNascita}"/></td>
                    <td><c:out value="${p.provinciaAppartenenza}"/></td>
                    <td><c:out value="${p.codiceFiscale}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="../js/clickable_row.js"></script>

<script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.0/dist/latest/bootstrap-autocomplete.min.js"></script>
<script src="../js/ricerca_pazienti.js"></script>
</body>
</html>
