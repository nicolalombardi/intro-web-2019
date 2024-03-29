package com.icecoldbier.filters.controllers;

import com.icecoldbier.persistence.dao.implementations.*;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class MedicoBaseController implements Filter {
    private PazienteDAO pazienteDAO;
    private MedicoBaseDAO medicoBaseDAO;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private SSPDAO sspDAO;
    private VisitePossibiliDAO visitePossibiliDAO;
    private VisitaBaseDAO visitaBaseDAO;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String userPath = request.getServletPath();
        final User user = (User)session.getAttribute("user");

        boolean doChain = true;

        if(userPath.equals("/medico-base/lista")){
            ArrayList<Paziente> listaPazienti;
            try{
                String showAllS = request.getParameter("mostraTutti");
                boolean showAll = showAllS == null ? true : Boolean.parseBoolean(showAllS);

                if(showAll){
                    listaPazienti = pazienteDAO.getPazienti();
                }else{
                    listaPazienti = pazienteDAO.getPazientiAssociati(user.getId());
                }

                request.setAttribute("showAll", showAll);
                request.setAttribute("listaPazienti", listaPazienti);
            }catch (DAOException e) {
                doChain = false;
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista dei pazienti, riprova più tardi");
                e.printStackTrace();
            }
        }else if(userPath.equals("/medico-base/lista-visite-base")){
            int idPaziente = -1, idMedico = user.getId();

            String idPazienteS;
            idPazienteS = request.getParameter("id_paziente");

            try {
                if(idPazienteS != null && !idPazienteS.equals("")){
                    idPaziente = Integer.parseInt(idPazienteS);
                }

            }catch (Exception e){
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista delle visite base, riprova più tardi");
                return;
            }
            try{
                ArrayList<VisitaBase> listaVisite;

                if(idPaziente == -1){
                    listaVisite = visitaBaseDAO.getByMedico(idMedico);
                }else {
                    Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);
                    //Se il paziente non è tuo
                    if(!paziente.getMedico().getId().equals(user.getId())){
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Il paziente non è tuo");
                    }
                    request.setAttribute("paziente", paziente);
                    listaVisite = visitaBaseDAO.getByMedicoAndPaziente(idMedico, idPaziente);
                }

                request.setAttribute("listaVisite", listaVisite);

            }catch (DAOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista delle visite base, riprova più tardi");
            }

        }else if(userPath.equals("/medico-base/lista-visite-specialistiche")){
            int idPaziente = -1, idMedico = user.getId();

            String idPazienteS;
            idPazienteS = request.getParameter("id_paziente");

            try {
                if(idPazienteS != null && !idPazienteS.equals("")){
                    idPaziente = Integer.parseInt(idPazienteS);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                ArrayList<VisitaSpecialisticaOrSSP> listaVisite = new ArrayList<>();


                if(idPaziente == -1){
                    listaVisite = medicoBaseDAO.getVisiteEsamiByMedico(idMedico);
                }else {
                    Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);
                    //Se il paziente non è tuo
                    if(!paziente.getMedico().getId().equals(user.getId())){
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Il paziente non è tuo");
                    }
                    request.setAttribute("paziente", paziente);
                    listaVisite =  medicoBaseDAO.getVisiteEsamiByMedicoAndPaziente(idMedico, idPaziente);
                }

                request.setAttribute("listaVisite", listaVisite);

            }catch (DAOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è stato possibile recuperare la lista delle visite specialistiche, riprova più tardi");
            }

        }
        else if(userPath.equals("/medico-base/scheda-paziente")){
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
            visitaBaseDAO = daoFactory.getDAO(VisitaBaseDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
