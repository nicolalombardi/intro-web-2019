package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;

import java.util.ArrayList;

public interface PazienteDAOInterface extends DAO<Paziente, Integer> {
    Long getAssociatiCount(int idMedico) throws DAOException;
    ArrayList<Paziente> getPazienti() throws DAOException;
    ArrayList<Paziente> getPazientiAssociati(int idMedico) throws DAOException;
    ArrayList<Paziente> searchPazienti(String query) throws DAOException;
    ArrayList<VisitaBase> getVisiteBase(Integer id) throws DAOException, DAOFactoryException;
    ArrayList<VisitaSpecialistica> getVisiteSpecialistiche(Integer id) throws DAOException, DAOFactoryException;
    ArrayList<VisitaSSP> getVisiteSSP(Integer id) throws DAOException, DAOFactoryException;
    ArrayList<InfoRicetta> getRicette(Integer id) throws DAOException;
    void changeProfilePicture(Integer pazienteId, String newPath) throws DAOException;
    void changeMedicoBase(User paziente, User newMedicoBase) throws DAOException, ProvincieNotMatchingException;
    ArrayList<Ticket> getTicketsPaged(Integer pazienteId) throws DAOException;
    ArrayList<Ticket> getTickets(Integer pazienteId) throws DAOException;
    ArrayList<User> getAllMediciBase(int idp) throws DAOException;
    ArrayList<VisitaSpecialisticaOrSSP> getVisiteFuture(Integer id) throws DAOException;
}
