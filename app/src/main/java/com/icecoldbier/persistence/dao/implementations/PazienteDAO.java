package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.PazienteDAOInterface;
import com.icecoldbier.persistence.entities.Paziente;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PazienteDAO extends JDBCDAO<Paziente, Integer> implements PazienteDAOInterface {
    private static final String GET_PAZIENTE_BY_ID = "SELECT * FROM paziente WHERE id_user = ?";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public PazienteDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public Paziente getByPrimaryKey(Integer primaryKey) throws DAOException {
        return null;
    }

    @Override
    public List<Paziente> getAll() throws DAOException {
        return null;
    }

    @Override
    public Paziente getPaziente(Integer id) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_PAZIENTE_BY_ID)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return new Paziente(
                        resultSet.getInt("id_user"),
                        resultSet.getDate("data_nascita"),
                        resultSet.getString("luogo_nascita"),
                        resultSet.getString("codice_fiscale"),
                        resultSet.getString("sesso").charAt(0),
                        resultSet.getString("foto"),
                        resultSet.getInt("id_medico"),
                        resultSet.getString("email")

                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting paziente", e);
        }
    }
}
