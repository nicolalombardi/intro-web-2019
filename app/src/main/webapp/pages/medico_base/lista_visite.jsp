<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/customTags/ellipsizeTag.tld" prefix="ct" %>
<%@ taglib uri="/WEB-INF/customTags/miniProfileTag.tld" prefix="mp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="listaVisite" scope="request" type="java.util.List<com.icecoldbier.persistence.entities.VisitaBase>"/>


<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Medico Base - Lista Visite Base</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
<body>
<%@ include file="navbar.html" %>

<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="container">
    <c:choose>
        <c:when test="${empty paziente}">
            <h1>Lista visite base</h1>
        </c:when>
        <c:otherwise>
            <h1>Lista visite base di <c:out value="${paziente.nome} ${paziente.cognome}"/></h1>
        </c:otherwise>
    </c:choose>


        <table class="datatable table table-striped">
            <thead class="thead-dark">
            <tr>
                <th class="all" scope="col">Foto</th>
                <th class="all" scope="col">Paziente</th>
                <th class="min-sm default-sort" scope="col">Data erogazione</th>
                <th class="min-md" scope="col">Ricetta</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${listaVisite}">
                <tr>
                    <td>
                        <img class="profile-picture-thumbnail" src="<c:out value="${v.paziente.fotoThumb}"/>" height="48" width="48">
                    </td>
                    <td>
                        <mp:miniProfileTag paziente="${v.paziente}"/>
                        <c:if test="${user.id == v.paziente.medico.id}">
                            <span class="badge badge-success"><i class="material-icons badge-icon">check</i></span>
                        </c:if>
                    </td>
                    <td><c:out value="${v.prettyDataErogazione}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${empty v.ricetta}">
                                Nessuna ricetta
                            </c:when>
                            <c:otherwise>
                                <ct:ellipsizeTag maxLength="30"><c:out value="${v.ricetta.nome}"/></ct:ellipsizeTag>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>

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
<script src="../js/ricerca_pazienti.js"></script>
</body>
</html>
