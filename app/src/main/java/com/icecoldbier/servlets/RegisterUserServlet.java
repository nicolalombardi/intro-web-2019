package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Logger;
import com.icecoldbier.utils.Password;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "RegisterUserServlet", value = "/register")
public class RegisterUserServlet extends HttpServlet {
    private UserDAO userDao;
    private Logger logger;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String typ = request.getParameter("typ");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String provinciaAppartenenza = request.getParameter("provinciaAppartenenza");
        String hashedSaltedPassword = null;
        try {
            hashedSaltedPassword = Password.generatePasswordHash(pass);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            userDao.createUser(User.UserType.valueOf(typ), username, hashedSaltedPassword, nome, cognome, provinciaAppartenenza);
        } catch (DAOException e) {
            throw new ServletException("Error while registering a new user");
        }

    }
}
