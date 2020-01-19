<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SSP - Report ricette</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.standalone.css"/>
    <link rel="stylesheet" type="text/css" href="../css/style.css">

</head>
<body>
<%@ include file="navbar.html" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new Date());
    pageContext.setAttribute("today", today);
%>

<div class="container div-centered">
    <h1>Generazione report ricette</h1>
    <p>Seleziona una data per generare un report Excel delle ricette erogate in quella data nella tua regione</p>

    <div class="div-centered calendar" id="datepicker" data-date="<c:out value="${today}"/>"></div>
    <form action="genera-report-ricette" method="get">
        <input type="hidden" id="date" name="date" value="<c:out value="${today}"/>">
        <button type="submit" class="btn btn-primary">Scarica report ricette</button>
    </form>
</div>

<div class="container div-centered">
    <h1>Generazione report visite</h1>
    <p>Seleziona una data per generare un report Excel delle visite erogate in quella data</p>

    <div class="div-centered calendar" id="datepicker2" data-date="<c:out value="${today}"/>"></div>
    <form action="genera-report-visite" method="get">
        <input type="hidden" id="data" name="data" value="<c:out value="${today}"/>">
        <button type="submit" class="btn btn-primary">Scarica report visite</button>
    </form>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.it.min.js"></script>
<script>
    $('#datepicker').datepicker({
        format: "yyyy-mm-dd",
        language: "it"
    });
    $('#datepicker').on('changeDate', function() {
        $('#date').val(
            $('#datepicker').datepicker('getFormattedDate')
        );
    });
</script>
</body>
</html>
