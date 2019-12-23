package com.icecoldbier.filters;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.User.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "MedicoBaseFilter", value = "/medico-base/*")
public class MedicoBaseFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);

        //If the user isn't logged in as a "Medico di base" redirect him to the login page
        HttpServletResponse servletResponse = (HttpServletResponse) resp;
        if (session == null) {
            servletResponse.sendRedirect("/login");
        } else {
            final User user = (User) session.getAttribute("user");
            if (user == null) {
                servletResponse.sendRedirect("/login");
            } else {
                if (user.getTyp() != UserType.medico_base) {
                    //Not a medico di base, invalidate session and redirect to login page
                    session.setAttribute("user", null);
                    session.invalidate();
                    servletResponse.sendRedirect("/login");
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }


}
