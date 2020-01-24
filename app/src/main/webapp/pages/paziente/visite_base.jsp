<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 12/26/19
  Time: 10:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="elencoVisite" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaBase>"/>
<html>
<head>
    <title>Elenco Visite Base</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>


<div class="container">
    <h1>Elenco visite base</h1>

    <div class="table-responsive-md">
        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Medico base</th>
                <th scope="col">Data erogazione</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${elencoVisite}">
                <tr>
                    <td scope="row"><c:out value="${v.medicoBase.toStringNomeCognome()}"/></td>
                    <td><c:out value="${v.dataErogazione}"/></td>
                    <c:choose>
                        <c:when test="${empty v.ricetta.id}">
                            <td></td>
                        </c:when>
                        <c:otherwise>
                            <td><button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modaleRicetta<c:out value="${v.ricetta.id}"/>">Ricetta</button></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<c:forEach var="v" items="${elencoVisite}">
    <div class="modal fade" id="modaleRicetta<c:out value="${v.ricetta.id}"/>" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Ricetta</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th>Medico</th>
                            <td><c:out value="${v.medicoBase.nome} ${v.medicoBase.cognome}"/></td>
                        </tr>
                        <tr>
                            <th>Nome ricetta</th>
                            <td><c:out value="${v.ricetta.nome}"/></td>
                        </tr>
                        <tr>
                            <th>Erogata</th>
                            <td>
                                <c:choose>
                                    <c:when test="${v.ricetta.prescritta}">
                                        SI
                                    </c:when>
                                    <c:otherwise>
                                        NO
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
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
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>

<script src="../js/init_datatables.js"></script>
<script src="../js/clickable_row.js"></script>
</body>
</html>