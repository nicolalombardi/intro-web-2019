package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;

public interface UserDAOInterface {
    User getUser(Integer id);
    User createUser(String typ, String username, String password);
}
