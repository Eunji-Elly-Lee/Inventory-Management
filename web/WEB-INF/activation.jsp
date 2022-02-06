<%-- 
    Document   : The activate account page where a user enters their email and password to activate their account.
    Created on : Feb 5, 2022
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
        <title>Inventory Management</title>
    </head>
    <body>
        <div class="p-4 bg-dark">
            <h1><a href="login" class="home">Home Inventory</a></h1>
        </div>
        
        <div class="p-4" id="wapper">
            <h2>Activate Account</h2>
        
            <p>Please enter your email and password.</p>
            
            <form class="my-3" action="registration" method="POST">
                <div class="row mt-3 mb-1">
                    <label class="form_label2" for="email">E-mail:</label>
                    <input type="text" class="form-control" id="email" name="email" value="${email}">
                </div>
                <div class="row mb-1">
                    <label class="form_label2" for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" value="${password}">
                </div>
                <div>
                    <input type="hidden" name="uuid" value=${uuid}>
                    <input type="submit" value="Submit" class="formButton1">
                    <input type="hidden" name ="action" value="activate">
                </div>
            </form>
                
            <div>
                <c:if test="${invalidInput}">
                    <p>Invalid. Please try again through a link in the email.</p>
                </c:if>
                <c:if test="${activated}">
                    <p>Account has been activated successfully.</p>
                    <a href="login" class="my-3">Go to Login</a>
                </c:if>
                <c:if test="${failedActivation}">
                    <p>Sorry, something went wrong.</p>
                    <a href="login" class="my-3">Go to Login</a>
                </c:if>
            </div>
        </div>        
    </body>
</html>