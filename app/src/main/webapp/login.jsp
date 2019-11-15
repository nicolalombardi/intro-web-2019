<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Obamacare - Login</title>

    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">

    <link href="css/login.css" rel="stylesheet">

    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-login" action="login" method="POST">
    <h1 class="h3 mb-3 font-weight-normal">Please log in</h1>
    <label for="inputEmail" class="sr-only">Email address</label>
    <div class="form-group">

        <input id="inputEmail" name="inputEmail" placeholder="Email" type="email" class="form-control <c:if test="${param.error}">is-invalid</c:if>">
        <label for="inputPassword" class="sr-only">Password</label>
        <input id="inputPassword" name="inputPassword" placeholder="Password" type="password" class="form-control <c:if test="${param.error}">is-invalid</c:if>">
        <c:if test="${param.error}">
            <div class="invalid-feedback">
                <c:out value="${param.error_message}"/>
            </div>
        </c:if>
    </div>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
</form>
</body>
</html>