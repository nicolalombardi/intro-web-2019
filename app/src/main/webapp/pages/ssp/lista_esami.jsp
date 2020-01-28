<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="listaVisite" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaSSP>"/>


<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SSP - Lista Esami SSP</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.css"/>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Lista Esami SSP</h1>

        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th class="all" scope="col">Foto</th>
                <th class="all default-sort" scope="col">Paziente</th>
                <th class="min-md" scope="col">Data prescrizione</th>
                <th class="min-lg" scope="col">Descrizione</th>
                <th class="min-md" scope="col">Stato</th>
                <th class="min-sm" scope="col">Dettagli</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${listaVisite}">
                <c:set var="targetModal" value="#modaleVisitaSSP-${v.id}"/>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty v.paziente.fotoThumb}">
                                <img class="profile-picture-thumbnail" src="<c:out value="${v.paziente.foto}"/>" height="48" width="48">
                            </c:when>
                            <c:otherwise>
                                <img src="/images/profile_placeholder.svg" height="48" width="48">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${v.paziente.nome} ${v.paziente.cognome}"/></td>
                    <td><c:out value="${v.dataPrescrizione}"/></td>
                    <td><c:out value="${v.tipo_visita.nome}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${empty v.dataErogazione}">
                                Non erogato
                            </c:when>
                            <c:otherwise>
                                Erogato
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a class="btn btn-primary mb-3 icon-white" data-toggle="modal" data-target="<c:out value="${targetModal}"/>" href="#"
                           role="button"><i class="material-icons">open_in_new</i></a></td>

                </tr>
            </c:forEach>

            </tbody>
        </table>
</div>

<c:forEach var="v" items="${listaVisite}">
    <c:set var="modalId" value="modaleVisitaSSP-${v.id}"/>
    <div class="modal fade" id="<c:out value="${modalId}"/>" tabindex="-1" role="dialog" aria-hidden="true">
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
                            <th>SSP</th>
                            <td>SSP di <c:out value="${v.ssp.provinciaAppartenenza}"/></td>
                        </tr>
                        <tr>
                            <th>Paziente</th>
                            <td><c:out value="${v.paziente.nome}"/> <c:out value="${v.paziente.cognome}"/></td>
                        </tr>
                        <tr>
                            <th>Nome esame</th>
                            <td><c:out value="${v.tipo_visita.nome}"/></td>
                        </tr>
                        <tr>
                            <th>Descrizione o note</th>
                            <td><c:out value="${v.tipo_visita.descrizione}"/></td>
                        </tr>
                        <tr>
                            <th>Costo ticket</th>
                            <td><c:out value="${v.tipo_visita.costo_ticket}"/> euro</td>
                        </tr>
                        <tr>
                            <th>Prescritta da</th>
                            <td><c:out value="${v.medicoBase.nome}"/> <c:out value="${v.medicoBase.cognome}"/></td>
                        </tr>
                        <tr>
                            <th>Data prescrizione</th>
                            <td><c:out value="${v.dataPrescrizione}"/></td>
                        </tr>
                        <tr>
                            <th>Data erogazione</th>
                            <td>
                                <c:choose>
                                    <c:when test="${empty v.dataErogazione}">
                                        Non ancora erogato
                                        <form action="eroga" method="post">
                                            <input type="hidden" name="idVisitaSSP" value="<c:out value="${v.id}"/>">
                                            <button type="submit" class="btn btn-primary">Eroga</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                       <c:out value="${v.dataErogazione}"/>
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
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.js"></script>

<script src="../js/init_datatables.js"></script>
<script src="../js/toggle_modal_hash.js"></script>

<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>

</body>
</html>
