<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 12/26/19
  Time: 5:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Elenco Visite Specialistiche</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<%@ include file="navbar.html" %>


<div class="container">
    <h1>Elenco visite specialistiche</h1>

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a class="page-link" href="/paziente/elenco-visite-specialistiche?page=${page - 1}" <c:if test="${page == 1}">tabindex="-1"</c:if> >Previous</a>
            </li>

            <c:forEach var = "i" begin = "1" end = "${pagesCount}">
                <li class="page-item <c:if test="${page == i}">active</c:if> "><a class="page-link" href="paziente/elenco-visite-specialistiche?page=${i}">${i}</a></li>
            </c:forEach>

            <li class="page-item <c:if test="${page == pagesCount}">disabled</c:if>">
                <a class="page-link" href="/paziente/elenco-visite-specialistiche?page=${page + 1}" <c:if test="${page == 1}">tabindex="-1"</c:if> >Next</a>
            </li>
        </ul>
    </nav>

    <div class="table-responsive-md">
        <table class="table table-striped table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Tipo Visita</th>
                <th scope="col">Erogata</th>
                <th scope="col">Data Prescrizione</th>
                <th scope="col">Data Erogazione</th>
                <th scope="col">Nome Medico</th>
                <th scope="col">Cognome Medico</th>
                <th scope="col">Report</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${elencoVisite}">
                <tr>
                    <th scope="row">${v.getId_visita()}</th>
                    <th>${v.isErogata()}</th>
                    <th>${v.getDataPrescrizione()}</th>
                    <th>${v.getDataErogazione()}</th>
                    <th>${v.getNomeMedico()}</th>
                    <th>${v.getCognomeMedico()}</th>


                    <c:choose>
                        <c:when test="${v.getIdReport() == 0}">
                            <th><button class="btn btn-sm btn-primary btn-block" disabled="disabled" type="submit">Report</button></th>
                        </c:when>
                        <c:otherwise>
                            <!-- Trigger the modal with a button -->
                            <th><button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal">Report</button></th>th>

                            <!-- Modal -->
                            <div id="myModal" class="modal fade" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Testo report</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">bsn</button>
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </c:otherwise>
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
<script src="../js/clickable_row.js"></script>
</body>
</html>