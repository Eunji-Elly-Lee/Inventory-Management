package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import services.AccountService;

/**
 * The Servlet for the reset password page.
 * @author Eunji Elly Lee
 * @version Jan 20, 2022
 */
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("uuid") != null) {
            request.setAttribute("uuid", request.getParameter("uuid")); 
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request,response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        String action = request.getParameter("action");
        
        if(action != null && action.equals("submitEmail")) {
            String email = request.getParameter("email");
            
            if(email == null || email.equals("")) {
                request.setAttribute("invalidInput", true);            
            } else {                
                String path = getServletContext().getRealPath("/WEB-INF");
                String url = request.getRequestURL().toString();
                accountService.resetPassword(email, path, url);            
                request.setAttribute("sentEmail", true);
            }
                        
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        } else if(action != null && action.equals("submitPassword")) {
            String uuid = request.getParameter("uuid");
            String password = request.getParameter("password");
            
            if(accountService.changePassword(uuid, password)) {
                request.setAttribute("setNewPassword", true);
            } else {
                request.setAttribute("failedResetPassword", true);
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }
    }
}