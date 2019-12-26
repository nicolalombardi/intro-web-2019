package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.VisitaBase;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface VisitaBaseDAOInterface extends DAO<VisitaBase, Integer> {
    public Long getCount(int idp) throws DAOException;
}
