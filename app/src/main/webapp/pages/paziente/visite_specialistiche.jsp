<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 12/26/19
  Time: 5:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="elencoVisite" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaSpecialistica>"/>
<html>
<head>
    <title>Elenco Visite Specialistiche</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>


<div class="container">
    <h1>Elenco visite specialistiche</h1>

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a class="page-link" href="/paziente/elenco-visite-specialistiche?page=<c:out value="${page - 1}"/>" <c:if test="${page == 1}">tabindex="-1"</c:if> >Previous</a>
            </li>

            <c:forEach var = "i" begin = "1" end = "${pagesCount}">
                <li class="page-item <c:if test="${page == i}">active</c:if> "><a class="page-link" href="paziente/elenco-visite-specialistiche?page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
            </c:forEach>

            <li class="page-item <c:if test="${page == pagesCount}">disabled</c:if>">
                <a class="page-link" href="/paziente/elenco-visite-specialistiche?page=<c:out value="${page + 1}"/>" <c:if test="${page == 1}">tabindex="-1"</c:if> >Next</a>
            </li>
        </ul>
    </nav>

    <div class="table-responsive-md">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tipo Visita</th>
                <th scope="col">Erogata</th>
                <th scope="col">Data Prescrizione</th>
                <th scope="col">Data Erogazione</th>
                <th scope="col">Medico specialista</th>
                <th scope="col">Report</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${elencoVisite}">
                <tr>
                    <th scope="row"><c:out value="${v.tipo_visita.nome}"/></th>
                    <th>
                        <c:choose>
                            <c:when test="${v.erogata}">SI</c:when>
                            <c:otherwise>NO</c:otherwise>
                        </c:choose>
                    </th>

                    <th><c:out value="${v.dataPrescrizione}"/></th>
                    <th><c:out value="${v.dataErogazione}"/></th>
                    <th><c:out value="${v.medicoSpecialista.toStringNomeCognome()}"/></th>
                    <c:choose>
                        <c:when test="${empty v.report.id}">
                            <th></th>
                        </c:when>
                        <c:otherwise>
                            <th><button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modaleReport<c:out value="${v.report.id}"/>">Report</button></th>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<c:forEach var="v" items="${elencoVisite}">
    <div class="modal fade" id="modaleReport<c:out value="${v.report.id}"/>" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Report</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th>Esito report</th>
                            <td><c:out value="${v.report.esito}"/></td>
                        </tr>
                        <tr>
                            <th>Ricetta</th>
                            <c:choose>
                                <c:when test="${empty v.report.ricetta.id}">
                                    <td>Nessuna ricetta associata a questo report</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${v.report.ricetta.nome}"/></td></tr>
                                    <tr>
                                        <th>Erogata</th>
                                        <c:choose>
                                            <c:when test="${v.report.ricetta.prescritta}">
                                                <td>SI</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>NO</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        <!-- tr già presente nel c:choose -->
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

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