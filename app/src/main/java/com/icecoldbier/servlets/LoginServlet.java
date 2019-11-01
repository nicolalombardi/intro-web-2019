package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.dao.factories.DAOFactory;
import com.icecoldbier.utils.Logger;
import com.icecoldbier.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDao;
    private static Logger logger;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        userDao = daoFactory.getUserDAO();
        logger = Logger.getLogger(this.getClass().getName());
        logger.print("Login servlet initialized.");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String contextPath = Utils.getServletContextPath(getServletContext());

        User u = userDao.getUserByUsernameAndPassword(username, password);
        if(u == null){
            response.sendRedirect(response.encodeRedirectURL(contextPath + "login.html"));
            logger.print("Incorrect user or password.");
        }else{
            request.getSession().setAttribute("user", u);
            response.sendRedirect(response.encodeRedirectURL(contextPath + "dummy_restricted.html"));
            logger.print("User " + u.getUsername() + " successfully logged in.");
        }


    }
}
