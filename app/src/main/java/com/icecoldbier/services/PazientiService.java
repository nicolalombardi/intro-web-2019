package com.icecoldbier.services;




import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import com.alibaba.fastjson.JSON;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

import static com.icecoldbier.persistence.entities.User.UserType.medico_base;
import static com.icecoldbier.persistence.entities.User.UserType.medico_specialista;

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
        //Check if the session exists
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("user") == null){
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non sei autorizzato ad effettuare questa richiesta");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //Check if the user is allowed to access this resource
            User user = (User) session.getAttribute("user");
            if(user.getTyp() != medico_specialista && user.getTyp() != medico_base){
                try {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non sei autorizzato ad effettuare questa richiesta");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                //Query the data and return it
                String query = request.getParameter("q");
                if(query == null){
                    return "[]";
                }
                try {
                    ArrayList<Paziente> pazienti = pazienteDAO.searchPazienti(query);
                    return JSON.toJSONString(pazienti);
                } catch (DAOException e) {
                    try {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossible to access the persistence layer: " + e.getMessage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        //Too bad i guess
                    }
                    return null;

                }
            }
        }
        return null;
    }
}