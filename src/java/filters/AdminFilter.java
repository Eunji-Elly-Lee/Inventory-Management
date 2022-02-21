package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.*;
import models.User;

/**
 * The java file of a filter layer for managing the authentication of an administrator.
 * @author Eunji Elly Lee
 * @version Feb 21, 2022
 */
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String email = (String) session.getAttribute("email");
        
        try {
            UserDB userDB = new UserDB();
            User user = userDB.get(email);
            
            if(user.getRole().getRoleId() == 2) {
                httpResponse.sendRedirect("inventory");
                return;
            }
        } catch(Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
}