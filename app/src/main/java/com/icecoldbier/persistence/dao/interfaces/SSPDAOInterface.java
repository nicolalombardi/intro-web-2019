package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.InfoRicetta;
import com.icecoldbier.persistence.entities.SSP;
import com.icecoldbier.persistence.entities.VisitaSSP;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public interface SSPDAOInterface extends DAO<SSP, Integer> {
    void erogaVisitaPrescritta(int id) throws DAOException;
    ArrayList<InfoRicetta> getListaRicette(Date data, SSP ssp) throws DAOException;
    ArrayList<VisitaSSP> getListaVisite(Date data, SSP ssp) throws DAOException;
}
