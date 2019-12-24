package com.icecoldbier.filters;

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

@WebFilter(filterName = "ControllerFilter", urlPatterns = {"/medico-base/*"})
public class ControllerFilter implements Filter {
    private static final float DEFAULT_PAGE_COUNT = 15;
    private PazienteDAO pazienteDAO;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        User user = (User)session.getAttribute("user");

        System.out.println(request.getServletPath());

        if(userPath.equals("/medico-base/lista")){
            ArrayList<Paziente> listaPazienti = null;
            try{
                long count = pazienteDAO.getCount();
                int pagesCount = (int)Math.ceil(count/DEFAULT_PAGE_COUNT);
                int requestedPage = 1;

                //Grab the requested page value if i exist, set a default value (1) if it does not
                if(request.getParameter("page") != null){
                    requestedPage = Integer.parseInt(request.getParameter("page"));
                }

                requestedPage = Utils.coerceInt(1, pagesCount, requestedPage);

                listaPazienti = pazienteDAO.getPazientiPaged((int)DEFAULT_PAGE_COUNT, requestedPage);

                request.setAttribute("page", requestedPage);
                request.setAttribute("pagesCount", pagesCount);
                request.setAttribute("listaPazienti", listaPazienti);
            }catch (DAOException e) {
                ((HttpServletResponse)resp).sendError(500, e.getMessage());
                e.printStackTrace();
            }
        }else if(userPath.equals("/medico-base/ricerca")){

        }else if(userPath.equals("/medico-base/profilo")){

        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("called controller filter init method");
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for pazienti storage system");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for pazienti storage system");
        }
    }

}