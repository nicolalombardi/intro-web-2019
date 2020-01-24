<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="listaVisite" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaBase>"/>


<html>
<head>
    <title>Medico Base - Lista Visite Base</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <c:choose>
        <c:when test="${empty paziente}">
            <h1>Lista visite base</h1>
        </c:when>
        <c:otherwise>
            <h1>Lista visite base di <c:out value="${paziente.nome} ${paziente.cognome}"/></h1>
        </c:otherwise>
    </c:choose>

    <div class="table-responsive-md">
        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tuo</th>
                <th scope="col">Foto</th>
                <th scope="col">Paziente</th>
                <th scope="col">Data erogazione</th>
                <th scope="col">Ricetta</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${listaVisite.size() == 0}">
                    <tr style="text-align: center">
                        <td colspan="5">Nessuna visita presente</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="v" items="${listaVisite}">
                        <tr data-toggle="modal" data-target="#modaleVisita<c:out value="${v.id}"/>">
                            <td>
                                <c:if test="${user.id == v.paziente.medico.id}">
                                    <i class="material-icons info-icon">check</i>
                                </c:if>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty v.paziente.fotoThumb}">
                                        <img class="profile-picture" src="<c:out value="${v.paziente.fotoThumb}"/>" height="48" width="48">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/images/profile_placeholder.svg" height="48" width="48">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${v.paziente.nome} ${v.paziente.cognome}"/></td>
                            <td><c:out value="${v.dataErogazione}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty v.ricetta}">
                                        Nessuna ricetta
                                    </c:when>
                                    <c:otherwise>
                                        <ct:ellipsizeTag maxLength="30"><c:out value="${v.ricetta.nome}"/></ct:ellipsizeTag>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>
</div>

<c:forEach var="v" items="${listaVisite}">
    <div class="modal fade" id="modaleVisita<c:out value="${v.id}"/>" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Dettagli visita</h5>
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
                            <th>Paziente</th>
                            <td><c:out value="${v.paziente.nome} ${v.paziente.cognome}"/></td>
                        </tr>
                        <tr>
                            <th>Data erogazione</th>
                            <td><c:out value="${v.dataErogazione}"/></td>
                        </tr>
                        <tr>
                            <th>Ricetta</th>
                            <td>
                                <c:choose>
                                    <c:when test="${empty v.ricetta}">
                                        Nessuna ricetta
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${v.ricetta.nome}"/>
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


<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.0/dist/latest/bootstrap-autocomplete.min.js"></script>

<script src="../js/clickable_row.js"></script>
<script src="../js/init_datatables.js"></script>
<script src="../js/ricerca_pazienti.js"></script>
</body>
</html>
