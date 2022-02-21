package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.User;
import services.AccountService;
import services.InventoryService;

/**
 * The Servlet for the managing category page.
 * @author Eunji Elly Lee
 * @version Feb 12, 2022
 */
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        request.setAttribute("isNotEditing", true);
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request,response);
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
                case "addCategory":
                    String newCategoryName = request.getParameter("newCategoryName");
                    request.setAttribute("newCategoryName", newCategoryName);
                    
                    if(newCategoryName == null || newCategoryName.equals("")) {
                        request.setAttribute("invalidInput", true);       
                    } else {
                        inventoryService.insertCategory(newCategoryName);
                        request.setAttribute("newCategoryName", "");                     
                        request.setAttribute("addCategoryName", newCategoryName);                            
                        request.setAttribute("addedCategory", true);
                    }
                    
                    request.setAttribute("isNotEditing", true);
                    break;                
                case "editCategory":    
                    int selectedCategoryId = Integer.parseInt(request.getParameter("selectedCategory"));
                    Category seledtedCategory = inventoryService.getCategory(selectedCategoryId);
                    
                    request.setAttribute("editCategoryID", seledtedCategory.getCategoryId());
                    request.setAttribute("editCategoryName", seledtedCategory.getCategoryName());                    
                    request.setAttribute("calledCategory", true);
                    request.setAttribute("isNotEditing", false);      
                    break;
                case "updateCategory":
                    int editCategoryID = Integer.parseInt(request.getParameter("editCategoryID"));
                    String editCategoryName = request.getParameter("editCategoryName");
                    request.setAttribute("editCategoryID", editCategoryID);  
                    request.setAttribute("editCategoryName", editCategoryName);
                    
                    if(editCategoryName == null || editCategoryName.equals("")) {
                            request.setAttribute("invalidInput", true);   
                            request.setAttribute("isNotEditing", false);
                        } else {  
                            inventoryService.updateCategory(editCategoryID, editCategoryName);
                            request.setAttribute("editCategoryName", "");
                            request.setAttribute("updateCategoryName", editCategoryName);                            
                            request.setAttribute("updatedCategory", true);
                            request.setAttribute("isNotEditing", true);
                        }
                  
                    break;
            }
        } catch(Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request,response);
    }
    
    private void displayPage(HttpServletRequest request, String email)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        InventoryService inventoryService = new InventoryService();
        
        try {
            User admin = accountService.getUser(email);
            List<Category> categories = inventoryService.getAllCategories();
            request.setAttribute("admin", admin);
            request.setAttribute("categories", categories);
        } catch(Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}