<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profilo</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Profilo</h1>
    <div class="container">
        <div class="row">
            <%--            Dati paziente--%>
            <div class="col">
                <div class="row">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <c:choose>
                                    <c:when test="${not empty paziente.foto}">
                                        <img src="<c:out value="${paziente.foto}"/>" height="300px" width="300px">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/images/profile_placeholder.svg" height="300px" width="300px">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th><b>Nome</b></th>
                            <td><c:out value="${paziente.nome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Cognome</b></th>
                            <td><c:out value="${paziente.cognome}"/></td>
                        </tr>
                        <tr>
                            <th><b>Email</b></th>
                            <td><c:out value="${paziente.username}"/></td>
                        </tr>
                        <tr>
                            <th><b>Provincia di appartenenza</b></th>
                            <td><c:out value="${paziente.provinciaAppartenenza}"/></td>
                        </tr>
                        <tr>
                            <th><b>Data di nascita</b></th>
                            <td><c:out value="${paziente.dataNascita}"/></td>
                        </tr>
                        <tr>
                            <th><b>Luogo di nascita</b></th>
                            <td><c:out value="${paziente.luogoNascita}"/></td>
                        </tr>
                        <tr>
                            <th><b>Codice fiscale</b></th>
                            <td><c:out value="${paziente.codiceFiscale}"/></td>
                        </tr>
                        <tr>
                            <th><b>Sesso</b></th>
                            <td><c:out value="${paziente.sesso}"/></td>
                        </tr>
                        <tr>
                            <th><b>Medico di base</b></th>
                            <c:choose>
                                <c:when test="${empty paziente.medico}">
                                    <td>Non ancora scelto.</td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${paziente.medico.nome}"/> <c:out value="${paziente.medico.cognome}"/></td>
                                </c:otherwise>
                            </c:choose>

                        </tr>
                        </tbody>
                    </table>

                </div>




            </div>
            <%--            Funzioni--%>
            <div class="col funzioni-col">
                <h2>Funzioni</h2>

                <div class="card-deck">

                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">
                            <c:choose>
                                <c:when test="${empty paziente.medico}">
                                    Scegli medico
                                </c:when>
                                <c:otherwise>
                                    Cambia medico
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="card-body text-dark">
                            <button type="button" class="btn btn-primary stretched-link" data-toggle="modal" data-target="#modaleCambiaMedico">
                                Apri
                            </button>
                        </div>
                    </div>
                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">Cambia password</div>
                        <div class="card-body text-dark">
                            <button type="button" class="btn btn-primary stretched-link" data-toggle="modal" data-target="#modaleCambioPassword">
                                Apri
                            </button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>



<%-- Modale prescrizione esame ssp--%>
<%-- TODO: fare il modale--%>
<div class="modal fade" id="modaleCambiaMedico" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="prescrivi-ssp">
                <input type="hidden" name="idPaziente" value="<c:out value="${paziente.id}"/>">
                <div class="modal-header">
                    <h5 class="modal-title">Prescrivi un esame specialistico</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="esameSpecialistico">Seleziona tipo esame</label>
                        <select class="form-control" name="esameSpecialistico" id="esameSpecialistico">
                            <c:forEach var="e" items="${esamiPossibili}">
                                <option value="<c:out value="${e.id}"/>"><c:out value="${e.nome}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="visitaSSPDescriptionContainer">
                        <c:forEach var="e" items="${esamiPossibili}">
                            <div id="descrizioneEsameSSP-<c:out value="${e.id}"/>" class="card border-dark mb-3" style="display: none;">
                                <div class="card-header">Descrizione/Note</div>
                                <div class="card-body text-dark">
                                    <p class="card-text"><c:out value="${e.descrizione}"/></p>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <label for="ssp">Seleziona l'SSP</label>
                        <select class="form-control" name="ssp" id="ssp">
                            <c:forEach var="s" items="${ssp}">
                                <option value="<c:out value="${s.id}"/>">SSP di <c:out value="${s.provinciaAppartenenza}"/></option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary">Prescrivi esame</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- TODO: fare il modale per cambio password--%>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

<script src="../js/scheda_paziente.js"></script>

<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>


</body>
</html>