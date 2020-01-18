package com.icecoldbier.filters;

import com.icecoldbier.persistence.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RedirectFilter", urlPatterns = {"/login"})
public class LoginPageFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);

        //If a user that is already logged in tries to reach the login page, redirect him to the proper home page
        if (session != null) {
            final User user = (User) session.getAttribute("user");
            HttpServletResponse servletResponse = (HttpServletResponse) resp;
            if (user != null) {
                switch (user.getTyp()) {
                    case medico_base:
                        servletResponse.sendRedirect("/medico-base/lista");
                        break;
                    case medico_specialista:
                        servletResponse.sendRedirect("/medico-specialista/lista");
                        break;
                    case paziente:
                        servletResponse.sendRedirect("/paziente/elenco-visite-base");
                        break;
                    case ssp:
                        servletResponse.sendRedirect("/ssp/lista-esami");
                        break;
                    default:
                        //Unexpected user type, invalidate session and continue to login page
                        session.setAttribute("user", null);
                        session.invalidate();
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
