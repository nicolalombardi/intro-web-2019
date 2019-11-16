package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Controller",
        loadOnStartup = 1,
        urlPatterns = {"/setLocale"})
public class ControllerServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession(true);

        if(userPath.equals("/setLocale")){
            String locale = request.getParameter("locale");
            ArrayList<String> supportedLocale = new ArrayList<>();
            supportedLocale.add("it");
            supportedLocale.add("en");

            if(!supportedLocale.contains(locale)){
                response.sendRedirect("/login.html");
            }else{
                User user = (User) session.getAttribute("user");
                if(user != null){
                    try {
                        userDAO.setUserLocale(user.getId(), locale);
                    } catch (DAOException e) {
                        throw new ServletException(e.getMessage());
                    }
                }
                session.setAttribute("locale", locale);

                String userView = (String) session.getAttribute("view");

                System.out.println(userView);
                if (userView != null) {
                    response.sendRedirect(userView);
                } else {
                    response.sendRedirect("/index.html");
                }
            }
        }
    }
}
