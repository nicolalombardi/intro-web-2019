/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Lab 08 - Commons - DAO interface
 * UniTN
 */
package it.unitn.disi.wp.commons.persistence.dao.factories;

import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;

/**
 * This interface must be implemented by all the concrete {@code DAOFactor(y)}ies.
 * 
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 2019.04.06
 */
public interface DAOFactory {

    /**
     * Shutdowns the connection to the storage system.
     * 
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public void shutdown();
    
    /**
     * Returns the concrete {@link DAO dao} which type is the class passed as
     * parameter.
     * 
     * @param <DAO_CLASS> the class name of the {@code dao} to get.
     * @param daoInterface the class instance of the {@code dao} to get.
     * @return the concrete {@code dao} which type is the class passed as
     * parameter.
     * @throws DAOFactoryException if an error occurred during the operation.
     * 
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException;
}
