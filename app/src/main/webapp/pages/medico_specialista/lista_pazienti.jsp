<%@ page import="com.icecoldbier.persistence.entities.Paziente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
    <head>
        <title>Medico Specialista - Lista Pazienti</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
    </head>
    <body>
    <%@ include file="navbar.html" %>
    
    <div class="container">
        <h1>Lista pazienti</h1>
        
        <div class="table-responsive-md">
            <table class="datatable table table-striped table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Sesso</th>
                    <th scope="col">Data di nascita</th>
                    <th scope="col">Luogo di nascita</th>
                    <th scope="col">Codice fiscale</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${listaPazientiSpecialista}">
                        <tr data-href="/medico-specialista/scheda-paziente?id=<c:out value="${p.getId()}"/>">
                            <td><c:out value="${p.getNome()}"/></td>
                            <td><c:out value="${p.getCognome()}"/></td>
                            <td><c:out value="${p.getSesso()}"/></td>
                            <td><c:out value="${p.getDataNascita()}"/></td>
                            <td><c:out value="${p.getLuogoNascita()}"/></td>
                            <td><c:out value="${p.getCodiceFiscale()}"/></td>
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
    #
    <script src="../js/init_datatables.js"></script>
    <script src="../js/clickable_row.js"></script>
    </body>
</html>
