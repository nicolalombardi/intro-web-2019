package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.VisitaSpecialistica;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface VisitaSpecialisticaDAOInterface extends DAO<VisitaSpecialistica, Integer> {
    public Long getCount(int idp) throws DAOException;
}
