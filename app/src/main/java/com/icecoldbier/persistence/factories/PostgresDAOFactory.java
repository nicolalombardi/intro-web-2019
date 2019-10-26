package com.icecoldbier.persistence.factories;

import com.icecoldbier.persistence.dao.implementations.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDAOFactory extends DAOFactory {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL = "jdbc:postgresql://localhost/nicola";
    private static final String USER = "nicola";
    private static final String PASS = "123";

    /**
     * Metodo per creare una connessione al database
     *
     * @return L'oggetto Connection
     */
    public static Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:postgres:;shutdown=true");
        } catch (SQLException sqle) {
            System.err.println(this.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    @Override
    public UserDAO getUserDAO() {
        return null;
    }
}