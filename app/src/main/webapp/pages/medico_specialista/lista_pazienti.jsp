<%@ page import="com.icecoldbier.persistence.entities.Paziente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="pageParams" scope="request" type="com.icecoldbier.utils.pagination.PaginationParameters"/>

<html>
    <head>
        <title>Medico Specialista - Lista Pazienti</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
    </head>
    <body>
    <%@ include file="navbar.html" %>
    
    <div class="container">
        
        
        <div class="container">
            <div class="row">
                <div class="col-sm">
                </div>
                <div class="col-sm">
                    <nav aria-label="Navigazione lista pazienti">
                        <span class="btn-group"></span>
                        <ul class="pagination justify-content-center">
                            <li class="page-item <c:if test="${pageParams.page == 1}">disabled</c:if>">
                                <a class="page-link" href="lista?page=<c:out value="${pageParams.page - 1}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>&"
                                   <c:if test="${pageParams.page == 1}">tabindex="-1"</c:if> >Precedente</a>
                            </li>

                            <c:forEach var="i" begin="1" end="${pageParams.pagesCount}">
                                <li class="page-item <c:if test="${pageParams.page == i}">active</c:if> "><a class="page-link"
                                                                                                             href="lista?page=<c:out value="${i}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>"><c:out value="${i}"/></a>
                                </li>
                            </c:forEach>

                            <li class="page-item <c:if test="${pageParams.page == pageParams.pagesCount || pageParams.pagesCount == 0}">disabled</c:if>">
                                <a class="page-link" href="lista?page=<c:out value="${pageParams.page + 1}"/>&pageSize=<c:out value="${pageParams.pageSize}"/>"
                                   <c:if test="${pageParams.page == 1}">tabindex="-1"</c:if> >Successiva</a>
                            </li>
                        </ul>

                    </nav>
                </div>
                <div class="col-sm">
                    <div class="btn-group">
                        <label class="label" for="pageSizeDropdown">Elementi: </label>
                        <button id="pageSizeDropdown" class="btn btn-secondary btn-sm dropdown-toggle" type="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <c:out value="${pageParams.pageSize}"/>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=10">10</a>
                            <a class="dropdown-item" href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=15">15</a>
                            <a class="dropdown-item" href="lista?page=<c:out value="${pageParams.page}"/>&pageSize=30">30</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        
        
        
        
        <h1>Lista pazienti</h1>
        
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
                <c:forEach var="p" items="${listaPazientiSpecialista}">
                        <tr data-href="/medico-specialista/scheda-paziente?id=<c:out value="${p.getId()}"/>">
                            <th><c:out value="${p.getNome()}"/></th>
                            <th><c:out value="${p.getCognome()}"/></th>
                            <th><c:out value="${p.getSesso()}"/></th>
                            <th><c:out value="${p.getDataNascita()}"/></th>
                            <th><c:out value="${p.getLuogoNascita()}"/></th>
                            <th><c:out value="${p.getCodiceFiscale()}"/></th>
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
