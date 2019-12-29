package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.MedicoBaseDAO;
import com.icecoldbier.persistence.dao.implementations.MedicoSpecialistaDAO;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;
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

@WebFilter(filterName = "MedicoSpecialistaController", urlPatterns = {"/medico-specialista/*"})
public class MedicoSpecialistaController implements Filter {

    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        User user = (User)session.getAttribute("user");

        boolean error = false;

        if(userPath.equals("/medico-specialista/lista")){
            ArrayList<Paziente> listaPazientiSpecialista = null;
            try {
                listaPazientiSpecialista = medicoSpecialistaDAO.getListaPazientiAssociati(user.getId());
                request.setAttribute("listaPazientiSpecialista", listaPazientiSpecialista);
            } catch (DAOException ex) {
                error = true;
                ((HttpServletResponse)resp).sendError(500, ex.getMessage());
                ex.printStackTrace();
            }
        }else if(userPath.equals(("/medico-specialista/visite"))){
            ArrayList<VisitaSpecialistica> visite = null;
            try {
                visite = medicoSpecialistaDAO.getListaVisitePazienti(user.getId());
                request.setAttribute("visite", visite);
            } catch (DAOException ex) {
                ((HttpServletResponse)resp).sendError(500, ex.getMessage());
                ex.printStackTrace();
            }
        }else if(userPath.equals(("/medico-specialista/scheda-paziente?id=*"))){
            System.out.println("Arrivato qua");
            System.out.println(request.getParameter("id"));
        }else if(userPath.equals(("/medico-specialista/scheda-paziente"))){
            System.out.println("Sono quiiiii");
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
            medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
    }

}
