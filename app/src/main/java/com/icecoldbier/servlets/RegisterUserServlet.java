package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.factories.DAOFactory;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Password;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request.getServletContext().getAttribute("daoFactory");

        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String typ = request.getParameter("typ");

        String hashedSaltedPassword = null;
        try {
            hashedSaltedPassword = Password.generatePasswordHash(pass);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        User newUser = daoFactory.getUserDAO().createUser(typ, username, hashedSaltedPassword);
        response.getWriter().println(newUser.toString());

    }
}
