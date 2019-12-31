package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaBaseDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VisitaBaseDAO extends JDBCDAO<VisitaBase, Integer> implements VisitaBaseDAOInterface {
    private static final String GET_BY_ID = "SELECT * FROM visita_base WHERE id = ? ";
    private static final String GET_VISITE_COUNT = "SELECT COUNT(id) as count FROM visita_base v WHERE v.id_paziente = ?";

    private UserDAO userDAO;
    private PazienteDAO pazienteDAO;
    private RicettaDAO ricettaDAO;


    public VisitaBaseDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            ricettaDAO = daoFactory.getDAO(RicettaDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Long getCount(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_VISITE_COUNT))) {
            preparedStatement.setInt(1, idp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visitaBase", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public VisitaBase getByPrimaryKey(Integer primaryKey) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
                Paziente paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
                Ricetta ricetta = ricettaDAO.getByPrimaryKey(resultSet.getInt("id_ricetta"));

                return new VisitaBase(
                        resultSet.getInt("id"),
                        paziente,
                        resultSet.getDate("data_erogazione"),
                        medicoBase,
                        ricetta
                );


            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting visita base", e);
        }
    }

    @Override
    public List<VisitaBase> getAll() throws DAOException {
        return null;
    }
}
