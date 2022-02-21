package filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The java file of a filter layer for managing all authentication.
 * @author Eunji Elly Lee
 * @version Feb 21, 2022
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String email = (String) session.getAttribute("email");
        
        if(email == null) {            
            httpResponse.sendRedirect("login");
            return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
}
