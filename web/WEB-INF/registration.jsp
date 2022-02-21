<%-- 
    Document   : The registration page where a user enters their information, which are an email address, password,
                 name, and selects their family.
    Created on : Jan 30, 2022
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
            <h2>Create new Account</h2>
        
            <p>Please fill out your information.</p>
            
            <form class="my-3" action="registration" method="POST">
                <div class="row mt-3 mb-1">
                    <label class="form_label2" for="newEmail">E-mail:</label>
                    <input type="text" class="form-control" id="newEmail" name="newEmail" value="${newEmail}">
                </div>
                <div class="row mb-1">
                    <label class="form_label2" for="newPassword">Password:</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" value="${newPassword}">
                </div>
                <div class="row mb-1">
                    <label class="form_label2" for="newFirstName">First Name:</label>
                    <input type="text" class="form-control" id="newFirstName" name="newFirstName" value="${newFirstName}">
                </div>
                <div class="row mb-1">
                    <label class="form_label2" for="newLastName">Last Name:</label>
                    <input type="text" class="form-control" id="newLastName" name="newLastName" value="${newLastName}">
                </div>
                <div class="row mb-3">
                    <label class="form_label2" for="newFamily">Family:</label>
                    <select class="form-select" id="newFamily" name="newFamily">
                        <option value="0">---------- Choose Family ----------</option>
                        <c:forEach items="${families}" var="family">
                            <option value="${family.familyId}"
                                    <c:if test="${newFamily == family.familyId}">selected</c:if>>
                                ${family.familyName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <input type="submit" value="Submit" class="formButton1">
                    <input type="hidden" name ="action" value="register">
                </div>
            </form>
                
            <div>
                <c:if test="${invalidInput}">
                    <p>All sections should not be empty. Please try it again.</p>
                </c:if>
                <c:if test="${addedUser}">
                    <p>
                        New account of user &quot;${newUserName}&quot; has successfully been created.
                        Please find a link in an email sent to activate an account.
                    </p>
                </c:if>
                <c:if test="${failedAdd}">
                    <p>Email already registered is not available. Please try again with another email.</p>
                </c:if>
            </div>
                
            <div class="my-3">
                <a href="login">Go back to Login</a>
            </div>
        </div>        
    </body>
</html>