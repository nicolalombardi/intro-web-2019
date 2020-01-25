package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.*;
import com.icecoldbier.utils.Utils;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "PazienteController", urlPatterns = {"/paziente/*"})
public class PazienteController implements Filter {

    private static final float DEFAULT_PAGE_COUNT = 15;
    private VisitaBaseDAO visitaBaseDAO;
    private PazienteDAO pazienteDAO;
    private VisitaSpecialisticaDAO visitaSpecialisticaDAO;
    private RicettaDAO ricettaDAO;
    private VisitaSSPDAO visitaSSPDAO;
    private TicketDAO ticketDAO;
    private VisitePossibiliDAO visitePossibiliDAO;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        User user = (User)session.getAttribute("user");
        Paziente paziente = null;
        try {
            paziente = pazienteDAO.getByPrimaryKey(user.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.setAttribute("paziente",paziente);

        if(userPath.equals("/paziente/elenco-visite-base")){
            ArrayList<VisitaBase> elencoVisite = null;
            try{
                elencoVisite = pazienteDAO.getVisiteBase(user.getId());

                request.setAttribute("elencoVisite", elencoVisite);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/elenco-visite-specialistiche")){
            ArrayList<VisitaSpecialistica> elencoVisite = null;
            try{
                elencoVisite = pazienteDAO.getVisiteSpecialistiche(user.getId());

                request.setAttribute("elencoVisite", elencoVisite);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/elenco-visite-ssp")){
            ArrayList<VisitaSSP> elencoVisiteSSP = null;
            try{
                elencoVisiteSSP = pazienteDAO.getVisiteSSP(user.getId());

                request.setAttribute("elencoVisiteSSP", elencoVisiteSSP);

            }catch (DAOException | DAOFactoryException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if (userPath.equals("/paziente/elenco-prescrizioni-ricette")){
            ArrayList<InfoRicetta> elencoRicette = null;
            try{
                elencoRicette = pazienteDAO.getRicette(user.getId());

                request.setAttribute("elencoRicette", elencoRicette);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/elenco-prescrizioni-visite")){
            ArrayList<VisitaSpecialisticaOrSSP> elencoVisiteFuture = null;
            try{
                elencoVisiteFuture = pazienteDAO.getVisiteFuture(user.getId());

                request.setAttribute("elencoVisiteFuture", elencoVisiteFuture);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/tickets")){
            ArrayList<Ticket> listaTickets = null;

            try{
                listaTickets = pazienteDAO.getTicketsPaged(user.getId());

                request.setAttribute("listaTickets", listaTickets);
                request.setAttribute("idPaziente", user.getId());

            }catch (DAOException e) {
                e.printStackTrace();
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
            }
        }else if(userPath.equals("/paziente/esami-possibili")){

            ArrayList<VisitaPossibile> listaVisite = null;

            try{

                listaVisite = visitePossibiliDAO.getVisitePossibili(User.UserType.medico_specialista);
                listaVisite.addAll(visitePossibiliDAO.getVisitePossibili(User.UserType.ssp));

                request.setAttribute("listaVisite", listaVisite);
            }catch (DAOException e) {
                e.printStackTrace();
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
            }
        }else if(userPath.equals("/paziente/profilo")){
            try {
                ArrayList<User> listaMediciSceglibili = pazienteDAO.getAllMediciBase(paziente.getId());
                request.setAttribute("listaMediciSceglibili", listaMediciSceglibili);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory storage system");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for pazienti storage system");
        }
        try {
            visitaBaseDAO = daoFactory.getDAO(VisitaBaseDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for visitaBase storage system");
        }
        try {
            visitaSpecialisticaDAO = daoFactory.getDAO(VisitaSpecialisticaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for visitaSpecialistica storage system");
        }
        try {
            visitaSSPDAO = daoFactory.getDAO(VisitaSSPDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for visitaSSP storage system");
        }
        try {
            ricettaDAO = daoFactory.getDAO(RicettaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for ricetta storage system");
        }
        try {
            ticketDAO = daoFactory.getDAO(TicketDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for ticket storage system");
        }
        try {
            visitePossibiliDAO = daoFactory.getDAO(VisitePossibiliDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for visita possibile storage system");
        }
    }
}
