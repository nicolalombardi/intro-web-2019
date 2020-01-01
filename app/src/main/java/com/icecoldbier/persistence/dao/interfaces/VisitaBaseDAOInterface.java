package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.VisitaBase;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.util.ArrayList;

public interface VisitaBaseDAOInterface extends DAO<VisitaBase, Integer> {
    ArrayList<VisitaBase> getByMedicoPaged(int medicoId, int pageSize, int page) throws DAOException;
    ArrayList<VisitaBase> getByPazientePaged(int pazienteId, int pageSize, int page) throws DAOException;
    ArrayList<VisitaBase> getByMedicoAndPazientePaged(int idMedico, int idPaziente, int pageSize, int page) throws DAOException;
    Long getByMedicoCount(int idMedico) throws DAOException;
    Long getByPazienteCount(int idPaziente) throws DAOException;
    Long getByMedicoAndPazienteCount(int idMedico, int idPaziente) throws DAOException;
}
