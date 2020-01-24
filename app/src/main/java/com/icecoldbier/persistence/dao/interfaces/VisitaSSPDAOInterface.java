package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.VisitaSSP;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.util.ArrayList;

public interface VisitaSSPDAOInterface extends DAO<VisitaSSP, Integer> {
    public Long getVisiteByPazienteCount(int idp) throws DAOException;
    ArrayList<VisitaSSP> getVisiteBySSP(int idSSP) throws DAOException;
    Long getVisiteBySSPCount(int idSSP) throws DAOException;
    Long getCountVisiteFutureByPaziente(int idp) throws DAOException;
}
