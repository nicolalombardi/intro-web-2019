package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.entities.VisitaBase;
import com.icecoldbier.persistence.dao.interfaces.VisitaBaseDAOInterface;
import com.icecoldbier.persistence.entities.Ricetta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitaBaseDAO implements VisitaBaseDAOInterface{
    private static final String SELECT_QUERY = "SELECT * FROM visita_base WHERE id = ? ";
    
    @Override
    public VisitaBase getVisitaBase(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        VisitaBase visitaBase = null;
        
        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                visitaBase = new VisitaBase(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3)
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
        
        return visitaBase;
    }
    
}
