package com.icecoldbier.listeners;

import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //Get database configuration parameters
            String dbUrl = sce.getServletContext().getInitParameter("dbUrl");
            String dbDriver = sce.getServletContext().getInitParameter("dbDriver");
            String dbUser = sce.getServletContext().getInitParameter("dbUser");
            String dbPassword = sce.getServletContext().getInitParameter("dbPassword");

            JDBCDAOFactory.configure(dbUrl, dbDriver, dbUser, dbPassword);
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            sce.getServletContext().setAttribute("daoFactory", daoFactory);
            //Create the photos folder and the thumbs folder if they don't exist
            String resizedPhotosFolderPath = sce.getServletContext().getInitParameter("resizedPhotosFolder");
            resizedPhotosFolderPath = sce.getServletContext().getRealPath(resizedPhotosFolderPath);
            File resizedPhotosFolder = new File(resizedPhotosFolderPath);
            if (! resizedPhotosFolder.exists()){
                resizedPhotosFolder.mkdirs();
            }
        } catch (DAOFactoryException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
        daoFactory = null;
    }
}
