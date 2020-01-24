<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 1/2/20
  Time: 11:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Elenco Prescrizioni Ricette</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<%@ include file="navbar.html" %>
<jsp:useBean id="elencoRicette" scope="request"
             type="java.util.List<com.icecoldbier.persistence.entities.Ricetta>"/>


<div class="container">
    <h1>Elenco ricette prescritte</h1>

    <div class="table-responsive-md">
        <table class="datatable table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Farmaco prescritto</th>
                <th scope="col">Acquistabile</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="r" items="${elencoRicette}">
                <tr>
                    <td><c:out value="${r.nome}"/></td>
                    <c:choose>
                        <c:when test="${r.prescritta}">
                            <td>Si</td>
                            <form action="stampa-ricetta" method="POST">
                                <input hidden id="idRicetta" name="idRicetta" value=<c:out value="${r.id}"/>>
                                <td><button class="btn btn-sm btn-primary btn-block" type="submit">Stampa ricetta</button></td>
                            </form>
                        </c:when>
                        <c:otherwise><td>NO</td><td></td></c:otherwise>
                    </c:choose>

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