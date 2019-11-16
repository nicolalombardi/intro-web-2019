package com.icecoldbier.filters;

import com.icecoldbier.persistence.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "LocaleFilter",
        value = "/*"
)
public class LocaleFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        User user;
        HttpSession session = ((HttpServletRequest) request).getSession(true);

        user = (User) session.getAttribute("user");

        if(user != null){
            if (user.getLocale() != null) {
                session.setAttribute("locale", user.getLocale());
            }
        }

        if(session.getAttribute("locale") == null){
            session.setAttribute("locale", "en");
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
