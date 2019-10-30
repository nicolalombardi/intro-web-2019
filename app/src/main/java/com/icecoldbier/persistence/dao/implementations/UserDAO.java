package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.UserDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.utils.Password;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDAOInterface {
    private static final String REGISTER_QUERY = "INSERT INTO users(typ, username, pass, nome, cognome, provincia_appartenenza) VALUES(?,?,?,?,?,?)";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE id = ?";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM Users WHERE username = ?";


    @Override
    public User getUserById(Integer id) {
        Connection conn = PostgresDAOFactory.createConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(GET_USER_BY_ID_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                return new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        Connection conn = PostgresDAOFactory.createConnection();

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(GET_USER_BY_USERNAME_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            //If the username is no existent
            if(resultSet == null || !resultSet.next()){
                return null;
            }

            User u = new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );

            return Password.isMatching(password, u.getPassword()) ? u : null; //Return user if matching, null otherwise

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User createUser(String typ, String username, String password, String nome, String cognome, String provinciaAppartenenza) {
        Connection conn = PostgresDAOFactory.createConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(REGISTER_QUERY);
            preparedStatement.setString(1, typ);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, nome);
            preparedStatement.setString(5, cognome);
            preparedStatement.setString(6, provinciaAppartenenza);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(-1, typ, username, password, nome, cognome, provinciaAppartenenza);
    }
}
