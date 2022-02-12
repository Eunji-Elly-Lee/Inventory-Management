<%-- 
    Document   : The family administrator page where a user whoes role is an administrator manages user's accounts
                 belonging to the family.
                 The User can create a new account, call, edit, and delete an acount.
    Created on : Feb 11, 2022
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
                                <a class="nav-link active" aria-current="page" href="admin">Users</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="login?logout">Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            
            <h2>User Management for ${admin.firstName}</h2>
            
            <table class="table table-dark table-striped my-3">            
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Family</th>
                        <th scope="col">Role</th>
                        <th scope="col">Active</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <th scope="row">${user.firstName} ${user.lastName}</th>
                            <td>${user.family.familyName}</td>
                            <td>${user.role.roleName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.active}">Active</c:when>
                                    <c:otherwise>None</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="admin" method="POST">
                                    <input type="hidden" name="selectedUser" value=${user.email}>
                                    <button type="submit" value="edit" class="btn btn-default">
                                        <img src="https://img.icons8.com/external-kiranshastry-gradient-kiranshastry/32/
                                                000000/external-edit-interface-kiranshastry-gradient-kiranshastry.png"
                                                class="editImg"/>
                                        <input type="hidden" name ="action" value="editUser">
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form action="admin" method="POST">
                                    <input type="hidden" name="pointedUser" value=${user.email}>
                                    <button type="submit" value="delete" class="btn btn-default">
                                        <img src="https://img.icons8.com/color/32/000000/delete-sign--v1.png"
                                                class="deleteImg"/>
                                        <input type="hidden" name ="action" value="deleteUser">
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
            
            <c:if test="${invalidInput}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>All sections should not be empty. Please try it again.</span>
                </div>
            </c:if>  
            <c:if test="${addedUser}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>
                        New user &quot;${newUserName}&quot; has successfully been added.
                        Please find a link in an email sent to activate an account.
                    </span>
                </div>
            </c:if>
            <c:if test="${failedAdd}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Email already registered is not available. Please try again with another email.</span>
                </div>
            </c:if>
            <c:if test="${calledUser}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>User &quot;${editUserName}&quot; has successfully been called.</span>
                </div>
            </c:if>
            <c:if test="${updatedUser}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>User &quot;${updateUserName}&quot; has successfully been updated.</span>
                </div>
            </c:if>
            <c:if test="${cannotDeleting}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Sorry, cannot delete your own account.</span>
                </div>
            </c:if>
            <c:if test="${deletedUser}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>User &quot;${deleteUserName}&quot; has successfully been deleted.</span>
                    
                    <form action="admin" method="POST" class="ms-1 undoForm">
                        <input type="submit" value="Undo">
                        <input type="hidden" name ="action" value="undo">
                    </form>
                </div>
            </c:if>
            <c:if test="${undoDeleting}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>User &quot;${undoUserName}&quot; has been successfully restored.</span>
                </div>
            </c:if>
            
            <div class="row ps-1">
                <div class="col-md-5 mx-auto mt-3">
                    <h3>Add User</h3>

                    <form action="admin" method="POST">
                        <div class="row mt-3 mb-1">
                            <label class="form_label2" for="newEmail">E-mail:</label>
                            <input type="text" class="form-control" id="newEmail" name="newEmail" value="${newEmail}">
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="newPassword">Password:</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword"
                                    value="${newPassword}">
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="newFirstName">First Name:</label>
                            <input type="text" class="form-control" id="newFirstName" name="newFirstName"
                                    value="${newFirstName}">
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="newLastName">Last Name:</label>
                            <input type="text" class="form-control" id="newLastName" name="newLastName"
                                    value="${newLastName}">
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="newFamily">Family:</label>
                            <select class="form-select" id="newFamily" name="newFamily">
                                <option value="0">---------- Choose Family -----------</option>
                                <option value="${family.familyId}"
                                        <c:if test="${newFamily == family.familyId}">selected</c:if>>
                                    ${family.familyName}
                                </option>
                            </select>
                        </div>
                        <div class="row mb-3">
                            <label class="form_label2" for="newRole">Role:</label>
                            <select class="form-select" id="newRole" name="newRole">
                                <option value="0">---------- Choose Role -----------------</option>
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role.roleId}"
                                            <c:if test="${newRole == role.roleId}">selected</c:if>>
                                        ${role.roleName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <input type="submit" value="Add" class="formButton2">
                            <input type="hidden" name ="action" value="addUser">
                        </div>
                    </form>
                </div>

                <div class="col-md-5 mx-auto mt-3">
                    <h3>Edit User</h3>

                    <form action="admin" method="POST">
                        <div class="row mt-3 mb-1">
                            <label class="form_label2" for="editEmail">E-mail:</label>
                            <input type="text" class="form-control" id="editEmail" name="editEmail" value="${editEmail}"
                                    disabled>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="editPassword">Password:</label>
                            <input type="password" class="form-control" id="editPassword" name="editPassword"
                                    value="${editPassword}" <c:if test="${isNotEditing}">disabled</c:if>>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="editFirstName">First Name:</label>
                            <input type="text" class="form-control" id="editFirstName" name="editFirstName"
                                    value="${editFirstName}" <c:if test="${isNotEditing}">disabled</c:if>>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="editLastName">Last Name:</label>
                            <input type="text" class="form-control" id="editLastName" name="editLastName"
                                    value="${editLastName}" <c:if test="${isNotEditing}">disabled</c:if>>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="editFamily">Family:</label>
                            <select class="form-select" id="editFamily" name="editFamily"
                                    <c:if test="${isNotEditing}">disabled</c:if>>
                                <option value="${family.familyId}">${family.familyName}</option>
                            </select>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label2" for="editRole">Role:</label>
                            <select class="form-select" id="editRole" name="editRole"
                                    <c:if test="${isNotEditing}">disabled</c:if>>
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role.roleId}"
                                            <c:if test="${editRole == role.roleId}">selected</c:if>>
                                        ${role.roleName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row my-3">
                            <label for="isActive">
                                <input type="checkbox" class="me-2" value="${isActive}" name="isActive" id="isActive"
                                       <c:if test="${isActive}">checked</c:if>>
                                Account active
                            </label>
                        </div>
                        <div>
                            <input type="hidden" name="editUserEmail" value=${editUserEmail}>
                            <input type="submit" value="Update" class="formButton2">
                            <input type="hidden" name ="action" value="updateUser">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>