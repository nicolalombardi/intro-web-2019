<%@ page import="com.icecoldbier.persistence.entities.Paziente" %>
<%@ page import="com.icecoldbier.persistence.entities.VisitaSpecialistica" %>
<%@ page import="com.icecoldbier.persistence.entities.User" %>
<jsp:useBean id="visita" scope="request" type="com.icecoldbier.persistence.entities.VisitaSpecialistica"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Dettagli visita</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="../css/medico_base.css">
    </head>
    <body>
    <%@ include file="navbar.html" %>

    <div class="container">
        <h1>Visita di </h1>
        <div class="container">
            <div class="row">
    <%--            Dati paziente--%>
                <div class="col">
                    <div class="row">
                        <table class="table">
                            <tbody>
                            <tr>
                                <th><b>Nome</b></th>
                                <td>${visita.getPaziente().getNome()}</td>
                            </tr>
                            <tr>
                                <th><b>Cognome</b></th>
                                <td>${visita.getPaziente().getCognome()}</td>
                            </tr>
                            <tr>
                                <th><b>Data di prescrizione</b></th>
                                <td>${visita.getDataPrescrizione()}</td>
                            </tr>
                            <tr>
                                <th><b>Erogata</b></th>
                                <td>${visita.isErogata()}</td>
                            </tr>
                            <tr>
                                <th><b>Medico di base</b></th>
                                <td> <c:out value="${visita.getMedicoBase().getNome()} ${visita.getMedicoBase().getCognome()}"/> </td>
                            </tr>
                            
                            </tbody>
                        </table>
                    </div>
                    <c:if test="${visita.isErogata()==false}">
                        <div class="row">
                            <form method="post" action="">
                                <div class="form-group">
                                    <label for="exampleFormControlTextarea1">Report</label>
                                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" name="idPaziente" value="">
                                    <button type="submit" class="btn btn-primary form-control">Eroga visita</button>
                                </div>
                            </form>
                        </div>
                    </c:if>
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
    <script src="../js/clickable_row.js"></script>
    </body>
</html>
