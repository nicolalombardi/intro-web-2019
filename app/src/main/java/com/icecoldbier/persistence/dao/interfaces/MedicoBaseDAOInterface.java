package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaBase;
import com.icecoldbier.persistence.entities.VisitaPossibile;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.sql.Date;
import java.util.ArrayList;

public interface MedicoBaseDAOInterface extends DAO<User, Integer> {
    public ArrayList<VisitaBase> getListaPazienti(int id);
    public void createVisitaBase(int idm, int idp, Date data) throws DAOException;
    public void prescrizioneEsameSSP(VisitaBase visitaBase, int idSSP, VisitaPossibile vis, Date dataPrescrizione) throws DAOException;
    public void prescrizioneEsameMS(VisitaBase visitaBase, int idMS, VisitaPossibile vis, Date dataPrescrizione) throws DAOException;
}