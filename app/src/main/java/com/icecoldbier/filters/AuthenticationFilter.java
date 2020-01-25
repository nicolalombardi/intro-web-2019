package com.icecoldbier.filters;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.User.UserType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class AuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        HttpServletRequest request = (HttpServletRequest) req;

        boolean authenticated = false;
        String basePath = request.getServletPath().split("/")[1];

        System.out.println("Called authentication filter");
        HttpServletResponse servletResponse = (HttpServletResponse) resp;
        if (session != null) {
            final User user = (User) session.getAttribute("user");
            if (user != null){
                switch (basePath) {
                    case "medico-base":
                        System.out.println("User is authenticated as a medico di base");
                        authenticated = user.getTyp() == UserType.medico_base;
                        break;
                    case "medico-specialista":
                        System.out.println("User is authenticated as a medico specialista");
                        authenticated = user.getTyp() == UserType.medico_specialista;
                        break;
                    case "ssp":
                        System.out.println("User is authenticated as a ssp");
                        authenticated = user.getTyp() == UserType.ssp;
                        break;
                    case "paziente":
                        System.out.println("User is authenticated as a paziente");
                        authenticated = user.getTyp() == UserType.paziente;
                        break;
                    case "profile":
                        System.out.println("User is authenticated as a user");
                        authenticated = true;
                        break;
                    case "rest":
                        System.out.println("User is authenticated as a medico di base or medico specialista");
                        authenticated = user.getTyp() == UserType.medico_base || user.getTyp() == UserType.medico_specialista;
                        break;
                }
            }
        }

        if(authenticated){
            chain.doFilter(req, resp);
        }else{
            servletResponse.sendRedirect("/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }


}
