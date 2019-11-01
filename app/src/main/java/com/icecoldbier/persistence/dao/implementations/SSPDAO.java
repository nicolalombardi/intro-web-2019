package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.SSPDAOInterface;
import com.icecoldbier.persistence.entities.SSP;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SSPDAO extends JDBCDAO<SSP, Integer> implements SSPDAOInterface {
    private static final String EROGA_VISITA_BY_ID = "UPDATE visita_ssp SET erogata = TRUE, data_erogazione = NOW() WHERE id = ?";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public SSPDAO(Connection con) {
        super(con);
    }

    @Override
    public void erogaVisitaPrescritta(int id) throws DAOException {

        try (PreparedStatement preparedStatement = CON.prepareStatement(EROGA_VISITA_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while updating visita", e);
        }
    }


    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public SSP getByPrimaryKey(Integer primaryKey) throws DAOException {
        return null;
    }

    @Override
    public List<SSP> getAll() throws DAOException {
        return null;
    }
}
