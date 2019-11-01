package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;

public interface UserDAOInterface {
    User getUserById(Integer id);
    User getUserByUsernameAndPassword(String username, String password);
    User createUser(User.UserType typ, String username, String password, String nome, String cognome, String provinciaAppartenenza);
}
