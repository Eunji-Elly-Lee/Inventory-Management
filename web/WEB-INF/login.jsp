<%-- 
    Document   : The login page where a user enters their email and password and logs into the web application.
    Created on : Jan 16, 2022
    Author     : Eunji Elly Lee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous">
        </script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link media="all" type="text/css" rel="stylesheet" href="./css/css1.css">
        <title>Home nVentory</title>
    </head>
    <body>
        <div class="p-4 bg-dark">
            <h1><a href="login" class="home">Home nVentory</a></h1>
        </div>
        
        <div class="p-4" id="wapper">
            <h2>Login</h2>
        
            <form class="my-3" action="login" method="POST">
                <div class="row mb-1">
                    <label class="form_label1" for="email">E-mail:</label>
                    <input type="email" class="form-control" id="email" name="email" value="${email}">
                </div>
                <div class="row mb-3">
                    <label class="form_label1" for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
                <div>
                    <input type="submit" value="Log in" class="formButton2">
                </div>
            </form>
                
            <div>
                <c:if test="${loggedOut}">
                    <p>You have successfully logged out.</p>
                </c:if>
                <c:if test="${invalidLogin}">
                    <p>Invalid login.</p>
                </c:if>
                <c:if test="${nonActiveUser}">
                    <p>Sorry, your account is non-active.</p>
                </c:if>                
            </div>
        </div>
    </body>
</html>