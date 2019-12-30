package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.MedicoBaseDAO;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.entities.Paziente;
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

@WebServlet(name = "MedicoBaseServlet", urlPatterns = {"/medico-base/eroga"})
public class MedicoBaseServlet extends HttpServlet {
    private MedicoBaseDAO medicoBaseDAO;
    private PazienteDAO pazienteDAO;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for medico base storage system");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userPath = request.getServletPath();
        HttpSession session =  request.getSession(false);
        User user = (User) session.getAttribute("user");
        String contextPath = Utils.getServletContextPath(request.getServletContext());


        if(userPath.equals("/medico-base/eroga")){

            String idPazienteS = request.getParameter("idPaziente");

            if(idPazienteS == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
            }else{
                int idPaziente = Integer.parseInt(idPazienteS);
                try {
                    Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);

                    //If it's not you patient
                    if(paziente.getMedico().getId() != user.getId()){
                        session.setAttribute("errorMessage", "L'utente selezionato non è un tuo paziente");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/scheda-paziente?id=" + idPaziente));
                    }else{
                        medicoBaseDAO.createVisitaBase(user.getId(), idPaziente);
                        session.setAttribute("successMessage", "Visita erogata con successo");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/scheda-paziente?id=" + idPaziente));
                    }
                } catch (DAOException e) {
                    session.setAttribute("errorMessage", "Errore nell'erogare la visita, riprova più tardi");
                    e.printStackTrace();
                }
            }

        }

    }
}
