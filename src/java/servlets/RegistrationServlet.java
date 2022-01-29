package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import services.AccountService;

/**
 * The Servlet for the registration page.
 * @author Eunji Elly Lee
 * @version Jan 28, 2022
 */
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("uuid") != null) {
            request.setAttribute("uuid", request.getParameter("uuid")); 
            getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request,response);
        } else {
            AccountService accountService = new AccountService();
        
            try {
                List<Family> families = accountService.getAllFamilies();
                request.setAttribute("families", families);
            } catch(Exception ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        String action = request.getParameter("action");
        
        if(action != null && action.equals("register")) {
            String newEmail = request.getParameter("newEmail");
            String newPassword = request.getParameter("newPassword");
            String newFirstName = request.getParameter("newFirstName");
            String newLastName = request.getParameter("newLastName");
            int newFamilyID = Integer.parseInt(request.getParameter("newFamily"));
            request.setAttribute("newEmail", newEmail);
            request.setAttribute("newPassword", newPassword);  
            request.setAttribute("newFirstName", newFirstName);        
            request.setAttribute("newLastName", newLastName);
            request.setAttribute("newFamilyID", newFamilyID);
                    
            if(newEmail == null || newEmail.equals("") || newPassword == null || newPassword.equals("") ||
                    newFirstName == null || newFirstName.equals("") || newLastName == null ||
                    newLastName.equals("") || newFamilyID == 0) {
                request.setAttribute("invalidInput", true);
            } else {
                try {
                    List<User> users = accountService.getAllUsers();                    
                    boolean foundEmail = false;
                      
                    for(int i = 0; i < users.size(); i++) {
                        if(users.get(i).getEmail().equals(newEmail)) {
                            foundEmail = true;
                            break;
                        }
                    }
                    
                    if(foundEmail) {
                        request.setAttribute("failedAdd", true);
                    } else {
                        String path = getServletContext().getRealPath("/WEB-INF");
                        String url = request.getRequestURL().toString();
                        accountService.registerAccount(newEmail, newFirstName, newLastName, newPassword,
                                newFamilyID, 2, path, url);
                        
                        request.setAttribute("newEmail", "");
                        request.setAttribute("newPassword", "");  
                        request.setAttribute("newFirstName", "");        
                        request.setAttribute("newLastName", "");
                        request.setAttribute("newFamilyID", 0);                          
                        request.setAttribute("newUserName", newFirstName);
                        request.setAttribute("addedUser", true);
                    }
                } catch(Exception ex) {
                    Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                List<Family> families = accountService.getAllFamilies();
                request.setAttribute("families", families);
            } catch(Exception ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request,response);
        } else if(action != null && action.equals("activate")) {
            String uuid = request.getParameter("uuid");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            request.setAttribute("email", email);
            request.setAttribute("password", password);  
                    
            if(email == null || email.equals("") || password == null || password.equals("")) {
                request.setAttribute("invalidInput", true);
            } else {
                String path = getServletContext().getRealPath("/WEB-INF");
                String url = request.getRequestURL().toString();
                        
                if(accountService.activateAccount(uuid, email, password, path, url)) {
                    request.setAttribute("email", "");
                    request.setAttribute("password", "");  
                    request.setAttribute("activated", true);
                } else {
                    request.setAttribute("failedActivation", true);
                }
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request,response);
        }
    }
}