<%@ page import="com.icecoldbier.persistence.entities.Paziente" %>
<%@ page import="com.icecoldbier.persistence.entities.VisitaSpecialistica" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Medico Specialista - Lista visite</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
    </head>
    <body>
    <%@ include file="navbar.html" %>

    <div class="container">
        <h1>Lista visite</h1>

        <div class="table-responsive-md">
            <table class="table table-striped table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Data prescrizione</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Erogata</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${visite}">
                        <tr data-href="/medico-specialista/visite/dettagli-visita?id=${p.getId()}">
                            <td scope="row">${p.getDataPrescrizione()}</td>
                            <td>${p.getPaziente().getNome()}</td>
                            <td>${p.getPaziente().getCognome()}</td>
                            <td>${p.isErogata()}</td>
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
    <script src="../js/clickable_row.js"></script>
    </body>
</html>
