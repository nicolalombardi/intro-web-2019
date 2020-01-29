<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Elenco Prescrizioni Ricette</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>
<jsp:useBean id="elencoRicette" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.InfoRicetta>"/>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Elenco ricette prescritte</h1>

        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th class="all default-sort" scope="col">Farmaco prescritto</th>
                <th class="all" scope="col">Data prescrizione</th>
                <th class="all" scope="col">Approvato da</th>
                <th class="min-md" scope="col" width="16%">Azione</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="r" items="${elencoRicette}">
                <tr>
                    <td><c:out value="${r.farmaco}"/></td>
                    <td><c:out value="${r.prettyDataPrescrizione}"/></td>
                    <c:choose>
                        <c:when test="${r.acquistabile}">
                            <td><mp:miniProfileTag user="${r.medicoBase}"/></td>
                            <form action="stampa-ricetta" method="POST">
                                <input type="hidden" name="idRicetta" value=<c:out value="${r.id}"/>>
                                <td><button class="btn btn-sm btn-primary" type="submit">Stampa ricetta</button></td>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <td>Non ancora approvato</td>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>

<c:forEach var="r" items="${elencoRicette}">
    <div class="modal fade" id="modaleRicetta<c:out value="${r.id}"/>" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Dettagli ricetta</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th>Farmaco prescritto</th>
                            <td><c:out value="${r.farmaco}"/></td>
                        </tr>
                        <tr>
                            <th>Data prescrizione</th>
                            <td><c:out value="${r.prettyDataPrescrizione}"/></td>
                        </tr>
                        <tr>
                            <th>Stato approvazione</th>
                            <c:choose>
                                <c:when test="${r.acquistabile}">
                                    <td>Approvato da <mp:miniProfileTag user="${r.medicoBase}"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td>Non ancora approvato</td>
                                </c:otherwise>
                            </c:choose>
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
<script src="../js/init_non_datatable_popover.js"></script>
<script src="../js/toggle_modal_hash.js"></script>

<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>

</body>
</html>