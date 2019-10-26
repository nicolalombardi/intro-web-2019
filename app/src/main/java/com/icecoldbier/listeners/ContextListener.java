package com.icecoldbier.listeners;

import com.icecoldbier.persistence.factories.DAOFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.POSTGRES);
        servletContextEvent.getServletContext().setAttribute("daoFactory", daoFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DAOFactory daoFactory = (DAOFactory) servletContextEvent.getServletContext().getAttribute("daoFactory");
        if(daoFactory != null){
            daoFactory.shutdown();
            daoFactory = null;
        }
    }
}
