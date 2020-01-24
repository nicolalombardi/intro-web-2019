package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.VisitaBase;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.util.ArrayList;

public interface VisitaBaseDAOInterface extends DAO<VisitaBase, Integer> {
    ArrayList<VisitaBase> getByMedico(int medicoId) throws DAOException;
    ArrayList<VisitaBase> getByPaziente(int pazienteId) throws DAOException;
    ArrayList<VisitaBase> getByMedicoAndPaziente(int idMedico, int idPaziente) throws DAOException;
    Long getByMedicoCount(int idMedico) throws DAOException;
    Long getByPazienteCount(int idPaziente) throws DAOException;
    Long getByMedicoAndPazienteCount(int idMedico, int idPaziente) throws DAOException;
    VisitaBase getContainingRicetta(int idRicetta) throws DAOException;

}
