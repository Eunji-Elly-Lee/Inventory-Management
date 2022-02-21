<%-- 
    Document   : The management category page where a system adminstrator can create a new category, call and edit a category.
    Created on : Feb 21, 2022
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
                                <a class="nav-link" href="admin">Users</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="family">Families</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="category">Categories</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="login?logout">Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            
            <h2>Category Management for ${admin.firstName}</h2>
            
            <table class="table table-dark table-striped my-3">            
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <th scope="row">${category.categoryName}</th>
                            <td>
                                <form action="category" method="POST">
                                    <input type="hidden" name="selectedCategory" value=${category.categoryId}>
                                    <button type="submit" value="edit" class="btn btn-default">
                                        <img src="https://img.icons8.com/external-kiranshastry-gradient-kiranshastry/32/
                                                000000/external-edit-interface-kiranshastry-gradient-kiranshastry.png"
                                                class="editImg"/>
                                        <input type="hidden" name ="action" value="editCategory">
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
            
            <c:if test="${invalidInput}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Name section should not be empty. Please try it again.</span>
                </div>
            </c:if>  
            <c:if test="${addedCategory}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>New Category &quot;${addCategoryName}&quot; has successfully been added.</span>
                </div>
            </c:if>
            <c:if test="${calledCategory}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Category &quot;${editCategoryName}&quot; has successfully been called.</span>
                </div>
            </c:if>
            <c:if test="${updatedCategory}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Category &quot;${updateCategoryName}&quot; has successfully been updated.</span>
                </div>
            </c:if>
            
            <div class="row ps-1">
                <div class="col-md-5 mx-auto mt-3">
                    <h3>Add Category</h3>

                    <form action="category" method="POST">
                        <div class="row my-3">
                            <label class="form_label1" for="newCategoryName">Name:</label>
                            <input type="text" class="form-control" id="newCategoryName" name="newCategoryName"
                                    value="${newCategoryName}">
                        </div>
                        <div>
                            <input type="submit" value="Add" class="formButton2">
                            <input type="hidden" name ="action" value="addCategory">
                        </div>
                    </form>
                </div>

                <div class="col-md-5 mx-auto mt-3">
                    <h3>Edit Category</h3>

                    <form action="category" method="POST">
                        <div class="row my-3">
                            <label class="form_label2" for="editCategoryName">Name:</label>
                            <input type="text" class="form-control" id="editCategoryName" name="editCategoryName"
                                    value="${editCategoryName}" <c:if test="${isNotEditing}">disabled</c:if>>
                        </div>                        
                        <div>
                            <input type="hidden" name="editCategoryID" value=${editCategoryID}>
                            <input type="submit" value="Update" class="formButton2">
                            <input type="hidden" name ="action" value="updateCategory">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>