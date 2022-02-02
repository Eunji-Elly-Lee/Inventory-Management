<%-- 
    Document   : The inventory page where a user enters their email and password and logs into the web application.
                 A User can also create a new account or set up a new password instead of a forgotten one here.
    Created on : Feb 1, 2022
    Author     : Eunji Elly Lee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                                <a class="nav-link active" aria-current="page" href="inventory">Inventory</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="account">Account</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="login?logout">Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            
            <h2>Inventory for ${user.firstName} ${user.lastName}</h2>
            
            <table class="table table-dark table-striped my-3">            
                <thead>
                    <tr>
                        <th scope="col">Category</th>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <th scope="row">${item.category.categoryName}</th>
                            <td>${item.itemName}</td>
                            <td><fmt:formatNumber type="currency" value="${item.price}"/></td>
                            <td>
                                <form action="inventory" method="POST">
                                    <input type="hidden" name="selectedItem" value=${item.itemId}>
                                    <button type="submit" value="edit" class="btn btn-default">
                                        <img src="https://img.icons8.com/external-kiranshastry-gradient-kiranshastry/32/
                                                000000/external-edit-interface-kiranshastry-gradient-kiranshastry.png"
                                                class="editImg"/>
                                        <input type="hidden" name ="action" value="editItem">
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form action="inventory" method="POST">
                                    <input type="hidden" name="pointedItem" value=${item.itemId}>
                                    <button type="submit" value="delete" class="btn btn-default">
                                        <img src="https://img.icons8.com/color/32/000000/delete-sign--v1.png"
                                                class="deleteImg"/>
                                        <input type="hidden" name ="action" value="deleteItem">
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
            
            <c:if test="${invalidInput}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Invalid Input. Please re-enter.</span>
                </div>
            </c:if>  
            <c:if test="${addedItem}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>New item &quot;${newItemName}&quot; has successfully been added.</span>
                </div>
            </c:if>
            <c:if test="${calledItem}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Item &quot;${editItemName}&quot; has successfully been called.</span>
                </div>
            </c:if>
            <c:if test="${updatedItem}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Item &quot;${updateItemName}&quot; has successfully been updated.</span>
                </div>
            </c:if>
            <c:if test="${deletedItem}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Item &quot;${deleteItemName}&quot; has successfully been deleted.</span>
                    
                    <form action="inventory" method="POST" class="ms-1 undoForm">
                        <input type="hidden" name="categoryForUndo" value=${itemForUndo.category.categoryId}>
                        <input type="hidden" name="nameForUndo" value=${itemForUndo.itemName}>
                        <input type="hidden" name="priceForUndo" value=${itemForUndo.price}>
                        <input type="submit" value="Undo">
                        <input type="hidden" name ="action" value="undo">
                    </form>
                </div>
            </c:if>
            <c:if test="${undoDeleting}">
                <div class="mt-3 mx-2 p-2 notice">
                    <span>Item &quot;${undoItemName}&quot; has been successfully restored.</span>
                </div>
            </c:if>
            
            <div class="row ps-1">
                <div class="col-md-5 mx-auto mt-3">
                    <h3>Add Item</h3>

                    <form action="inventory" method="POST">
                        <div class="row mt-3 mb-1">
                            <label class="form_label1" for="newCategory">Category:</label>
                            <select class="form-select" id="newCategory" name="newCategory">
                                <option value="0">---------- Choose Category ----------</option>
                                <c:forEach items="${categoryies}" var="category">
                                    <option value="${category.categoryId}"
                                            <c:if test="${newCategory == category.categoryId}">selected</c:if>>
                                        ${category.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label1" for="newItem">Name:</label>
                            <input type="text" class="form-control" id="newItem" name="newItem" value="${newItem}">
                        </div>
                        <div class="row mb-3">
                            <label class="form_label1" for="newPrice">Price:</label>
                            <input type="text" class="form-control" id="newPrice" name="newPrice" value="${newPrice}">
                        </div>
                        <div>
                            <input type="submit" value="Add" class="formButton2">
                            <input type="hidden" name ="action" value="addItem">
                        </div>
                    </form>
                </div>

                <div class="col-md-5 mx-auto mt-3">
                    <h3>Edit Item</h3>

                    <form action="inventory" method="POST">
                        <div class="row mt-3 mb-1">
                            <label class="form_label1" for="editCategory">Category:</label>
                            <select class="form-select" id="editCategory" name="editCategory"
                                    <c:if test="${isNotEditing}">disabled</c:if>>
                                <c:forEach items="${categoryies}" var="category">
                                    <option value="${category.categoryId}"
                                            <c:if test="${editCategory == category.categoryId}">selected</c:if>>
                                        ${category.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row mb-1">
                            <label class="form_label1" for="editItem">Name:</label>
                            <input type="text" class="form-control" id="editItem" name="editItem" value="${editItem}"
                                    <c:if test="${isNotEditing}">disabled</c:if>>
                        </div>
                        <div class="row mb-3">
                            <label class="form_label1" for="editPrice">Price:</label>
                            <input type="text" class="form-control" id="editPrice" name="editPrice" value="${editPrice}"
                                    <c:if test="${isNotEditing}">disabled</c:if>>
                        </div>
                        <div>
                            <input type="hidden" name="editItemID" value=${editItemID}>
                            <input type="submit" value="Update" class="formButton2">
                            <input type="hidden" name ="action" value="updateItem">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>