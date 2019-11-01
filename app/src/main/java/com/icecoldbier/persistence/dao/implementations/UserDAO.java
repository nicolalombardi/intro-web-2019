package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.UserDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Password;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends JDBCDAO<User, Integer> implements UserDAOInterface {
    private static final String GET_COUNT = "SELECT COUNT(*) FROM users";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM users ORDER BY cognome";
    private static final String REGISTER_QUERY = "INSERT INTO users(typ, username, pass, nome, cognome, provincia_appartenenza) VALUES(?,?,?,?,?,?)";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public UserDAO(Connection con) {
        super(con);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_USER_BY_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                User u = new User(
                        rs.getInt("id"),
                        User.UserType.valueOf(rs.getString("typ")),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("provincia_appartenenza")
                );

                return Password.isMatching(password, u.getPassword()) ? u : null; //Return user if matching, null otherwise
            }

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new DAOException("Error while getting the user by username and password", e);
        }
    }

    @Override
    public void createUser(User.UserType typ, String username, String password, String nome, String cognome, String provinciaAppartenenza) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(REGISTER_QUERY)) {
            preparedStatement.setString(1, typ.toString());
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, nome);
            preparedStatement.setString(5, cognome);
            preparedStatement.setString(6, provinciaAppartenenza);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while creating a new user", e);
        }

    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery(GET_COUNT);
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count users", ex);
        }

        return 0L;
    }

    @Override
    public User getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(GET_USER_BY_ID_QUERY)) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                return new User(
                        rs.getInt("id"),
                        User.UserType.valueOf(rs.getString("typ")),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("provincia_appartenenza")
                );
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery(GET_ALL)) {


                while (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            User.UserType.valueOf(rs.getString("typ")),
                            rs.getString("username"),
                            rs.getString("pass"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("provincia_appartenenza")
                    );

                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return users;
    }
}
