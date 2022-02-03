package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.User;
import services.AccountService;

/**
 * The Servlet for the account page.
 * @author Eunji Elly Lee
 * @version Feb 2, 2022
 */
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
        displayPage(request, email);
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String userPassword = request.getParameter("userPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName"); 
        String active = request.getParameter("isActive");
        boolean isActive = false;
        
        if(active != null) {
            isActive = true;
            request.setAttribute("isActive", true);
        } else {
            request.setAttribute("isActive", false);
        }
        
        try {
            AccountService accountService = new AccountService();
            User user = accountService.getUser(email);
            
            if(userPassword == null || userPassword.equals("") || firstName == null || firstName.equals("") ||
                    lastName == null || lastName.equals("")) {
                request.setAttribute("user", user);
                request.setAttribute("userPassword", userPassword); 
                request.setAttribute("firstName", firstName); 
                request.setAttribute("lastName", lastName);
                request.setAttribute("invalidInput", true); 
            } else {
                accountService.updateUser(user.getEmail(), isActive, firstName, lastName, userPassword,
                        user.getFamily().getFamilyId(), user.getRole().getRoleId());
                
                if(!isActive) {
                    request.setAttribute("logout", "");
                    request.setAttribute("deactivated", true);
                    
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
                    return;
                }
                
                displayPage(request, email);
                request.setAttribute("updatedUser", true);
            }                        
        } catch(Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request,response);
    }
    
    private void displayPage(HttpServletRequest request, String email)
            throws ServletException, IOException {
        try {
            AccountService accountService = new AccountService();
            User user = accountService.getUser(email);
            
            request.setAttribute("user", user);
            request.setAttribute("userPassword", user.getPassword());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            
            if(user.getActive()) {
                request.setAttribute("isActive", true);
            }
        } catch(Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}