package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import services.AccountService;

/**
 * The Servlet for the managing family page.
 * @author Eunji Elly Lee
 * @version Feb 12, 2022
 */
public class FamilyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        request.setAttribute("isNotEditing", true);
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/family.jsp").forward(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");
        
        try {
            AccountService accountService = new AccountService();
            
            switch(action) {
                case "addFamily":
                    String newFamilyName = request.getParameter("newFamilyName");
                    request.setAttribute("newFamilyName", newFamilyName);
                    
                    if(newFamilyName == null || newFamilyName.equals("")) {
                        request.setAttribute("invalidInput", true);       
                    } else {
                        accountService.insertFamily(newFamilyName);
                        request.setAttribute("newFamilyName", "");                     
                        request.setAttribute("addFamilyName", newFamilyName);                            
                        request.setAttribute("addedFamily", true);
                    }
                    
                    request.setAttribute("isNotEditing", true);
                    break;                
                case "editFamily":    
                    int selectedFamilyId = Integer.parseInt(request.getParameter("selectedFamily"));
                    Family seledtedFamily = accountService.getFamily(selectedFamilyId);
                    request.setAttribute("editFamilyID", seledtedFamily.getFamilyId());
                    request.setAttribute("editFamilyName", seledtedFamily.getFamilyName());                    
                    request.setAttribute("calledFamily", true);
                    request.setAttribute("isNotEditing", false);      
                    break;
                case "updateFamily":
                    int editFamilyID = Integer.parseInt(request.getParameter("editFamilyID"));
                    String editFamilyName = request.getParameter("editFamilyName");
                    request.setAttribute("editFamilyID", editFamilyID);  
                    request.setAttribute("editFamilyName", editFamilyName);
                    
                    if(editFamilyName == null || editFamilyName.equals("")) {
                            request.setAttribute("invalidInput", true);   
                            request.setAttribute("isNotEditing", false);
                        } else {  
                            accountService.updateFamily(editFamilyID, editFamilyName);
                            request.setAttribute("editFamilyName", "");
                            request.setAttribute("updateFamilyName", editFamilyName);                            
                            request.setAttribute("updatedFamily", true);
                            request.setAttribute("isNotEditing", true);
                        }
                  
                    break;
            }
        } catch(Exception ex) {
            Logger.getLogger(FamilyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/family.jsp").forward(request,response);
    }
    
    private void displayPage(HttpServletRequest request, String email)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        
        try {
            User admin = accountService.getUser(email);
            List<Family> families = accountService.getAllFamilies();
            request.setAttribute("admin", admin);
            request.setAttribute("families", families);
        } catch(Exception ex) {
            Logger.getLogger(FamilyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}