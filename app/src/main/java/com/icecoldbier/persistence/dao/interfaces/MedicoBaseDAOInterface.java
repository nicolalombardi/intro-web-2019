package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.sql.Date;
import java.util.ArrayList;

public interface MedicoBaseDAOInterface extends DAO<User, Integer> {
    void erogaVisitaBase(int idm, int idp, String ricetta) throws DAOException;
    void prescrizioneEsameSSP(int idTipoVisita, int idSSP, int idPaziente, int idMedicoBase) throws DAOException;
    void prescrizioneEsameMS(int idTipoVisita, int idMedicoSpecialista, int idPaziente, int idMedicoBase) throws DAOException;
    InfoVisita getInfoVisita(int idv) throws DAOException;
}