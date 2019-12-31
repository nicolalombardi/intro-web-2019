package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.MedicoBaseDAO;
import com.icecoldbier.persistence.dao.implementations.MedicoSpecialistaDAO;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.dao.implementations.VisitaSpecialisticaDAO;
import com.icecoldbier.persistence.entities.Report;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;
import com.icecoldbier.utils.Utils;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "MedicoSpecialistaServlet", urlPatterns = {"/medico-specialista/visite/eroga"})
public class MedicoSpecialistaServlet extends HttpServlet {
    private MedicoBaseDAO medicoBaseDAO;
    private PazienteDAO pazienteDAO;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private VisitaSpecialisticaDAO visitaSpecialisticaDAO;
    
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
            visitaSpecialisticaDAO = daoFactory.getDAO(VisitaSpecialisticaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for medico base storage system");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String userPath = request.getServletPath();
        HttpSession session =  request.getSession(false);
        User user = (User) session.getAttribute("user");
        String contextPath = Utils.getServletContextPath(request.getServletContext());
        
        if(userPath.equals("/medico-specialista/visite/eroga")){
            String idPaziente = request.getParameter("idPaziente");
            String idMedicoSpecialista = request.getParameter("idMedicoSpecialista");
            String idMedicoBase = request.getParameter("idMedicoBase");
            String idVisita = request.getParameter("idVisita");
            String textReport = request.getParameter("textReport");
            if(idPaziente != null && idMedicoSpecialista!= null && idMedicoBase!=null && idVisita!=null && textReport!=null){
                int idPaz = Integer.parseInt(idPaziente);
                int idVis = Integer.parseInt(idVisita);
                int idMedBas = Integer.parseInt(idMedicoBase);
                int idMedSpec = Integer.parseInt(idMedicoSpecialista);
                VisitaSpecialistica visita = null;
                Report report = new Report(textReport);
                try {
                    medicoSpecialistaDAO.erogaVisita(idVis, idPaz, idMedBas, report);
                    visita = visitaSpecialisticaDAO.getByPrimaryKey(idVis);
                    session.setAttribute("successMessage", "Visita erogata con successo");
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-specialista/visite/dettagli-visita?id=" + idVisita));
                } catch (DAOException ex) {
                    session.setAttribute("errorMessage", "Errore nell'erogare la visita, riprova più tardi");
                    ex.printStackTrace();
                }
            }else{
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id mancanti");
            }
        }
    }
    

}
