package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.UserDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDAOInterface {
    private static final String CREATE_QUERY = "INSERT INTO users(typ, username, pass) VALUES(?,?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM Users WHERE id = ?";


    @Override
    public User getUser(Integer id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
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

        return user;
    }

    @Override
    public User createUser(String typ, String username, String password) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            conn = PostgresDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, typ);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                conn.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        user = new User(-1, typ, username, password);
        return user;
    }
}
