package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.util.ArrayList;

public interface PazienteDAOInterface extends DAO<Paziente, Integer> {
    ArrayList<Paziente> getPazientiPaged(int pageSize, int page) throws DAOException;;
    ArrayList<Visita> getVisite(Integer id) throws DAOException;
    ArrayList<Ricetta> getRicette(Integer id) throws DAOException;
    void changeProfilePicture(Integer pazienteId, String newPath) throws DAOException;
    void changeMedicoBase(User paziente, User newMedicoBase) throws DAOException, ProvincieNotMatchingException;
    ArrayList<Ticket> getTickets(Integer pazienteId) throws DAOException;
}
