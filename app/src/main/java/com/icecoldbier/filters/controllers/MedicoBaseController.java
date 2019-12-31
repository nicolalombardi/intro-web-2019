package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.SSP;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaPossibile;
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

@WebFilter(filterName = "MedicoBaseController", urlPatterns = {"/medico-base/*"})
public class MedicoBaseController implements Filter {
    private static final int DEFAULT_PAGE_SIZE = 15;
    private static final int MAX_PAGE_SIZE = 30;
    private static final int MIN_PAGE_SIZE = 10;

    private PazienteDAO pazienteDAO;
    private MedicoBaseDAO medicoBaseDAO;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private SSPDAO sspDAO;
    private VisitePossibiliDAO visitePossibiliDAO;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        User user = (User)session.getAttribute("user");

        boolean doChain = true;

        if(userPath.equals("/medico-base/lista")){
            ArrayList<Paziente> listaPazienti;
            try{
                int requestedPage = 1;
                int requestedPageSize = DEFAULT_PAGE_SIZE;

                String showAllS = request.getParameter("mostraTutti");

                boolean showAll = showAllS == null ? true : Boolean.parseBoolean(showAllS);

                //Grab the requested page size value if it exists, set a default value (1) if it does not
                if(request.getParameter("pageSize") != null && !request.getParameter("pageSize").equals("")){
                    requestedPageSize = Integer.parseInt(request.getParameter("pageSize"));
                }

                requestedPageSize = Utils.coerceInt(MIN_PAGE_SIZE, MAX_PAGE_SIZE, requestedPageSize);


                //Grab the requested page value if it exists, set a default value (1) if it does not
                if(request.getParameter("page") != null && !request.getParameter("page").equals("")){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }

                long count = showAll ? pazienteDAO.getCount() : pazienteDAO.getAssociatiCount(user.getId());

                int pagesCount = (int)Math.ceil(((count * 1.0f)/ requestedPageSize));
                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);

                if(showAll){
                    listaPazienti = pazienteDAO.getPazientiPaged(requestedPageSize, requestedPage);
                }else{
                    listaPazienti = pazienteDAO.getPazientiAssociatiPaged(user.getId(), requestedPageSize, requestedPage);
                }

                request.setAttribute("selectedPage", requestedPage);
                request.setAttribute("pageSize", requestedPageSize);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("showAll", showAll);
                request.setAttribute("listaPazienti", listaPazienti);
            }catch (DAOException e) {
                doChain = false;
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista dei pazienti, riprova più tardi");
                e.printStackTrace();
            }
        }else if(userPath.equals("/medico-base/profilo")){

        }else if(userPath.equals("/medico-base/scheda-paziente")){
            String idS = request.getParameter("id");
            if(idS != null){
                int id = Integer.parseInt(idS);
                try {
                    Paziente p = pazienteDAO.getByPrimaryKey(id);
                    request.setAttribute("paziente", p);

                    //Visite specialistiche possibili
                    ArrayList<VisitaPossibile> visitePossibili = visitePossibiliDAO.getVisitePossibili(User.UserType.medico_specialista);
                    request.setAttribute("visitePossibili", visitePossibili);

                    //Esami ssp possibili
                    ArrayList<VisitaPossibile> esamiPossibili = visitePossibiliDAO.getVisitePossibili(User.UserType.ssp);
                    request.setAttribute("esamiPossibili", esamiPossibili);


                    ArrayList<User> mediciSpecialisti = (ArrayList<User>) medicoSpecialistaDAO.getAll();
                    request.setAttribute("mediciSpecialisti", mediciSpecialisti);

                    ArrayList<SSP> ssp = (ArrayList<SSP>) sspDAO.getAll();
                    request.setAttribute("ssp", ssp);
                    try {
                        User medicoBase = medicoBaseDAO.getByPrimaryKey(p.getMedico().getId());
                        request.setAttribute("medico", medicoBase);
                    } catch (DAOException e) {
                        e.printStackTrace();
                        request.setAttribute("medico", null);
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                    doChain = false;
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare i dettagli del paziente, riprova più tardi");
                }
            }

        }


        if(doChain)
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory ");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
            medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
            sspDAO = daoFactory.getDAO(SSPDAO.class);
            visitePossibiliDAO = daoFactory.getDAO(VisitePossibiliDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
