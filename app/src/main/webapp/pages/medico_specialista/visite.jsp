<%@ page import="com.icecoldbier.persistence.entities.Paziente" %>
<%@ page import="com.icecoldbier.persistence.entities.VisitaSpecialistica" %>
<%@ page import="com.icecoldbier.persistence.entities.VisitaPossibile" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Medico Specialista - Lista visite</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.css"/>
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    </head>
    <body>
    <%@ include file="navbar.html" %>

    <div class="container">

        <h1>Lista visite</h1>

            <table class="datatable table table-striped table-hover">
                <thead class="thead-dark">
                <tr>
                    <th class="all" scope="col">Data prescrizione</th>
                    <th class="all" scope="col">Tipologia visita</th>
                    <th class="min-sm" scope="col">Nome</th>
                    <th class="min-md" scope="col">Cognome</th>
                    <th class="min-md" scope="col">Erogata</th>
                    <th class="min-lg" scope="col">Dettagli</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${visite}">
                        <tr>
                            <td><c:out value="${p.getDataPrescrizione()}"/></td>
                            <td><c:out value="${p.getTipo_visita().getNome()}"/></td>
                            <td><c:out value="${p.getPaziente().getNome()}"/></td>
                            <td><c:out value="${p.getPaziente().getCognome()}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${p.isErogata()==true}">Si</c:when>
                                    <c:when test="${p.isErogata()==false}">No</c:when>
                                </c:choose>
                            </td>
                            <td><a class="btn btn-primary mb-3 icon-white"  href="visite/dettagli-visita?id=<c:out value="${p.getId()}"/>"
                                   role="button"><i class="material-icons">open_in_new</i></a></td>
                        </tr>
                </c:forEach>
                </tbody>
            </table>
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
    <script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/r-2.2.3/datatables.min.js"></script>

    <script src="../js/init_datatables.js"></script>
    </body>
</html>
