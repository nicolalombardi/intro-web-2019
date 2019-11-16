package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface UserDAOInterface extends DAO<User, Integer> {
    User getUserByUsernameAndPassword(String username, String password) throws DAOException;
    void createUser(User.UserType typ, String username, String password, String nome, String cognome, String provinciaAppartenenza) throws DAOException;
}
