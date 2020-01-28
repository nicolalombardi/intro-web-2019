<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
<jsp:useBean id="visita" scope="request" type="com.icecoldbier.persistence.entities.VisitaSpecialistica"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Dettagli visita</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Visita di</h1>
    <div class="row">
        <div class="col-12 col-lg-6">
            <table class="table profile-table">
                <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <c:choose>
                                <c:when test="${not empty paziente.foto}">
                                    <img class="profile-picture" src="<c:out value="${paziente.foto}"/>">
                                </c:when>
                                <c:otherwise>
                                    <img class="profile-picture" src="/images/profile_placeholder.svg">
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>

                    <tr>
                        <th><b>Tipo visita</b></th>
                        <td><c:out value="${visita.tipo_visita.nome}"/></td>
                    </tr>
                    <tr>
                        <th><b>Descrizione visita</b></th>
                        <td><c:out value="${visita.tipo_visita.nome}"/></td>
                    </tr>
                    <tr>
                        <th><b>Paziente</b></th>
                        <td> <mp:miniProfileTag paziente="${visita.paziente}"/></td>
                    </tr>
                    <tr>
                        <th><b>Codice Fiscale</b></th>
                        <td><c:out value="${visita.paziente.codiceFiscale}"/></td>
                    </tr>
                    <tr>
                        <th><b>Data di prescrizione</b></th>
                        <td><c:out value="${visita.prettyDataPrescrizione}"/></td>
                    </tr>
                    <tr>
                        <th><b>Erogata</b></th>
                        <td>
                            <c:choose>
                                <c:when test="${visita.erogata}">Si</c:when>
                                <c:otherwise>No</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <th><b>Medico di base</b></th>
                        <td> <mp:miniProfileTag user="${visita.medicoBase}"/> </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-12 col-lg-6  funzioni-col">
            <h2>Funzioni</h2>
            <div class="card-deck">
                <div class="card border-dark mb-3" style="max-width: 100rem;">
                    <div class="card-header">Eroga visita specialistica</div>
                    <div class="card-body text-dark">
                        <p class="card-text">Eroga una visita specialistica con la possibilità di associare
                            una ricetta.</p>
                        <button type="button" class="btn btn-primary stretched-link" data-toggle="modal"
                                data-target="#modaleErogaVisitaSpecialistica" <c:if test="${visita.erogata}">disabled</c:if> >
                            Apri
                        </button>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</div>

<div class="modal fade" id="modaleErogaVisitaSpecialistica" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="eroga">
                <input type="hidden" name="idPaziente" value="<c:out value="${visita.paziente.id}"/>">
                <input type="hidden" name="idMedicoSpecialista" value="<c:out value="${visita.medicoSpecialista.id}"/>">
                <input type="hidden" name="idMedicoBase" value="<c:out value="${visita.medicoBase.id}"/>">
                <input type="hidden" name="idVisita" value="<c:out value="${visita.id}"/>">
                <input type="hidden" name="usernamePaziente" value="<c:out value="${visita.paziente.username}"/>">
                <div class="modal-header">
                    <h5 class="modal-title">Eroga una visita specialistica</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="textRicetta">Ricetta</label>
                        <textarea class="form-control" name="textRicetta" id="textRicetta" rows="3"
                                  placeholder="Inserisci qua l'oggetto della ricetta. Lasciando questo campo vuoto non verrà inserita alcuna ricetta"></textarea>

                    </div>
                    <div class="form-group">
                        <label for="textReport">Report</label>
                        <textarea class="form-control" name="textReport" id="textReport" rows="3"
                                  placeholder="Inserisci qua l'esito del report. Si prega di compilarlo"
                                  required></textarea>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary">Eroga</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    var specialista = true;
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

<script src="../../js/init_datatables.js"></script>
<script src="../../js/ricerca_pazienti.js"></script>

<script src="../../js/init_non_datatable_popover.js"></script>


<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>
</body>
</html>
