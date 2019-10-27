package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;

public interface UserDAOInterface {
    User getUserById(Integer id);
    User getUserByUsernameAndPassword(String username, String password);
    User createUser(String typ, String username, String password);
}
