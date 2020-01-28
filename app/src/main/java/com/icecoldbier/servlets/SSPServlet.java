package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.*;
import com.icecoldbier.utils.Utils;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SSPServlet", urlPatterns = {"/ssp/eroga"})
public class SSPServlet extends HttpServlet {
    private SSPDAO sspDAO;
    private VisitaSSPDAO visitaSSPDAO;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            sspDAO = daoFactory.getDAO(SSPDAO.class);
            visitaSSPDAO = daoFactory.getDAO(VisitaSSPDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for medico base storage system");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userPath = request.getServletPath();
        HttpSession session =  request.getSession(false);
        User user = (User) session.getAttribute("user");
        String contextPath = Utils.getServletContextPath(request.getServletContext());


        if(userPath.equals("/ssp/eroga")){

            String idVisitaSSPS = request.getParameter("idVisitaSSP");
            System.out.println(idVisitaSSPS);

            if(idVisitaSSPS == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id ssp mancante");
            }else{
                int idVisitaSSP = Integer.parseInt(idVisitaSSPS);
                try {
                    VisitaSSP visitaSSP = visitaSSPDAO.getByPrimaryKey(idVisitaSSP);

                    //Se non è stata assegnata a te (ssp)
                    if(!visitaSSP.getSsp().getId().equals(user.getId())){
                        session.setAttribute("errorMessage", "La visita selezionata non è stata assegnata a questa SSP");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "ssp/lista-esami"));
                    }else{
                        sspDAO.erogaVisitaPrescritta(idVisitaSSP);

                        session.setAttribute("successMessage", "Visita erogata con successo");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "ssp/lista-esami"));
                    }
                } catch (DAOException e) {
                    session.setAttribute("errorMessage", "Errore nell'erogare la visita, riprova più tardi");
                    e.printStackTrace();
                }
            }
        }

    }
}
