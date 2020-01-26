<jsp:useBean id="visita" scope="request" type="com.icecoldbier.persistence.entities.VisitaSpecialistica"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Dettagli visita</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Visita di</h1>
    <div class="container">
        <div class="row">
            <%--            Dati paziente--%>
            <div class="col">
                <div class="row">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th><b>Tipo visita</b></th>
                            <td><c:out value="${visita.tipo_visita.nome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Descrizione visita</b></th>
                            <td><c:out value="${visita.tipo_visita.nome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Nome</b></th>
                            <td><c:out value="${visita.paziente.nome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Cognome</b></th>
                            <td><c:out value="${visita.paziente.cognome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Codice Fiscale</b></th>
                            <td><c:out value="${visita.paziente.codiceFiscale}"/></td>
                        </tr>
                        <tr>
                            <th><b>Data di prescrizione</b></th>
                            <td><c:out value="${visita.dataPrescrizione}"/></td>
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
                            <td><c:out value="${visita.paziente.medico.nome} ${visita.paziente.medico.cognome}"/></td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <c:if test="${not visita.erogata}">
                    <div class="col funzioni-col">
                        <h2>Funzioni</h2>

                        <div class="card-deck">
                            <div class="card border-dark mb-3" style="max-width: 100rem;">
                                <div class="card-header">Eroga visita specialistica</div>
                                <div class="card-body text-dark">
                                    <p class="card-text">Eroga una visita specialistica con la possibilità di associare
                                        una ricetta.</p>
                                    <button type="button" class="btn btn-primary stretched-link" data-toggle="modal"
                                            data-target="#modaleErogaVisitaSpecialistica">
                                        Apri
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
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

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>
</body>
</html>
