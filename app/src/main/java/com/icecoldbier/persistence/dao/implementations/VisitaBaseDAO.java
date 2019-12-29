package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaBaseDAOInterface;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaBase;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.List;

public class VisitaBaseDAO extends JDBCDAO<VisitaBase, Integer> implements VisitaBaseDAOInterface {
    private static final String GET_BY_ID = "SELECT * FROM visita_base WHERE id = ? ";
    private static final String GET_VISITE_COUNT = "SELECT COUNT(id) as count FROM visita_base v WHERE v.id_paziente = ?";

    private UserDAO userDAO;
    private PazienteDAO pazienteDAO;


    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public VisitaBaseDAO(Connection con) {
        super(con);
        try {
            userDAO = this.getDAO(UserDAO.class);
            pazienteDAO = this.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Long getCount(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_VISITE_COUNT))){
            preparedStatement.setInt(1,idp);
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
        User medicoBase;
        Paziente paziente;
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
                paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
                return new VisitaBase(
                        resultSet.getInt("id"),
                        paziente,
                        resultSet.getDate("data_erogazione"),
                        medicoBase
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
