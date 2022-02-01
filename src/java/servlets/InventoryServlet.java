package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import services.*;

/**
 * The Servlet for the inventory page.
 * @author Eunji Elly Lee
 * @version Jan 31, 2022
 */
public class InventoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        request.setAttribute("isNotEditing", true);
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");
        
        try {
            InventoryService inventoryService = new InventoryService();
            
            switch(action) {
                case "addItem":
                    int newCategoryID = Integer.parseInt(request.getParameter("newCategory"));
                    String newItem = request.getParameter("newItem");
                    String newPrice = request.getParameter("newPrice");
                    request.setAttribute("newCategory", newCategoryID);        
                    request.setAttribute("newItem", newItem);        
                    request.setAttribute("newPrice", newPrice);    
                    
                    try {
                        double priceNumber = Double.parseDouble(newPrice);
                        
                        if(newCategoryID == 0 || newItem == null || newItem.equals("") ||
                                newPrice == null || newPrice.equals("") || priceNumber < 0) {
                            request.setAttribute("invalidInput", true);       
                        } else {
                            inventoryService.insertItem(email, newCategoryID, newItem, priceNumber);      
                            request.setAttribute("newCategory", 0);
                            request.setAttribute("newItem", "");
                            request.setAttribute("newPrice", "");                            
                            request.setAttribute("newItemName", newItem);                            
                            request.setAttribute("addedItem", true);
                        }
                    } catch(NumberFormatException e) {
                        request.setAttribute("invalidInput", true);
                    }
                    
                    request.setAttribute("isNotEditing", true);
                    break;                
                case "editItem":    
                    int selectedItemId = Integer.parseInt(request.getParameter("selectedItem"));
                    Item seledtedItem = inventoryService.getItem(selectedItemId);
                    
                    request.setAttribute("editItemID", seledtedItem.getItemId());
                    request.setAttribute("editCategory", seledtedItem.getCategory().getCategoryId());        
                    request.setAttribute("editItem", seledtedItem.getItemName());
                    request.setAttribute("editPrice", seledtedItem.getPrice());
                    request.setAttribute("editItemName", seledtedItem.getItemName());                    
                    request.setAttribute("calledItem", true);
                    request.setAttribute("isNotEditing", false);      
                    break;
                case "updateItem":
                    int editItemID = Integer.parseInt(request.getParameter("editItemID"));
                    int editCategoryID = Integer.parseInt(request.getParameter("editCategory"));
                    String editItem = request.getParameter("editItem");
                    String editPrice = request.getParameter("editPrice"); 
                    request.setAttribute("editItemID", editItemID);  
                    request.setAttribute("editCategory", editCategoryID);  
                    request.setAttribute("editItem", editItem);        
                    request.setAttribute("editPrice", editPrice);  
                    
                    try {
                        double priceNumber = Double.parseDouble(editPrice);
                        
                        if(editItem == null || editItem.equals("") ||
                                editPrice == null || editPrice.equals("") || priceNumber < 0) {
                            request.setAttribute("invalidInput", true);   
                            request.setAttribute("isNotEditing", false);
                        } else {  
                            inventoryService.updateItem(editItemID, editCategoryID, editItem, priceNumber);      
                            request.setAttribute("editCategory", 1);
                            request.setAttribute("editItem", "");
                            request.setAttribute("editPrice", "");
                            request.setAttribute("updateItemName", editItem);                            
                            request.setAttribute("updatedItem", true);
                            request.setAttribute("isNotEditing", true);
                        }
                    } catch(NumberFormatException e) {
                        request.setAttribute("invalidInput", true);
                    }
                  
                    break;
                case "deleteItem":
                    int pointedItemID = Integer.parseInt(request.getParameter("pointedItem"));
                    Item deletedItem = inventoryService.getItem(pointedItemID);
                    inventoryService.deleteItem(email, pointedItemID);
                    
                    request.setAttribute("deleteItemName", deletedItem.getItemName());
                    request.setAttribute("itemForUndo", deletedItem);
                    request.setAttribute("deletedItem", true);
                    request.setAttribute("isNotEditing", true);    
                    break;
                case "undo":
                    int undoCategoryID = Integer.parseInt(request.getParameter("categoryForUndo"));
                    String undoItem = request.getParameter("nameForUndo");
                    double undoPrice = Double.parseDouble(request.getParameter("priceForUndo"));
                    inventoryService.insertItem(email, undoCategoryID, undoItem, undoPrice);
                    
                    request.setAttribute("undoItemName", undoItem);
                    request.setAttribute("undoDeleting", true);
                    request.setAttribute("isNotEditing", true);       
                    break;
            }
        } catch(Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request,response);
    }
    
    private void displayPage(HttpServletRequest request, String email)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        InventoryService inventoryService = new InventoryService();
        
        try {
            User user = accountService.getUser(email);
            List<Item> items = user.getItemList();
            List<Category> categoryies = inventoryService.getAllCategories();
            request.setAttribute("user", user);
            request.setAttribute("items", items);
            request.setAttribute("categoryies", categoryies);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}