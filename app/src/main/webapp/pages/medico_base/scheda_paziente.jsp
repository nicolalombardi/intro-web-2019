<%@ page
        import="com.icecoldbier.persistence.entities.User" %><%--<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>--%>
<jsp:useBean id="medico" scope="request" type="com.icecoldbier.persistence.entities.User"/>
<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<jsp:useBean id="visitePossibili" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaPossibile>"/>
<jsp:useBean id="esamiPossibili" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaPossibile>"/>
<jsp:useBean id="mediciSpecialisti" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.User>"/>
<jsp:useBean id="ssp" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.SSP>"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Scheda di ${paziente.nome}</title>
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
                                        <img src="${paziente.foto}" height="300px" width="300px">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/images/profile_placeholder.svg" height="300px" width="300px">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th><b>Nome</b></th>
                            <td>${paziente.nome}</td>
                        </tr>
                        <tr>
                            <th><b>Cognome</b></th>
                            <td>${paziente.cognome}</td>
                        </tr>
                        <tr>
                            <th><b>Email</b></th>
                            <td>${paziente.username}</td>
                        </tr>
                        <tr>
                            <th><b>Provincia di appartenenza</b></th>
                            <td>${paziente.provinciaAppartenenza}</td>
                        </tr>
                        <tr>
                            <th><b>Data di nascita</b></th>
                            <td>${paziente.dataNascita}</td>
                        </tr>
                        <tr>
                            <th><b>Luogo di nascita</b></th>
                            <td>${paziente.luogoNascita}</td>
                        </tr>
                        <tr>
                            <th><b>Codice fiscale</b></th>
                            <td>${paziente.codiceFiscale}</td>
                        </tr>
                        <tr>
                            <th><b>Sesso</b></th>
                            <td>${paziente.sesso}</td>
                        </tr>
                        <tr>
                            <th><b>Medico di base</b></th>
                            <c:choose>
                                <c:when test="${empty medico}">
                                    <td>Non ancora scelto.</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${medico.nome} ${medico.cognome}</td>
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

                <div class="row funzioni-row">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modaleErogaVisita"
                            <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                        Eroga visita base
                    </button>
                </div>
                <div class="row funzioni-row">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalePrescriviVisitaSpecialistica"
                            <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                        Prescrivi visita specialistica
                    </button>
                </div>
                <div class="row funzioni-row">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalePrescriviEsameSSP"
                            <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                        Prescrivi esame SSP
                    </button>
                </div>
                <div class="row funzioni-row">
                    <a class="btn btn-primary <c:if test="${not isMedicoAssociato}">disabled</c:if>" role="button" href="lista-visite-base?id_paziente=${paziente.id}"
                            <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                        Visualizza elenco visite base
                    </a>
                </div>
                <div class="row funzioni-row">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modaleElencoEsamiSpecialistici"
                            <c:if test="${not isMedicoAssociato}">disabled</c:if>>
                        Visualizza elenco esami/visite specialistiche
                    </button>
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
                <input type="hidden" name="idPaziente" value="${paziente.id}">
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
                <input type="hidden" name="idPaziente" value="${paziente.id}">
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
                                <option value="${v.id}">${v.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="visitaSpecialisticaDescriptionContainer">
                        <c:forEach var="v" items="${visitePossibili}">
                            <div id="descrizioneVisitaSpecialistica-${v.id}" class="card border-dark mb-3" style="display: none;" >
                                <div class="card-header">Descrizione/Note</div>
                                <div class="card-body text-dark">
                                    <p class="card-text">${v.descrizione}</p>
                                </div>
                            </div>

                        </c:forEach>
                    </div>

                    <div class="form-group">
                        <label for="medicoSpecialista">Seleziona il medico</label>
                        <select class="form-control" name="medicoSpecialista" id="medicoSpecialista">
                            <c:forEach var="m" items="${mediciSpecialisti}">
                                <option value="${m.id}">${m.nome} ${m.cognome}</option>
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
                <input type="hidden" name="idPaziente" value="${paziente.id}">
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
                                <option value="${e.id}">${e.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="visitaSSPDescriptionContainer">
                        <c:forEach var="e" items="${esamiPossibili}">
                            <div id="descrizioneEsameSSP-${e.id}" class="card border-dark mb-3" style="display: none;" >
                                <div class="card-header">Descrizione/Note</div>
                                <div class="card-body text-dark">
                                    <p class="card-text">${e.descrizione}</p>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <label for="ssp">Seleziona l'SSP</label>
                        <select class="form-control" name="ssp" id="ssp">
                            <c:forEach var="s" items="${ssp}">
                                <option value="${s.id}">SSP di ${s.provinciaAppartenenza}</option>
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

