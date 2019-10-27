package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.dao.interfaces.PazienteDAOInterface;
import com.icecoldbier.persistence.entities.Paziente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PazienteDAO implements PazienteDAOInterface {
    private static final String SELECT_QUERY = "SELECT * FROM paziente WHERE id_user = ?";
    @Override
    public Paziente getPaziente(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Paziente paziente = null;
        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                paziente = new Paziente(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7).charAt(0),
                        resultSet.getString(8),
                        resultSet.getInt(9),
                        resultSet.getString(10),
                        resultSet.getString(11)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return paziente;
    }
}
