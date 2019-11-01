package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.SSP;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface SSPDAOInterface extends DAO<SSP, Integer> {
    void erogaVisitaPrescritta(int id) throws DAOException;

}
