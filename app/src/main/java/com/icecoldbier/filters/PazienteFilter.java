package com.icecoldbier.filters;


import com.icecoldbier.persistence.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "PazienteFilter", urlPatterns = {"/paziente/*"})

public class PazienteFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);

        boolean doChain = true;

        //If the user isn't logged in as a "Paziente" redirect him to the login page
        HttpServletResponse servletResponse = (HttpServletResponse) resp;
        if (session == null) {
            servletResponse.sendRedirect("/login");
            doChain = false;
        } else {
            final User user = (User) session.getAttribute("user");
            if (user == null) {
                servletResponse.sendRedirect("/login");
                doChain = false;
            } else {
                if (user.getTyp() != User.UserType.paziente) {
                    //Not a paziente, invalidate session and redirect to login page
                    session.setAttribute("user", null);
                    session.invalidate();
                    servletResponse.sendRedirect("/login");
                    doChain = false;
                }
            }
        }
        if(doChain)
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
