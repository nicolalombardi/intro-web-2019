package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.TicketDAOInterface;
import com.icecoldbier.persistence.entities.Ticket;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TicketDAO extends JDBCDAO<Ticket, Integer> implements TicketDAOInterface {

    private static final String GET_TICKETS_COUNT = "SELECT count(*) AS count FROM (SELECT visita_specialistica.id AS id FROM visita_specialistica LEFT JOIN elenco_visite_possibili ON visita_specialistica.id_visita = elenco_visite_possibili.id WHERE id_paziente = ? AND data_erogazione IS NOT NULL UNION SELECT visita_ssp.id AS id FROM visita_ssp LEFT JOIN elenco_visite_possibili ON visita_ssp.id_visita = elenco_visite_possibili.id WHERE id_paziente = ? AND data_erogazione IS NOT NULL) AS tickets;";

    public TicketDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public Ticket getByPrimaryKey(Integer primaryKey) throws DAOException {
        return null;
    }

    @Override
    public List<Ticket> getAll() throws DAOException {
        return null;
    }

    @Override
    public Long getCount(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_TICKETS_COUNT))){
            preparedStatement.setInt(1,idp);
            preparedStatement.setInt(2,idp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Impossible to get the count of Ricetta", ex);
        }
    }
}
