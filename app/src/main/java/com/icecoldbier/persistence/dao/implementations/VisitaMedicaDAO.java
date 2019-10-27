package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.dao.interfaces.VisitaMedicaDAOInterface;
import com.icecoldbier.persistence.entities.VisitaMedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitaMedicaDAO implements VisitaMedicaDAOInterface {
    private static final String SELECT_QUERY = "SELECT * FROM visita_medica WHERE id = ?";
    @Override
    public VisitaMedica getVisitaMedica(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        VisitaMedica visitaMedica = null;

        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                visitaMedica = new VisitaMedica(
                        resultSet.getInt(1),
                        resultSet.getString(2)
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

        return visitaMedica;
    }
}
