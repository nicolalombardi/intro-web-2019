package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.entities.Ricetta;
import com.icecoldbier.persistence.dao.interfaces.RicettaDAOInterface;
import com.icecoldbier.persistence.entities.MedicoSpecialista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RicettaDAO implements RicettaDAOInterface{
    private static final String SELECT_QUERY = "SELECT * FROM ricetta WHERE id = ?";
     
    @Override
    public Ricetta getRicetta(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Ricetta ricetta = null;
        
        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                ricetta = new Ricetta(
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
        
        return ricetta;
    }
    
}
