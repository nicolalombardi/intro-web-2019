package com.icecoldbier.persistence.dao.factories;

import com.icecoldbier.persistence.dao.implementations.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDAOFactory extends DAOFactory {



    /**
     * Metodo per creare una connessione al database
     *
     * @return L'oggetto Connection
     */
    public static Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName(DBConf.DRIVER);
            conn = DriverManager.getConnection(DBConf.DBURL, DBConf.USER, DBConf.PASS);
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
        return new UserDAO();
    }
}