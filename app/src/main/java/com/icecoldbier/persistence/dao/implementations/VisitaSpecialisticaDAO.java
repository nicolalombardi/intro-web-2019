package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.dao.interfaces.VisitaSpecialisticaDAOInterface;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitaSpecialisticaDAO implements VisitaSpecialisticaDAOInterface {
    private static final String SELECT_QUERY = "SELECT * FROM visita_specialistica WHERE id_report = ?";
    @Override
    public VisitaSpecialistica getVisitaSpecialistica(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        VisitaSpecialistica visitaSpecialistica = null;

        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                visitaSpecialistica = new VisitaSpecialistica(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4)
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

        return visitaSpecialistica;
    }
}
