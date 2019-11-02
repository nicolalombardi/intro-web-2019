package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaPossibile;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.util.ArrayList;

public interface VisitePossibiliDAOInterface extends DAO<VisitaPossibile, Integer> {
    ArrayList<VisitaPossibile> getVisitePossibili(User.UserType praticante) throws DAOException;
    VisitaPossibile getVisitaFromId(int id) throws DAOException;
}
