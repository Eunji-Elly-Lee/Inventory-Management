<%-- 
    Document   : The account page where a user manages their acount.
                 The User can add a new item, call, edit, and delete an item.
    Created on : Feb 10, 2022
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
        
        <div class="p-4">
            <nav class="navbar navbar-expand-md navbar-light bg-light mb-3">
                <div class="container-fluid">
                    <a class="navbar-brand">Menu</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-md-0">
                            <li class="nav-item">
                                <a class="nav-link" href="inventory">Inventory</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="account">Account</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="login?logout">Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            
            <div class="px-2" id="wapper">
                <h2>Account for ${user.firstName} ${user.lastName}</h2>

                <form class="my-3" action="account" method="POST">
                    <div class="row mb-1">
                        <label class="form_label2" for="userEmail">E-mail:</label>
                        <input type="email" class="form-control" id="userEmail" name="userEmail" value="${user.email}"
                                disabled>
                    </div>
                    <div class="row mb-1">
                        <label class="form_label2" for="family">Family:</label>
                        <input type="text" class="form-control" id="family" name="family"
                                value="${user.family.familyName}" disabled>
                    </div>
                    <div class="row mb-1">    
                        <label class="form_label2" for="userPassword">Password:</label>
                        <input type="password" class="form-control" id="userPassword" name="userPassword"
                                value="${userPassword}">
                    </div>
                    <div class="row mb-1">
                        <label class="form_label2" for="firstName">First Name:</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}">
                    </div>
                    <div class="row mb-1">
                        <label class="form_label2" for="lastName">Last Name:</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="${lastName}">
                    </div>
                    <div class="row mt-3 mb-2">
                        <label for="isActive">
                            <input type="checkbox" class="me-2" value="${isActive}" name="isActive" id="isActive"
                                   <c:if test="${isActive}">checked</c:if>>
                            Account active
                        </label>
                            
                        <div class="m-2 p-2 notice warning">
                            <span>
                                If an account has been deactivated, you cannot log in.
                                Only an administrator can re-active your account.
                            </span>
                        </div>
                    </div>
                    <div>
                        <input type="submit" value="Update" class="formButton1">
                    </div>
                </form>

                <div>
                    <c:if test="${invalidInput}">
                        <p>Password and name sections should not be empty. Please try it again.</p>
                    </c:if>
                    <c:if test="${updatedUser}">
                        <p>Your account has successfully been updated.</p>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>