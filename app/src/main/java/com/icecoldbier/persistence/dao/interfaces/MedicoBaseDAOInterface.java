package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaBase;

import java.sql.Date;
import java.util.ArrayList;

public interface MedicoBaseDAOInterface {
    public ArrayList<VisitaBase> getListaPazienti(int id);
    public void createVisitaBase(int idm, int idp, Date data);

}