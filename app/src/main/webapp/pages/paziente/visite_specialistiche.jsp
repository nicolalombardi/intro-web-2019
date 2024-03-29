<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
<jsp:useBean id="elencoVisite" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaSpecialistica>"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Elenco Visite Specialistiche</title>
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
    <h1>Elenco visite specialistiche</h1>

        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th class="all" scope="col">Tipo Visita</th>
                <th class="min-lg" scope="col">Erogata</th>
                <th class="min-sm default-sort" scope="col">Data Prescrizione</th>
                <th class="min-md" scope="col">Medico erogante</th>
                <th class="min-lg" scope="col">Report</th>
                <th class="min-lg" scope="col">Dettagli</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${elencoVisite}">
                <tr>
                    <td><c:out value="${v.tipo_visita.nome}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${v.erogata}">Sì</c:when>
                            <c:otherwise>No</c:otherwise>
                        </c:choose>
                    </td>

                    <td><c:out value="${v.prettyDataPrescrizione}"/></td>
                    <td><mp:miniProfileTag user="${v.medicoSpecialista}"/></td>
                    <c:choose>
                        <c:when test="${empty v.report.id}">
                            <td>assente</td>
                        </c:when>
                        <c:otherwise>
                            <td><button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modaleReport<c:out value="${v.report.id}"/>">Report</button></td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <a class="btn btn-primary mb-3" href="#"  data-toggle="modal" data-target="#modaleVisitaSpecialistica${v.id}" role="button"><i class="material-icons">open_in_new</i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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

<c:forEach var="v" items="${elencoVisite}">
    <div class="modal fade" id="modaleVisitaSpecialistica<c:out value="${v.id}"/>" tabindex="-1" role="dialog" aria-hidden="true">
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
                            <th>Tipo visita</th>
                            <td><c:out value="${v.tipo_visita.nome}"/></td>
                        </tr>
                        <tr>
                            <th>Descrizione visita</th>
                            <td><c:out value="${v.tipo_visita.descrizione}"/></td>
                        </tr>
                        <tr>
                            <th>Data prescrizione</th>
                            <td><c:out value="${v.prettyDataPrescrizione}"/></td>
                        </tr>
                        <c:choose>
                            <c:when test="${v.erogata == true}">
                                <tr>
                                    <th>Erogata</th>
                                    <td>Sì</td>
                                </tr>
                                <tr>
                                    <th>Data erogazione</th>
                                    <td><c:out value="${v.prettyDataErogazione}"/></td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <th>Erogata</th>
                                    <td>No</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        <tr>
                            <th>Prescritta da</th>
                            <td><mp:miniProfileTag user="${v.medicoBase}"/> </td>
                        </tr>
                        <tr>
                            <th>Medico erogante</th>
                            <td><mp:miniProfileTag user="${v.medicoSpecialista}"/></td>
                        </tr>
                        <tr>
                            <th>Costo ticket</th>
                            <td>50.0€</td>
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
<script src="../../js/init_non_datatable_popover.js"></script>
</body>
</html>