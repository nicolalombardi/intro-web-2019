package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaBaseDAOInterface;
import com.icecoldbier.persistence.entities.VisitaBase;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VisitaBaseDAO extends JDBCDAO<VisitaBase, Integer> implements VisitaBaseDAOInterface {
    private static final String GET_BY_ID = "SELECT * FROM visita_base WHERE id = ? ";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public VisitaBaseDAO(Connection con) {
        super(con);
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
                return new VisitaBase(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_medico"),
                        resultSet.getInt("id_paziente"),
                        resultSet.getDate("data_erogazione")
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
