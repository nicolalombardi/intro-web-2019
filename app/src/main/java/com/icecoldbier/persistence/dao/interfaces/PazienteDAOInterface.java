package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;

import java.util.ArrayList;

public interface PazienteDAOInterface extends DAO<Paziente, Integer> {
    Long getAssociatiCount(int idMedico) throws DAOException;
    ArrayList<Paziente> getPazientiPaged(int pageSize, int page) throws DAOException;
    ArrayList<Paziente> getPazientiAssociatiPaged(int idMedico, int pageSize, int page) throws DAOException;
    ArrayList<Paziente> searchPazienti(String query) throws DAOException;
    ArrayList<VisitaBase> getVisiteBase(Integer id, int pageSize, int page) throws DAOException, DAOFactoryException;
    ArrayList<VisitaSpecialistica> getVisiteSpecialistiche(Integer id, int pageSize, int page) throws DAOException, DAOFactoryException;
    ArrayList<Ricetta> getRicette(Integer id, int pageSize, int page) throws DAOException;
    void changeProfilePicture(Integer pazienteId, String newPath) throws DAOException;
    void changeMedicoBase(User paziente, User newMedicoBase) throws DAOException, ProvincieNotMatchingException;
    ArrayList<Ticket> getTickets(Integer pazienteId) throws DAOException;
}
