package com.icecoldbier.servlets;


import com.icecoldbier.persistence.dao.implementations.RicettaDAO;
import com.icecoldbier.persistence.entities.Ricetta;
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

@WebServlet(name = "RicettaServlet", value = "/ricetta")
public class RicettaServlet extends HttpServlet {
    RicettaDAO ricettaDAO;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            ricettaDAO = daoFactory.getDAO(RicettaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for ricetta storage system");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idRicetta"));
        String contextPath = Utils.getServletContextPath(getServletContext());
        try {
            Ricetta r = ricettaDAO.getByPrimaryKey(id);
            if (r == null) {
                resp.sendRedirect(resp.encodeRedirectURL(contextPath));
                System.out.println("Incorrect id ricetta.");
            } else {
                req.getSession().setAttribute("ricetta", r);
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/elenco-visite-specialistiche/report/ricetta"));
                System.out.println("Redirecting to ricetta jsp");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


}
