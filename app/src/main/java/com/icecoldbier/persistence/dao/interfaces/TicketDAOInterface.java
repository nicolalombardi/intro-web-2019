package com.icecoldbier.persistence.dao.interfaces;

import com.icecoldbier.persistence.entities.Ticket;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;

public interface TicketDAOInterface extends DAO<Ticket, Integer> {
    public Long getCount(int idp) throws DAOException;
}
