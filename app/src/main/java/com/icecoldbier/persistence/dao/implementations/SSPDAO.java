package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.entities.SSP;
import com.icecoldbier.persistence.dao.interfaces.SSPDAOInterface;
import com.icecoldbier.persistence.entities.SSP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SSPDAO implements SSPDAOInterface{
    private static final String SELECT_QUERY = "SELECT * FROM servizio_sanitario_provinciale WHERE id = ?";
    
    @Override
    public SSP getProvinciaAppartenenza(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        SSP ssp = null;
        
        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                ssp = new SSP(
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
        
        return ssp;
    }
    
}
