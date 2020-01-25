<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8">
    <title>Obamacare - Login</title>

    <link href="css/login.css" rel="stylesheet" type="text/css">

    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" rel="stylesheet">
</head>
<body class="text-center">
<%-- THIS IS ONLY FOR TESTING--%>

<div class="container">

    <form class="form-login" action="dologin" method="POST">
        <input hidden name="inputEmail" value="progettoweb0+mb.antonio.santoro@gmail.com">

        <input hidden name="inputPassword" value="pass123">

        <input type="hidden" name="changePassword" value="0">

        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in as medico di base</button>
    </form>

    <form class="form-login" action="dologin" method="POST">
        <input hidden  name="inputEmail" value="progettoweb0+ms.tommaso.mariani@gmail.com">

        <input hidden  name="inputPassword" value="pass123">

        <input type="hidden" name="changePassword" value="0">

        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in as medico specialista</button>
    </form>

    <form class="form-login" action="dologin" method="POST">
        <input hidden  name="inputEmail" value="progettoweb0+p.ludovica.bianco@gmail.com">

        <input hidden  name="inputPassword" value="pass456">

        <c:choose>
            <c:when test="${param.changepassword}">
                <input type="hidden" name="changePassword" value="1">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="changePassword" value="0">
            </c:otherwise>
        </c:choose>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in as paziente</button>
    </form>

    <form class="form-login" action="dologin" method="POST">
        <input hidden  name="inputEmail" value="progettoweb0+ssp.milano@gmail.com">

        <input hidden  name="inputPassword" value="passssp">

        <input type="hidden" name="changePassword" value="0">

        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in as ssp</button>
    </form>

</div>
















<div class="container">
    <form class="form-login" action="dologin" method="POST">
        <h1 class="h3 mb-3 font-weight-normal">Please log in</h1>
        <label for="inputEmail" class="sr-only">Email address</label>
        <div class="form-group">

            <input id="inputEmail" name="inputEmail" placeholder="Email" type="email" class="form-control <c:if test="${param.error}">is-invalid</c:if>">
            <label for="inputPassword" class="sr-only">Password</label>
            <input id="inputPassword" name="inputPassword" placeholder="Password" type="password" class="form-control <c:if test="${param.error}">is-invalid</c:if>">
            <c:choose>
                <c:when test="${param.changepassword}">
                    <input type="hidden" name="changePassword" value="1">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="changePassword" value="0">
                </c:otherwise>
            </c:choose>
            <c:if test="${param.error}">
                <div class="invalid-feedback">
                    <c:out value="${param.error_message}"/>
                </div>
            </c:if>
        </div>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" name="rememberMe"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>


</div>
</body>
</html>