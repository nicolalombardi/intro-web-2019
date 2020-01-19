package com.icecoldbier.listeners;

import com.icecoldbier.persistence.dao.DBConf;
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
            JDBCDAOFactory.configure(DBConf.DBURL);
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            sce.getServletContext().setAttribute("daoFactory", daoFactory);
            String photosFolderPath = sce.getServletContext().getInitParameter("photosFolder");
            photosFolderPath = sce.getServletContext().getRealPath(photosFolderPath);
            File photosFolder = new File(photosFolderPath);
            if (! photosFolder.exists()){
                photosFolder.mkdir();
            }
        } catch (DAOFactoryException ex) {
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
