<%-- 
    Document   : A page where a user enters their registered email to set a new password when they forget their password.
    Created on : Jan 20, 2022
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
        <link media="all" type="text/css" rel="stylesheet" href="./css/cssStyle.css">
        <title>Inventory Management</title>
    </head>
    <body>
        <div class="p-4 bg-dark">
            <h1><a href="login" class="home">Home Inventory</a></h1>
        </div>
        
        <div class="p-4" id="wapper">
            <h2>Reset Password</h2>
        
            <p>Please enter your registered email to reset your password.</p>
            
            <form class="my-3" action="reset" method="POST">
                <div class="row mb-3">
                    <label class="form_label1" for="email">E-mail:</label>
                    <input type="email" class="form-control" id="email" name="email" value="${email}">
                </div>
                <div>
                    <input type="submit" value="Submit" class="formButton1">
                    <input type="hidden" name ="action" value="submitEmail">
                </div>
            </form>
                
            <div>
                <c:if test="${invalidInput}">
                    <p>please enter your email.</p>
                </c:if>
                <c:if test="${sentEmail}">
                    <p>Please find a link in an email sent to reset new password.</p>
                </c:if>
            </div>
                
            <div class="my-3">
                <a href="login">Go back to Login</a>
            </div>
        </div>        
    </body>
</html>