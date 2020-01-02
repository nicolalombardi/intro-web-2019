package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.Ricetta;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface RicettaDAOInterface extends DAO<Ricetta, Integer> {
    public Long getCount(int idp) throws DAOException;
}
