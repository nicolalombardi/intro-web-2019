package com.icecoldbier.filters;

import com.icecoldbier.persistence.dao.implementations.MedicoBaseDAO;
import com.icecoldbier.persistence.dao.implementations.MedicoSpecialistaDAO;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
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

@WebFilter(filterName = "ControllerFilter", urlPatterns = {"/medico-base/*","/medico-specialista/*"})
public class ControllerFilter implements Filter {
    private static final int DEFAULT_PAGE_SIZE = 15;
    private static final int MAX_PAGE_SIZE = 30;
    private static final int MIN_PAGE_SIZE = 10;

    private PazienteDAO pazienteDAO;
    private MedicoBaseDAO medicoBaseDAO;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        User user = (User)session.getAttribute("user");

        System.out.println(request.getServletPath());

        boolean error = false;

        if(userPath.equals("/medico-base/lista")){
            ArrayList<Paziente> listaPazienti;
            try{
                long count = pazienteDAO.getCount();
                int requestedPage = 1;
                int requestedPageSize = DEFAULT_PAGE_SIZE;

                //Grab the requested page size value if it exists, set a default value (1) if it does not
                if(request.getParameter("pageSize") != null){
                    requestedPageSize = Integer.parseInt(request.getParameter("pageSize"));
                }

                requestedPageSize = Utils.coerceInt(MIN_PAGE_SIZE, MAX_PAGE_SIZE, requestedPageSize);

                int pagesCount = (int)Math.ceil(((count * 1.0f)/ requestedPageSize));

                //Grab the requested page value if it exists, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }
                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);

                listaPazienti = pazienteDAO.getPazientiPaged(requestedPageSize, requestedPage);

                request.setAttribute("selectedPage", requestedPage);
                request.setAttribute("pageSize", requestedPageSize);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("listaPazienti", listaPazienti);
            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/medico-base/ricerca")){

        }else if(userPath.equals("/medico-base/profilo")){

        }else if(userPath.equals("/medico-base/scheda-paziente")){
            String idS = request.getParameter("id");
            if(idS != null){
                int id = Integer.parseInt(idS);
                try {
                    Paziente p = pazienteDAO.getByPrimaryKey(id);
                    request.setAttribute("paziente", p);
                    try {
                        User medicoBase = medicoBaseDAO.getByPrimaryKey(p.getIdMedico());
                        System.out.println(" medico " + medicoBase);
                        request.setAttribute("medico", medicoBase);
                    } catch (DAOException e) {
                        e.printStackTrace();
                        request.setAttribute("medico", null);
                    }
                } catch (DAOException e) {
                    error = true;
                    ((HttpServletResponse)resp).sendError(404, e.getMessage());
                }
            }

        }
        
        if(userPath.equals("/medico-specialista/lista")){
            ArrayList<Paziente> listaPazientiSpecialista = null;
            try {
                System.out.println(user.getId() + " " + user.getNome() + " " + user.getCognome());
                listaPazientiSpecialista = medicoSpecialistaDAO.getListaPazientiAssociati(user.getId());
                request.setAttribute("listaPazientiSpecialista", listaPazientiSpecialista);
            } catch (DAOException ex) {
                error = true;
                ((HttpServletResponse)resp).sendError(500, ex.getMessage());
                ex.printStackTrace();
            }
        }

        if(!error)
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("called controller filter init method");
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory ");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
            medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
    }

}
