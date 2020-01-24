<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 1/18/20
  Time: 6:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="elencoVisiteSSP" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.VisitaSSP>"/>
<html>
<head>
    <title>Elenco Visite SSP</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>

<div class="container">
    <h1>Elenco visite SSP</h1>

    <div class="table-responsive-md">
        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tipo Visita</th>
                <th scope="col">Erogata</th>
                <th scope="col">Data Prescrizione</th>
                <th scope="col">Data Erogazione</th>
                <th scope="col">Medico base</th>
                <th scope="col">Praticante</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="v" items="${elencoVisiteSSP}">
                <tr>
                    <td><c:out value="${v.tipo_visita.nome}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${v.erogata}">SI</c:when>
                            <c:otherwise>NO</c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${v.dataPrescrizione}"/></td>
                    <td><c:out value="${v.dataErogazione}"/></td>
                    <td><c:out value="${v.medicoBase.toStringNomeCognome()}"/></td>
                    <td><c:out value="${v.ssp.toStringSSP()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>

<script src="../js/init_datatables.js"></script>
<script src="../js/clickable_row.js"></script>
</body>
</html>
