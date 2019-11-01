package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.Paziente;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface PazienteDAOInterface extends DAO<Paziente, Integer> {
    Paziente getPaziente(Integer id) throws DAOException;
}
