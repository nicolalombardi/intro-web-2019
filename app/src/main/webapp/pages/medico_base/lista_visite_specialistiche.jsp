<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="listaVisite" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaSpecialisticaOrSSP>"/>


<html>
<head>
    <title>Medico Base - Lista Visite Specialistiche ed Esami SSP</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <c:choose>
        <c:when test="${empty paziente}">
            <h1>Lista Visite Specialistiche ed Esami SSP</h1>
        </c:when>
        <c:otherwise>
            <h1>Lista Visite Specialistiche ed Esami SSP di <c:out value="${paziente.nome}"/> <c:out value="${paziente.cognome}"/></h1>
        </c:otherwise>
    </c:choose>

    <div class="table-responsive-md">
        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tuo</th>
                <th scope="col">Foto</th>
                <th scope="col">Paziente</th>
                <th scope="col">Data prescrizione</th>
                <th scope="col">Tipo</th>
                <th scope="col">Descrizione</th>
                <th scope="col">Stato</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${listaVisite.size() == 0}">
                    <tr style="text-align: center">
                        <td colspan="6">Nessuna visita presente</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="v" items="${listaVisite}">
                        <c:choose>
                            <c:when test="${v.SSP}">
                                <c:set var="foto" value="${v.visitaSSP.paziente.fotoThumb}"/>
                                <c:set var="nome_e_cognome" value="${v.visitaSSP.paziente.nome} ${v.visitaSSP.paziente.cognome}"/>
                                <c:set var="dataPrescrizione" value="${v.visitaSSP.dataPrescrizione}"/>
                                <c:set var="tipo" value="Esame SSP"/>
                                <c:set var="descrizione" value="${v.visitaSSP.tipo_visita.nome}"/>
                                <c:set var="erogata" value="${not empty v.visitaSSP.dataErogazione}"/>
                                <c:set var="tuo" value="${user.id == v.visitaSSP.paziente.medico.id}"/>
                                <c:set var="targetModal" value="#modaleVisitaSSP-${v.visitaSSP.id}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="foto" value="${v.visitaSpecialistica.paziente.fotoThumb}"/>
                                <c:set var="nome_e_cognome" value="${v.visitaSpecialistica.paziente.nome} ${v.visitaSpecialistica.paziente.cognome}"/>
                                <c:set var="dataPrescrizione" value="${v.visitaSpecialistica.dataPrescrizione}"/>
                                <c:set var="tipo" value="Visita specialistica"/>
                                <c:set var="descrizione" value="${v.visitaSpecialistica.tipo_visita.nome}"/>
                                <c:set var="erogata" value="${not empty v.visitaSpecialistica.dataErogazione}"/>
                                <c:set var="tuo" value="${user.id == v.visitaSpecialistica.paziente.medico.id}"/>
                                <c:set var="targetModal" value="#modaleVisitaSpecialistica-${v.visitaSpecialistica.id}"/>
                            </c:otherwise>
                        </c:choose>
                        <tr data-toggle="modal" data-target="<c:out value="${targetModal}"/>">
                            <td>
                                <c:if test="${tuo}">
                                    <i class="material-icons info-icon">check</i>
                                </c:if>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty foto}">
                                        <img class="profile-picture" src="<c:out value="${foto}"/>" height="48" width="48">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/images/profile_placeholder.svg" height="48" width="48">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${nome_e_cognome}"/></td>
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
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>
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
                                    <td>SSP di <c:out value="${v.visitaSSP.ssp.provinciaAppartenenza}"/></td>
                                </tr>
                                <tr>
                                    <th>Paziente</th>
                                    <td><c:out value="${v.visitaSSP.paziente.nome}"/> <c:out value="${v.visitaSSP.paziente.cognome}"/></td>
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
                                    <td><c:out value="${v.visitaSSP.tipo_visita.costo_ticket}"/> euro</td>
                                </tr>
                                <tr>
                                    <th>Prescritta da</th>
                                    <td><c:out value="${v.visitaSSP.medicoBase.nome}"/> <c:out value="${v.visitaSSP.medicoBase.cognome}"/></td>
                                </tr>
                                <tr>
                                    <th>Data prescrizione</th>
                                    <td><c:out value="${v.visitaSSP.dataPrescrizione}"/></td>
                                </tr>
                                <tr>
                                    <th>Data erogazione</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty v.visitaSSP.dataErogazione}">
                                                <c:out value="Non ancora erogato"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${v.visitaSSP.dataErogazione}"/>
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
                                    <td><c:out value="${v.visitaSpecialistica.medicoSpecialista.nome}"/> <c:out value="${v.visitaSpecialistica.medicoSpecialista.cognome}"/></td>
                                </tr>
                                <tr>
                                    <th>Paziente</th>
                                    <td><c:out value="${v.visitaSpecialistica.paziente.nome}"/> <c:out value="${v.visitaSpecialistica.paziente.cognome}"/></td>
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
                                    <td><c:out value="${v.visitaSpecialistica.tipo_visita.costo_ticket}"/> euro</td>
                                </tr>
                                <tr>
                                    <th>Prescritta da</th>
                                    <td><c:out value="${v.visitaSpecialistica.medicoBase.nome}"/> <c:out value="${v.visitaSpecialistica.medicoBase.cognome}"/></td>
                                </tr>
                                <tr>
                                    <th>Data prescrizione</th>
                                    <td><c:out value="${v.visitaSpecialistica.dataPrescrizione}"/></td>
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
                                            <td><c:out value="${v.visitaSpecialistica.dataErogazione}"/></td>
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
<script src="../js/toggle_modal_hash.js"></script>
<script src="../js/ricerca_pazienti.js"></script>

<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>

</body>
</html>
