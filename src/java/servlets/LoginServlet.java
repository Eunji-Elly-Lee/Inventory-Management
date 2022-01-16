package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.User;
import services.AccountService;

/**
 * The Servlet for the login page.
 * @author Eunji Elly Lee
 * @version Jan 15, 2022
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
        if(email == null || email.equals("")) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
        } else {
            if(request.getParameter("logout") != null) {
                session.invalidate();
                session = request.getSession();
                request.setAttribute("loggedOut", true);
                
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
                return;
            }
            
            try {
                AccountService accountService = new AccountService();
                User user = accountService.getUser(email);
                
                if(user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 3) {
                    response.sendRedirect("admin");
                } else {
                    response.sendRedirect("inventory");
                }
            } catch(Exception ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");        
        request.setAttribute("email", email);
        
        AccountService accountService = new AccountService();
        User user = accountService.login(email, password);
        
        if(user == null) {
            request.setAttribute("invalidLogin", true);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            return;
        }
        
        if(!user.getActive()) {
            request.setAttribute("nonActiveUser", true);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            return;
        }

        if(user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 3) {
            session.setAttribute("email", email);
            response.sendRedirect("admin");
        } else {
            session.setAttribute("email", email);
            response.sendRedirect("inventory");
        }
    }
}