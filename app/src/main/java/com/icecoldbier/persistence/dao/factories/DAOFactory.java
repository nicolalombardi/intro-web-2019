package com.icecoldbier.persistence.dao.factories;

import com.icecoldbier.persistence.dao.implementations.UserDAO;

public abstract class DAOFactory {
    public static final int POSTGRES = 0; //This is the default one

    public abstract void shutdown();
    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(int db){
        if(db == POSTGRES){
            return new PostgresDAOFactory();
        }else{
            return new PostgresDAOFactory();
        }
    }


}
