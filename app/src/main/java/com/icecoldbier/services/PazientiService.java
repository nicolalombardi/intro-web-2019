package com.icecoldbier.services;




import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.entities.Paziente;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import com.alibaba.fastjson.JSON;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

@Path("/pazienti-service")
public class PazientiService {

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    private PazienteDAO pazienteDAO;

    @Context
    public void setServletContext(ServletContext servletContext) {
        if (servletContext != null) {
            DAOFactory daoFactory = (DAOFactory) servletContext.getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new RuntimeException(new ServletException("Impossible to get dao factory for storage system"));
            }
            try {
                pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException("Impossible to get dao factory for paziente storage system", ex));
            }
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPazienti() {
        try {
            String query = request.getParameter("query");
            System.out.println(query);
            ArrayList<Paziente> pazienti = pazienteDAO.searchPazienti(query);
            for(Paziente p:pazienti){
                System.out.println(p);
            }
            return JSON.toJSONString(pazienti);
        } catch (DAOException e) {
            try {
                response.sendError(500, "Impossible to access the persistence layer: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
                //Too bad i guess
            }

            return null;

        }
    }
}