package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.*;
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

@WebServlet(name = "MedicoBaseServlet", urlPatterns = {"/medico-base/eroga", "/medico-base/prescrivi-specialistica", "/medico-base/prescrivi-ssp"})
public class MedicoBaseServlet extends HttpServlet {
    private MedicoBaseDAO medicoBaseDAO;
    private PazienteDAO pazienteDAO;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private VisitePossibiliDAO visitePossibiliDAO;
    private VisitaSpecialisticaDAO visitaSpecialisticaDAO;
    private SSPDAO sspDAO;
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
            visitePossibiliDAO = daoFactory.getDAO(VisitePossibiliDAO.class);
            visitaSpecialisticaDAO = daoFactory.getDAO(VisitaSpecialisticaDAO.class);
            sspDAO = daoFactory.getDAO(SSPDAO.class);
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
            String ricetta = request.getParameter("testoRicetta");

            if(idPazienteS == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
            }else{
                int idPaziente = Integer.parseInt(idPazienteS);
                try {
                    Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);

                    //If it's not your patient
                    if(paziente.getMedico().getId() != user.getId()){
                        session.setAttribute("errorMessage", "L'utente selezionato non è un tuo paziente");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/scheda-paziente?id=" + idPaziente));
                    }else{

                        medicoBaseDAO.erogaVisitaBase(
                                user.getId(),
                                idPaziente,
                                ricetta.equals("") ? null : ricetta
                        );
                        session.setAttribute("successMessage", "Visita erogata con successo");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/scheda-paziente?id=" + idPaziente));
                    }
                } catch (DAOException e) {
                    session.setAttribute("errorMessage", "Errore nell'erogare la visita, riprova più tardi");
                    e.printStackTrace();
                }
            }

        }else if(userPath.equals("/medico-base/approva")){
            String idRicettaS = request.getParameter("id_ricetta");

            if(idRicettaS == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id ricetta mancante");
            }else{
                int idRicetta = Integer.parseInt(idRicettaS);
                try {
                    //Controlliamo che la ricetta sia contenuta in una visita prescritta dal medico base loggato
                    VisitaSpecialistica visitaSpecialistica = visitaSpecialisticaDAO.getContainingRicetta(idRicetta);
                    if(!visitaSpecialistica.getMedicoBase().getId().equals(user.getId())){
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La visita specialistica che contiene questa ricetta non è stata prescritta da te");
                    }else{
                        medicoBaseDAO.approvaRicetta(idRicetta);
                        session.setAttribute("successMessage", "Ricetta approvata con successo");
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/lista-visite-specialistiche?id=" + visitaSpecialistica.getPaziente().getId()));
                    }
                } catch (DAOException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore durante l'approvazione della ricetta");
                    e.printStackTrace();
                }
            }

        }else if(userPath.equals("/medico-base/prescrivi-specialistica")){
            String idPazienteS = request.getParameter("idPaziente");
            String visitaSpecialisticaS = request.getParameter("visitaSpecialistica");
            String medicoSpecialistaS = request.getParameter("medicoSpecialista");

            if(idPazienteS == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
            }else{
                int tipoVisitaId, medicoSpecialistaId, idPaziente;

                //Validazione dei dati
                try{
                    tipoVisitaId = Integer.parseInt(visitaSpecialisticaS);
                    medicoSpecialistaId = Integer.parseInt(medicoSpecialistaS);
                    idPaziente = Integer.parseInt(idPazienteS);

                    User medicoSpecialista = medicoSpecialistaDAO.getByPrimaryKey(medicoSpecialistaId);
                    if(medicoSpecialista == null) throw new Exception();

                    VisitaPossibile visitaSpecialistica = visitePossibiliDAO.getByPrimaryKey(tipoVisitaId);
                    if(visitaSpecialistica.getPraticante() != User.UserType.medico_specialista) throw new Exception();


                }catch (Exception e){
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    medicoBaseDAO.prescrizioneEsameMS(tipoVisitaId, medicoSpecialistaId, idPaziente, user.getId());
                    session.setAttribute("successMessage", "Visita prescritta con successo");
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/scheda-paziente?id=" + idPaziente));

                } catch (DAOException e) {
                    session.setAttribute("errorMessage", "Errore nel prescrivere la visita, riprova più tardi");
                    e.printStackTrace();
                }
            }

        }else if(userPath.equals("/medico-base/prescrivi-ssp")){
            String idPazienteS = request.getParameter("idPaziente");
            String esameSpecialisticoS = request.getParameter("esameSpecialistico");
            String sspS = request.getParameter("ssp");

            if(idPazienteS == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
            }else{
                int tipoVisitaId, sspId, idPaziente;

                //Validazione dei dati
                try{
                    tipoVisitaId = Integer.parseInt(esameSpecialisticoS);
                    sspId = Integer.parseInt(sspS);
                    idPaziente = Integer.parseInt(idPazienteS);

                    SSP ssp = sspDAO.getByPrimaryKey(sspId);
                    if(ssp == null) throw new Exception();

                    VisitaPossibile esameSSP = visitePossibiliDAO.getByPrimaryKey(tipoVisitaId);
                    if(esameSSP.getPraticante() != User.UserType.ssp) throw new Exception();


                }catch (Exception e){
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    medicoBaseDAO.prescrizioneEsameSSP(tipoVisitaId, sspId, idPaziente, user.getId());
                    session.setAttribute("successMessage", "Esame prescritto con successo");
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "medico-base/scheda-paziente?id=" + idPaziente));

                } catch (DAOException e) {
                    session.setAttribute("errorMessage", "Errore nel prescrivere l'esame, riprova più tardi");
                    e.printStackTrace();
                }
            }

        }

    }
}
