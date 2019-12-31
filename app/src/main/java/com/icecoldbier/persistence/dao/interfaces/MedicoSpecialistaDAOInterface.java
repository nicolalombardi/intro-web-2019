package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.Report;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import java.util.ArrayList;

public interface MedicoSpecialistaDAOInterface extends DAO<User, Integer> {
    public ArrayList <Paziente> getListaPazientiAssociati(int id) throws DAOException;
    public ArrayList <VisitaSpecialistica> getListaVisitePazienti(int id) throws DAOException;
    public void erogaVisita(int idVisita,int idPaziente,int idMedicoBAse,Report report) throws DAOException;
    public void erogaVisitaConRicetta(int idv, Report report) throws DAOException;
}
