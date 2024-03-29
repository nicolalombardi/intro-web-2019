package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.MedicoBaseDAO;
import com.icecoldbier.persistence.dao.implementations.MedicoSpecialistaDAO;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.dao.implementations.VisitaSpecialisticaDAO;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class MedicoSpecialistaController implements Filter {

    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private PazienteDAO pazienteDAO;
    private VisitaSpecialisticaDAO visitaSpecialisticaDAO;
    private MedicoBaseDAO medicoBaseDAO;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        User user = (User)session.getAttribute("user");

        boolean error = false;

        if(userPath.equals("/medico-specialista/lista")){
            ArrayList<Paziente> listaPazienti;
            try{
                String showAllS = request.getParameter("mostraTutti");
                boolean showAll = showAllS == null ? true : Boolean.parseBoolean(showAllS);

                if(showAll){
                    listaPazienti = pazienteDAO.getPazienti();
                }else{
                    listaPazienti = medicoSpecialistaDAO.getListaPazientiAssociati(user.getId());
                }

                request.setAttribute("showAll", showAll);
                request.setAttribute("listaPazientiSpecialista", listaPazienti);
            }catch (DAOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista dei pazienti, riprova più tardi");
                e.printStackTrace();
                return;
            }
        }else if(userPath.equals(("/medico-specialista/visite"))){
            ArrayList<VisitaSpecialistica> visite = null;
            try {
                visite = medicoSpecialistaDAO.getListaVisitePazienti(user.getId());
                request.setAttribute("visite", visite);
            } catch (DAOException ex) {
                ((HttpServletResponse)resp).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
                ex.printStackTrace();
            }
        }else if(userPath.equals("/medico-specialista/visite/dettagli-visita")){
            String idS = request.getParameter("id");
            VisitaSpecialistica visita = null;
            if(idS != null){
                int id;
                try{
                    id = Integer.parseInt(idS);
                }catch (Exception e){
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista delle visite specialistiche, riprova più tardi");
                    return;
                }

                try {
                    visita = visitaSpecialisticaDAO.getByPrimaryKey(id);
                    request.setAttribute("visita", visita);
                } catch (DAOException ex) {
                    ((HttpServletResponse)resp).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }else if(userPath.equals(("/medico-specialista/scheda-paziente"))){
            String idS = request.getParameter("id");
            Paziente paziente = null;
            User medico = null;
            if(idS != null){
                try {
                    int id = Integer.parseInt(idS);
                    paziente = pazienteDAO.getByPrimaryKey(id);
                    medico = medicoBaseDAO.getByPrimaryKey(paziente.getMedico().getId());
                    request.setAttribute("paziente", paziente);
                    request.setAttribute("medico", medico);
                } catch (DAOException ex) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare i dettagli del paziente, riprova più tardi.");
                    ex.printStackTrace();
                }
            }else{
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id non valido o mancante");
            }
        }

        if(!error)
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory ");
        }
        try {
            medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
        try {
            visitaSpecialisticaDAO = daoFactory.getDAO(VisitaSpecialisticaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
        try {
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
    }

}
