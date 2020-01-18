package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.*;
import com.icecoldbier.utils.Utils;
import com.icecoldbier.utils.pagination.Pagination;
import com.icecoldbier.utils.pagination.PaginationParameters;
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

        if(userPath.equals("/paziente/elenco-visite-base")){
            ArrayList<VisitaBase> elencoVisite = null;
            try{
                long count = visitaBaseDAO.getByPazienteCount(user.getId());
                int pagesCount = (int)Math.ceil(count/DEFAULT_PAGE_COUNT);
                int requestedPage = 1;

                //Grab the requested page value if i exist, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }

                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);

                elencoVisite = pazienteDAO.getVisiteBase(user.getId(), (int)DEFAULT_PAGE_COUNT, requestedPage);

                request.setAttribute("page", requestedPage);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("elencoVisite", elencoVisite);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/elenco-visite-specialistiche")){
            ArrayList<VisitaSpecialistica> elencoVisite = null;
            try{
                long count = visitaSpecialisticaDAO.getCount(user.getId());
                int pagesCount = (int)Math.ceil(count/DEFAULT_PAGE_COUNT);
                int requestedPage = 1;

                //Grab the requested page value if i exist, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }
                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);

                elencoVisite = pazienteDAO.getVisiteSpecialistiche(user.getId(), (int)DEFAULT_PAGE_COUNT, requestedPage);

                request.setAttribute("page", requestedPage);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("elencoVisite", elencoVisite);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if (userPath.equals("/paziente/elenco-prescrizioni-ricette")){
            ArrayList<Ricetta> elencoRicette = null;
            try{
                long count = ricettaDAO.getCount(user.getId());
                int pagesCount = (int)Math.ceil(count/DEFAULT_PAGE_COUNT);
                int requestedPage = 1;

                //Grab the requested page value if i exist, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }
                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);

                elencoRicette = pazienteDAO.getRicette(user.getId(), (int)DEFAULT_PAGE_COUNT, requestedPage);

                request.setAttribute("page", requestedPage);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("elencoRicette", elencoRicette);

            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/elenco-prescrizioni-visite")){
            ArrayList<VisitaSpecialistica> elencoVisiteSpecialistiche = null;
            ArrayList<VisitaSSP> elencoVisiteSSP = null;
            try{
                long count = visitaSpecialisticaDAO.getCount(user.getId()) + visitaSSPDAO.getVisiteByPazienteCount(user.getId());
                int pagesCount = (int)Math.ceil(count/DEFAULT_PAGE_COUNT);
                int requestedPage = 1;

                //Grab the requested page value if i exist, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }
                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);
                //TODO: paginazione vera
                elencoVisiteSpecialistiche = pazienteDAO.getVisiteSpecialisticheFuture(user.getId(), (int)DEFAULT_PAGE_COUNT, requestedPage);
                elencoVisiteSSP = pazienteDAO.getVisiteSSPFuture(user.getId(), (int)DEFAULT_PAGE_COUNT, requestedPage);

                request.setAttribute("page", requestedPage);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("elencoVisiteSpecialistiche", elencoVisiteSpecialistiche);
                request.setAttribute("elencoVisiteSSP", elencoVisiteSSP);

            }catch (DAOException | DAOFactoryException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/paziente/tickets")){
            ArrayList<Ticket> listaTickets = null;

            try{
                long count = ticketDAO.getCount(user.getId());
                int pagesCount = (int)Math.ceil(count/DEFAULT_PAGE_COUNT);
                int requestedPage = 1;

                //Grab the requested page value if i exist, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }
                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);
                listaTickets = pazienteDAO.getTicketsPaged(user.getId(), (int)DEFAULT_PAGE_COUNT, requestedPage);
                request.setAttribute("page", requestedPage);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("listaTickets", listaTickets);
                request.setAttribute("idPaziente", user.getId());

            }catch (DAOException e) {
                e.printStackTrace();
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
            }
        }else if(userPath.equals("/paziente/esami-possibili")){

            ArrayList<VisitaPossibile> listaVisite = null;

            try{
                long count = 20;
                String requestedPage = request.getParameter("page");

                listaVisite = visitePossibiliDAO.getVisitePossibili(User.UserType.medico_specialista);
                listaVisite.addAll(visitePossibiliDAO.getVisitePossibili(User.UserType.ssp));



                PaginationParameters pageParams = Pagination.getPageParameters(requestedPage, "15", count);
                int startIndex = (pageParams.getPage() -1) *15;
                List visite = listaVisite.subList(startIndex,Math.min(listaVisite.size() - 1, startIndex + 15));

                request.setAttribute("page", pageParams.getPage());
                request.setAttribute("pagesCount", pageParams.getPagesCount());
                request.setAttribute("listaVisite", visite);
            }catch (DAOException e) {
                e.printStackTrace();
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
            }
        }else if(userPath.equals("/paziente/profilo")){
            Paziente paziente = null;
            try {
                paziente = pazienteDAO.getByPrimaryKey(user.getId());
                ArrayList<User> listaMediciSceglibili = pazienteDAO.getAllMediciBase(paziente.getId());
                request.setAttribute("paziente",paziente);
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
