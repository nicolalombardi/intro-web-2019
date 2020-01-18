package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.*;
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

@WebFilter(filterName = "SSPController", urlPatterns = {"/ssp/*"})
public class SSPController implements Filter {
    private VisitaSSPDAO visitaSSPDAO;


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        final User user = (User)session.getAttribute("user");

        boolean doChain = true;

        if(userPath.equals("/ssp/lista-esami")){
            int idSSP = user.getId();

            try{
                long count = 0;
                count = visitaSSPDAO.getVisiteBySSPCount(idSSP);

                PaginationParameters pageParams = Pagination.getPageParameters(
                        request.getParameter("page"),
                        request.getParameter("pageSize"),
                        count
                );

                ArrayList<VisitaSSP> listaVisite = new ArrayList<>();

                listaVisite = visitaSSPDAO.getVisiteBySSPPaged(idSSP, pageParams);


                request.setAttribute("pageParams", pageParams);
                request.setAttribute("listaVisite", listaVisite);

            }catch (DAOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista delle visite ssp, riprova più tardi");
                doChain = false;
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
            visitaSSPDAO = daoFactory.getDAO(VisitaSSPDAO.class);

        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
