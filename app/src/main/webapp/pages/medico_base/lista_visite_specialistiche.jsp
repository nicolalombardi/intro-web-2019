<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="listaVisite" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaSpecialisticaOrSSP>"/>


<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Medico Base - Lista Visite Specialistiche ed Esami SSP</title>
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
    <c:choose>
        <c:when test="${empty paziente}">
            <h1>Lista Visite Specialistiche ed Esami SSP</h1>
        </c:when>
        <c:otherwise>
            <h1>Lista Visite Specialistiche ed Esami SSP di <mp:miniProfileTag paziente="${paziente}"/></h1>
        </c:otherwise>
    </c:choose>

        <table class="datatable table table-striped">
            <thead class="thead-dark">
            <tr>
                <th class="all" scope="col">Foto</th>
                <th class="all" scope="col">Paziente</th>
                <th class="min-sm  default-sort" scope="col">Data prescrizione</th>
                <th class="min-sm" scope="col">Tipo</th>
                <th class="min-md" scope="col">Descrizione</th>
                <th class="min-md" scope="col">Stato</th>
                <th class="all" scope="col">Dettagli</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${listaVisite}">
                <c:choose>
                    <c:when test="${v.SSP}">
                        <c:set var="foto" value="${v.visitaSSP.paziente.fotoThumb}"/>
                        <c:set var="paziente" value="${v.visitaSSP.paziente}"/>
                        <c:set var="dataPrescrizione" value="${v.visitaSSP.prettyDataPrescrizione}"/>
                        <c:set var="tipo" value="Esame SSP"/>
                        <c:set var="descrizione" value="${v.visitaSSP.tipo_visita.nome}"/>
                        <c:set var="erogata" value="${not empty v.visitaSSP.dataErogazione}"/>
                        <c:set var="tuo" value="${user.id == v.visitaSSP.paziente.medico.id}"/>
                        <c:set var="targetModal" value="#modaleVisitaSSP-${v.visitaSSP.id}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="foto" value="${v.visitaSpecialistica.paziente.fotoThumb}"/>
                        <c:set var="paziente" value="${v.visitaSpecialistica.paziente}"/>
                        <c:set var="dataPrescrizione" value="${v.visitaSpecialistica.prettyDataPrescrizione}"/>
                        <c:set var="tipo" value="Visita specialistica"/>
                        <c:set var="descrizione" value="${v.visitaSpecialistica.tipo_visita.nome}"/>
                        <c:set var="erogata" value="${not empty v.visitaSpecialistica.dataErogazione}"/>
                        <c:set var="tuo" value="${user.id == v.visitaSpecialistica.paziente.medico.id}"/>
                        <c:set var="targetModal" value="#modaleVisitaSpecialistica-${v.visitaSpecialistica.id}"/>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td>
                        <img class="profile-picture-thumbnail" src="<c:out value="${foto}"/>" height="48" width="48">
                    </td>
                    <td>
                        <mp:miniProfileTag paziente="${paziente}"/>
                        <c:if test="${tuo}">
                            <span class="badge badge-success"><i class="material-icons badge-icon">check</i></span>
                        </c:if>
                    </td>
                    <td><c:out value="${dataPrescrizione}"/></td>
                    <td><c:out value="${tipo}"/></td>
                    <td><c:out value="${descrizione}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${erogata}">
                                <c:out value="Erogata"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="Non ancora erogata"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a class="btn btn-primary mb-3" href="#"  data-toggle="modal" data-target="<c:out value="${targetModal}"/>"
                           role="button"><i class="material-icons">open_in_new</i></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>

