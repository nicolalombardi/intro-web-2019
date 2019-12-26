package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaSpecialisticaDAOInterface;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VisitaSpecialisticaDAO extends JDBCDAO<VisitaSpecialistica, Integer> implements VisitaSpecialisticaDAOInterface {
    private static final String GET_BY_ID = "SELECT * FROM visita_specialistica WHERE id = ?";
    private static final String GET_VISITE_COUNT = "SELECT COUNT(id) as count FROM visita_specialistica v WHERE v.id_paziente = ?";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public VisitaSpecialisticaDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
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
    public VisitaSpecialistica getByPrimaryKey(Integer primaryKey) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return new VisitaSpecialistica(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_visita"),
                        resultSet.getBoolean("erogata"),
                        resultSet.getDate("data_prescrizione"),
                        resultSet.getDate("data_erogazione"),
                        resultSet.getInt("id_medico"),
                        resultSet.getInt("id_paziente"),
                        resultSet.getInt("id_report")
                );
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting visita specialistica", e);
        }
    }

    @Override
    public List<VisitaSpecialistica> getAll() throws DAOException {
        return null;
    }
}