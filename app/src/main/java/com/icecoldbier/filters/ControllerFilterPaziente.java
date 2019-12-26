package com.icecoldbier.filters;

import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.dao.implementations.VisitaBaseDAO;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaBase;
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

@WebFilter(filterName = "ControllerFilterPaziente", urlPatterns = {"/paziente/*"})
public class ControllerFilterPaziente implements Filter {

    private static final float DEFAULT_PAGE_COUNT = 15;
    private VisitaBaseDAO visitaBaseDAO;
    private PazienteDAO pazienteDAO;
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
                long count = visitaBaseDAO.getCount(user.getId());
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
        }else if(userPath.equals("/....")){
            System.out.println("Else");
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("called controller filter init method");
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for pazienti or visitaBase storage system");
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
    }
}
