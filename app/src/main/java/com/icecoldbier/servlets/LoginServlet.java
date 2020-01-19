package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Logger;
import com.icecoldbier.utils.Utils;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dologin")
public class LoginServlet extends HttpServlet {
    private UserDAO userDao;
    private Logger logger;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        logger = Logger.getLogger(this.getClass().getName());
        logger.print("Login servlet initialized.");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputEmail = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");
        String rememberMeS = request.getParameter("rememberMe");

        boolean rememberMe = rememberMeS == null ? false : rememberMeS.equals("on");

        String contextPath = Utils.getServletContextPath(getServletContext());

        User u = null;
        try {
            u = userDao.getUserByUsernameAndPassword(inputEmail, inputPassword);

            if (u == null) {
                response.sendRedirect(response.encodeRedirectURL(contextPath + "login?error=true&error_message=Incorrect email or password"));
                logger.print("Incorrect user or password.");
            } else {
                request.getSession().setAttribute("user", u);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "login"));
                logger.print("User " + u.getUsername() + " successfully logged in.");
            }
        } catch (DAOException e) {
            response.sendRedirect(response.encodeRedirectURL(contextPath + "login?error=true&error_message=Incorrect email or password"));
            logger.print(e.getMessage());
        }


    }
}