<c:forEach var="v" items="${listaVisite}">
    <c:choose>
        <c:when test="${v.SSP}">
            <c:set var="modalId" value="modaleVisitaSSP-${v.visitaSSP.id}"/>
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
                                    <td><mp:miniProfileTag ssp="${v.visitaSSP.ssp}"/></td>
                                </tr>
                                <tr>
                                    <th>Paziente</th>
                                    <td><mp:miniProfileTag paziente="${v.visitaSSP.paziente}"/></td>
                                </tr>
                                <tr>
                                    <th>Nome esame</th>
                                    <td><c:out value="${v.visitaSSP.tipo_visita.nome}"/></td>
                                </tr>
                                <tr>
                                    <th>Descrizione o note</th>
                                    <td><c:out value="${v.visitaSSP.tipo_visita.descrizione}"/></td>
                                </tr>
                                <tr>
                                    <th>Costo ticket</th>
                                    <td><c:out value="${v.visitaSSP.tipo_visita.costo_ticket}"/>€</td>
                                </tr>
                                <tr>
                                    <th>Prescritta da</th>
                                    <td><mp:miniProfileTag user="${v.visitaSSP.medicoBase}"/></td>
                                </tr>
                                <tr>
                                    <th>Data prescrizione</th>
                                    <td><c:out value="${v.visitaSSP.prettyDataPrescrizione}"/></td>
                                </tr>
                                <tr>
                                    <th>Data erogazione</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty v.visitaSSP.dataErogazione}">
                                                <c:out value="Non ancora erogato"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${v.visitaSSP.prettyDataErogazione}"/>
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
        </c:when>
        <c:otherwise>
            <c:set var="modalId" value="modaleVisitaSpecialistica-${v.visitaSpecialistica.id}"/>
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
                                    <th>Medico Specialista</th>
                                    <td><mp:miniProfileTag user="${v.visitaSpecialistica.medicoSpecialista}"/></td>
                                </tr>
                                <tr>
                                    <th>Paziente</th>
                                    <td><mp:miniProfileTag paziente="${v.visitaSpecialistica.paziente}"/></td>
                                </tr>
                                <tr>
                                    <th>Nome visita</th>
                                    <td><c:out value="${v.visitaSpecialistica.tipo_visita.nome}"/></td>
                                </tr>
                                <tr>
                                    <th>Descrizione o note</th>
                                    <td><c:out value="${v.visitaSpecialistica.tipo_visita.descrizione}"/></td>
                                </tr>
                                <tr>
                                    <th>Costo ticket</th>
                                    <td><c:out value="${v.visitaSpecialistica.tipo_visita.costo_ticket}"/>€</td>
                                </tr>
                                <tr>
                                    <th>Prescritta da</th>
                                    <td><mp:miniProfileTag user="${v.visitaSpecialistica.medicoBase}"/></td>
                                </tr>
                                <tr>
                                    <th>Data prescrizione</th>
                                    <td><c:out value="${v.visitaSpecialistica.prettyDataPrescrizione}"/></td>
                                </tr>
                                <c:choose>
                                    <c:when test="${empty v.visitaSpecialistica.dataErogazione}">
                                        <tr>
                                            <th>Data erogazione</th>
                                            <td>Non ancora erogata</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <th>Data erogazione</th>
                                            <td><c:out value="${v.visitaSpecialistica.prettyDataErogazione}"/></td>
                                        </tr>
                                        <tr>
                                            <th>Report</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empty v.visitaSpecialistica.report}">
                                                        Nessun report allegato
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${v.visitaSpecialistica.report.esito}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Ricetta</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empty v.visitaSpecialistica.report.ricetta}">
                                                        Nessuna ricetta allegata al report
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${v.visitaSpecialistica.report.ricetta.nome}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <c:if test="${not empty v.visitaSpecialistica.report.ricetta}">
                                            <tr>
                                                <th>Stato Ricetta</th>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${v.visitaSpecialistica.report.ricetta.prescritta}">
                                                            Ricetta già approvata
                                                        </c:when>
                                                        <c:otherwise>
                                                            Ricetta non approvata
                                                            <form action="approva" method="post" class="approva">
                                                                <input type="hidden" name="id_ricetta" value="<c:out value="${v.visitaSpecialistica.report.ricetta.id}"/>">
                                                                <button type="submit" class="btn btn-primary">Approva</button>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>

                                    </c:otherwise>
                                </c:choose>

                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</c:forEach>


<script>
    var specialista = false;
</script>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.0/dist/latest/bootstrap-autocomplete.min.js"></script>

<script src="../js/init_datatables.js"></script>
<script src="../js/init_non_datatable_popover.js"></script>
<script src="../js/toggle_modal_hash.js"></script>
<script src="../js/ricerca_pazienti.js"></script>

<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>

</body>
</html>
