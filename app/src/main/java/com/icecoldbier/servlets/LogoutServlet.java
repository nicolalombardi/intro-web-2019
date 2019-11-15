package com.icecoldbier.servlets;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Logger;
import com.icecoldbier.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    private Logger logger;

    @Override
    public void init() throws ServletException {
        super.init();
        logger = Logger.getLogger(LogoutServlet.class.getName());

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                session.setAttribute("user", null);
                session.invalidate();
                logger.print("Session invalidated for user " + user.getUsername() + ".");
                user = null;
            }
        }else{
            logger.print("No session found.");
        }

        String contextPath = Utils.getServletContextPath(getServletContext());

        if (!response.isCommitted()) {
            response.sendRedirect(response.encodeRedirectURL(contextPath + "login.jsp"));
        }
    }
}
