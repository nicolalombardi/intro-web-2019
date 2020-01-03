package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.VisitaSSP;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface VisitaSSPDAOInterface extends DAO<VisitaSSP, Integer> {
    public Long getCount(int idp) throws DAOException;
}
