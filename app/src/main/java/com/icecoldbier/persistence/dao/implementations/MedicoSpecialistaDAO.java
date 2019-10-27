package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.dao.interfaces.MedicoSpecialistaDAOInterface;
import com.icecoldbier.persistence.entities.MedicoSpecialista;
import com.icecoldbier.persistence.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicoSpecialistaDAO implements MedicoSpecialistaDAOInterface{
    private static final String SELECT_QUERY = "SELECT * FROM medico_spacialista WHERE id = ?";
    
    @Override
    public MedicoSpecialista getMedicoSpecialista(Integer id){
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        MedicoSpecialista medicoSpecialista = null;
        
        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                medicoSpecialista = new MedicoSpecialista(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
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
        
        return medicoSpecialista;
    }
}
