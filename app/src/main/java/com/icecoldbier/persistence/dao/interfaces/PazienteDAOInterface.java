package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.Visita;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.util.ArrayList;

public interface PazienteDAOInterface extends DAO<Paziente, Integer> {
    ArrayList<Visita> getVisite(Integer id) throws DAOException;
}
