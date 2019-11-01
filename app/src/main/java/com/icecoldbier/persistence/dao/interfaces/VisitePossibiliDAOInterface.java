package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaPossibile;

import java.util.ArrayList;

public interface VisitePossibiliDAOInterface {
    ArrayList<VisitaPossibile> getVisitePossibili(User.UserType praticante);
}
