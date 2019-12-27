package com.icecoldbier.servlets;


import com.icecoldbier.persistence.dao.implementations.ReportDAO;
import com.icecoldbier.persistence.entities.Report;
import com.icecoldbier.utils.Utils;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReportServlet", value = "/report")
public class ReportServlet extends HttpServlet {
    ReportDAO reportDAO;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            reportDAO = daoFactory.getDAO(ReportDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for report storage system");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idReport"));
        String contextPath = Utils.getServletContextPath(getServletContext());
        try {
            Report r = reportDAO.getByPrimaryKey(id);
            if (r == null) {
                resp.sendRedirect(resp.encodeRedirectURL(contextPath));
                System.out.println("Incorrect id report.");
            } else {
                req.getSession().setAttribute("report", r);
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/elenco-visite-specialistiche/report"));
                System.out.println("Redirecting to report jsp");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
