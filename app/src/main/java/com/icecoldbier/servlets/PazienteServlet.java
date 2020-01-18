package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Utils;
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

@WebServlet(name = "PazienteServlet", urlPatterns = {"/paziente/cambia-medico"})


public class PazienteServlet extends HttpServlet {

    private PazienteDAO pazienteDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for paziente or user storage system");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userPath = req.getServletPath();
        HttpSession session =  req.getSession(false);
        User user = (User) session.getAttribute("user");
        String contextPath = Utils.getServletContextPath(req.getServletContext());
        String idp = req.getParameter("idPaziente");
        String idm = req.getParameter("changeMedico");

        if(idp == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
        }
        if(idm == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id medico mancante");
        }

        int idPaziente = Integer.parseInt(idp);
        int idMedico = Integer.parseInt(idm);

        if(idPaziente != user.getId()){
            session.setAttribute("errorMessage", "Non puoi modificare il medico di un altro paziente");
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente(profilo"));
        }else{
            try {
                User p = userDAO.getByPrimaryKey(idPaziente);
                User m = userDAO.getByPrimaryKey(idMedico);
                pazienteDAO.changeMedicoBase(p,m);
                System.out.println("medico cambiato");
                session.setAttribute("successMessage", "Medico cambiato");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/profilo"));
            } catch (DAOException | ProvincieNotMatchingException e) {
                session.setAttribute("errorMessage", "Errore nel cambiare il medico, riprova più tardi");
                e.printStackTrace();
            }
        }

    }
}
