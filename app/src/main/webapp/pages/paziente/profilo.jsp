<jsp:useBean id="paziente" scope="request" type="com.icecoldbier.persistence.entities.Paziente"/>
<jsp:useBean id="listaMediciSceglibili" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.User>"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Profilo</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style>
    #BottoneFoto {
        vertical-align: bottom;
    }
</style>
</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <h1>Profilo</h1>
    <div class="col">
        <div class="row">
            <table class="table profile-table">
                <tbody>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <c:choose>
                            <c:when test="${not empty paziente.foto}">
                                <img class="profile-picture" src="<c:out value="${paziente.foto}"/>">
                                <button type="button" class="btn btn-xs btn-dark" id= "BottoneFoto" data-toggle="modal" data-target="#modaleCambiaFoto" >
                                    <i class="material-icons md-light">create</i>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <img class="profile-picture" src="/images/profile_placeholder.svg">
                                <button type="button" class="btn btn-xs btn-dark" data-toggle="modal" id= "BottoneFoto" data-target="#modaleCambiaFoto" >
                                    <i class="material-icons md-light">create</i>
                                </button>
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
                            <td>Non ancora scelto.   <button type="button" class="btn btn-xs btn-dark" data-toggle="modal" data-target="#modaleCambiaMedico" ><i class="material-icons md-light">create</i></button></td>
                        </c:when>
                        <c:otherwise>
                            <td><c:out value="${paziente.medico.nome}"/> <c:out value="${paziente.medico.cognome}   "/><button type="button" class="btn btn-xs btn-dark" data-toggle="modal" data-target="#modaleCambiaMedico" ><i class="material-icons md-light">create</i></button></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th><b>Password:</b></th>
                    <td>
                        <c:choose>
                            <c:when test="${empty changePassword}">
                                <form method="POST" action="cambia-password">
                                    <input type="hidden" name="cambiaPassword" value=1>
                                    <button type="submit" class="btn btn-primary">Cambia password</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modaleCambioPassword">Cambia password</button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="modal fade" id="modaleCambiaFoto" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="cambia-foto" enctype = "multipart/form-data">
                <div class="modal-header">
                    <h5 class="modal-title">Carica foto profilo</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <input type="file" name="photo" accept="image/png, image/jpeg" size="10">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary">Carica</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="modaleCambiaMedico" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="cambia-medico">
                <input type="hidden" name="idPaziente" value="<c:out value="${paziente.id}"/>">
                <div class="modal-header">
                    <h5 class="modal-title">Cambia medico</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="changeMedico">Seleziona nuovo medico</label>
                        <select class="form-control" name="changeMedico" id="changeMedico">
                            <c:forEach var="m" items="${listaMediciSceglibili}">
                                <c:choose>
                                    <c:when test="${m.id == paziente.medico.id}">
                                        <option value="<c:out value="${m.id}"/>"><c:out value="${m.toStringNomeCognome()}   (attuale)"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="<c:out value="${m.id}"/>"><c:out value="${m.toStringNomeCognome()}"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary">Cambia medico</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="modaleCambioPassword" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form method="post" action="cambia-password">
                <input type="hidden" name="idPaziente" value="<c:out value="${paziente.id}"/>">
                <input type="hidden" name="cambiaPassword" value=0>
                <div class="modal-header">
                    <h5 class="modal-title">Cambia password</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Inserire nuova password: <input type="password" name="password">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary">Cambia password</button>
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
<script src="../js/toggle_modal_hash.js"></script>

<%--These are the success and error modals--%>
<%@ include file="../../WEB-INF/fragments/statusModals.jspf" %>


</body>
</html>