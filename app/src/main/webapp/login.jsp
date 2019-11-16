<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%-- Set language based on user's choice --%>
<c:if test="${!empty locale}">
    <fmt:setLocale value="${locale}" scope="session"/>
</c:if>

<c:set var='view' value='/login.html' scope='session'/>

<!DOCTYPE html>
<html lang="<c:out value="${locale}"/>">
<head>
    <meta charset="UTF-8">
    <title>Obamacare - Login</title>

    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">

    <link href="css/login.css" rel="stylesheet">

    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-light bg-primary">
    <a class="navbar-brand">Obamacare</a>
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <c:choose>
                <c:when test="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] eq 'it'}">
                    Italiano
                </c:when>
                <c:otherwise>
                    English
                </c:otherwise>
            </c:choose>
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item" href="setLocale?locale=it">Italiano</a>
            <a class="dropdown-item" href="setLocale?locale=en">English</a>
        </div>
    </div>
</nav>
<div class="container">
    <form class="form-login" action="login" method="POST">
        <h1 class="h3 mb-3 font-weight-normal"><fmt:message key='loginHeader'/></h1>
        <label for="inputEmail" class="sr-only"><fmt:message key='loginEmail'/></label>
        <div class="form-group">
            <input id="inputEmail" name="inputEmail" placeholder="<fmt:message key='loginEmail'/>" type="email"
                   class="form-control <c:if test="${param.error}">is-invalid</c:if>">
            <label for="inputPassword" class="sr-only"><fmt:message key='loginPassword'/></label>
            <input id="inputPassword" name="inputPassword" placeholder="<fmt:message key='loginPassword'/>" type="password"
                   class="form-control <c:if test="${param.error}">is-invalid</c:if>">
            <c:if test="${param.error}">
                <div class="invalid-feedback">
                    <c:out value="<fmt:message key='loginError'/>"/>
                </div>
            </c:if>
        </div>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> <fmt:message key='loginRememberMe'/>
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key='loginButton'/></button>
    </form>
</div>

</body>
</html>