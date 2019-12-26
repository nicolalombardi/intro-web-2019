package com.icecoldbier.filters;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.User.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "MedicoSpecialistaFilter", urlPatterns = {"/medico-specialista/*"})
public class MedicoSpecialistaFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        //If the user isn't logged in as a "Medico specialista" redirect him to the login page
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        if (session == null) {
            servletResponse.sendRedirect("/login");
        } else {
            final User user = (User) session.getAttribute("user");
            if (user == null) {
                servletResponse.sendRedirect("/login");
            } else {
                if (user.getTyp() != UserType.medico_specialista) {
                    //Not a medico specialista, invalidate session and redirect to login page
                    session.setAttribute("user", null);
                    session.invalidate();
                    servletResponse.sendRedirect("/login");
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}
