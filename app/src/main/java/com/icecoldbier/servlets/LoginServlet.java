package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.dao.factories.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        userDao = daoFactory.getUserDAO();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User u = userDao.getUser(Integer.parseInt(request.getParameter("id")));
        response.getWriter().println("User ottenuto: " + u.toString());

    }
}
