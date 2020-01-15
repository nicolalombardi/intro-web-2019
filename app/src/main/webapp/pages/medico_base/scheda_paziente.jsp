<%@ page
        import="com.icecoldbier.persistence.entities.User" %><%--<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>--%>
<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>
<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<jsp:useBean id="visitePossibili" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaPossibile>"/>
<jsp:useBean id="esamiPossibili" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaPossibile>"/>
<jsp:useBean id="mediciSpecialisti" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.User>"/>
<jsp:useBean id="ssp" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.SSP>"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Scheda di <c:out value="${paziente.nome}"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Scheda del paziente</h1>
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
                                <c:when test="${empty medico}">
                                    <td>Non ancora scelto.</td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${medico.nome}"/> <c:out value="${medico.cognome}"/></td>
                                </c:otherwise>
                            </c:choose>

                        </tr>
                        </tbody>
                    </table>

                </div>

                <%--                Calcola se è il tuo paziente--%>
                <%
                    boolean isMedicoAssociato = false;
                    User user = (User) session.getAttribute("user");
                    if (medico.getId().equals(user.getId())) {
                        isMedicoAssociato = true;
                    }
                    request.setAttribute("isMedicoAssociato", isMedicoAssociato);
                %>


            </div>
            <%--            Funzioni--%>
            <div class="col funzioni-col">
                <h2>Funzioni</h2>

                <c:if test="${not isMedicoAssociato}">
                    <p class="info-text">
                        <i class="material-icons info-icon">info_outline</i>
                        <small>Funzioni disabilitate perchè non è un tuo paziente</small>
                    </p>
                </c:if>

                <div class="card-deck">

                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">Eroga visita base</div>
                        <div class="card-body text-dark">
                            <p class="card-text">Eroga una visita base con la possibilità di associare una ricetta.</p>
                            <button type="button" class="btn btn-primary stretched-link" data-toggle="modal"
                                    data-target="#modaleErogaVisita"
                                    <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                                Apri
                            </button>
                        </div>
                    </div>
                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">Prescrivi visita specialistica
                        </div>
                        <div class="card-body text-dark">
                            <p class="card-text">Prescrivi una visita specialistica selezionando visita e medico</p>
                            <button type="button" class="btn btn-primary stretched-link" data-toggle="modal"
                                    data-target="#modalePrescriviVisitaSpecialistica"
                                    <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                                Apri
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card-deck">

                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">Prescrivi esame SSP</div>
                        <div class="card-body text-dark">
                            <p class="card-text">Prescrivi un'esame specialistico selezionando esame e ssp</p>
                            <button type="button" class="btn btn-primary stretched-link" data-toggle="modal"
                                    data-target="#modalePrescriviEsameSSP"
                                    <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                                Apri
                            </button>

                        </div>
                    </div>
                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">Elenco visite base</div>
                        <div class="card-body text-dark">
                            <p class="card-text">Visualizza l'elenco delle visite base erogate per questo paziente</p>
                            <a class="btn btn-primary stretched-link <c:if test="${not isMedicoAssociato}">disabled</c:if>"
                               role="button"  <c:if test="${isMedicoAssociato}">href="lista-visite-base?id_paziente=<c:out value="${paziente.id}"/>"</c:if>
                               <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                                Vai
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-deck">

                    <div class="card border-dark mb-3" style="max-width: 100rem;">
                        <div class="card-header">Elenco esami specialistici</div>
                        <div class="card-body text-dark">
                            <p class="card-text">Visualizza l'elenco degli esami specialistici erogati per questo
                                paziente</p>
                            <a class="btn btn-primary stretched-link <c:if test="${not isMedicoAssociato}">disabled</c:if>"
                               role="button" <c:if test="${isMedicoAssociato}">href="lista-visite-specialistiche?id_paziente=<c:out value="${paziente.id}"/>"</c:if>
                               <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                                Vai
                            </a>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%--Modale eroga visita--%>
<div class="modal fade" id="modaleErogaVisita" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="eroga">
                <input type="hidden" name="idPaziente" value="<c:out value="${paziente.id}"/>">
                <div class="modal-header">
                    <h5 class="modal-title">Eroga una visita base</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="testoRicetta">Ricetta</label>
                        <textarea class="form-control" name="testoRicetta" id="testoRicetta" rows="3"
                                  placeholder="Inserisci qua l'oggetto della ricetta. Lasciando questo campo vuoto non verrà inserita alcuna ricetta"></textarea>

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

<%-- Modale prescrizione visita specialistica--%>
<div class="modal fade" id="modalePrescriviVisitaSpecialistica" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="prescrivi-specialistica">
                <input type="hidden" name="idPaziente" value="<c:out value="${paziente.id}"/>">
                <div class="modal-header">
                    <h5 class="modal-title">Prescrivi una visita specialistica</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="visitaSpecialistica">Seleziona tipo visita</label>
                        <select class="form-control" name="visitaSpecialistica" id="visitaSpecialistica">
                            <c:forEach var="v" items="${visitePossibili}">
                                <option value="<c:out value="${v.id}"/>"><c:out value="${v.nome}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="visitaSpecialisticaDescriptionContainer">
                        <c:forEach var="v" items="${visitePossibili}">
                            <div id="descrizioneVisitaSpecialistica-<c:out value="${v.id}"/>" class="card border-dark mb-3"
                                 style="display: none;">
                                <div class="card-header">Descrizione/Note</div>
                                <div class="card-body text-dark">
                                    <p class="card-text"><c:out value="${v.descrizione}"/></p>
                                </div>
                            </div>

                        </c:forEach>
                    </div>

                    <div class="form-group">
                        <label for="medicoSpecialista">Seleziona il medico</label>
                        <select class="form-control" name="medicoSpecialista" id="medicoSpecialista">
                            <c:forEach var="m" items="${mediciSpecialisti}">
                                <option value="<c:out value="${m.id}"/>"><c:out value="${m.nome} ${m.cognome}"/></option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary">Prescrivi visita</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- Modale prescrizione esame ssp--%>
<div class="modal fade" id="modalePrescriviEsameSSP" tabindex="-1" role="dialog" aria-hidden="true">
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

