package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.RicettaDAOInterface;
import com.icecoldbier.persistence.entities.Ricetta;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RicettaDAO extends JDBCDAO<Ricetta, Integer> implements RicettaDAOInterface {
    private static final String GET_RICETTA_BY_ID = "SELECT * FROM ricetta WHERE id = ?";
    private static final String GET_RICETTE_COUNT = "SELECT COUNT(*) as count FROM (SELECT ricetta.id FROM ricetta, visita_base WHERE ricetta.id = visita_base.id_ricetta AND visita_base.id_paziente = ? UNION SELECT ricetta.id FROM ricetta, Visita_specialistica, report WHERE ricetta.id = report.id_ricetta AND report.id = visita_specialistica.id_report AND visita_specialistica.id_paziente = ?) AS ricette";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public RicettaDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_RICETTE_COUNT))){
            preparedStatement.setInt(1,idp);
            preparedStatement.setInt(2,idp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of Ricetta", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public Ricetta getByPrimaryKey(Integer primaryKey) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_RICETTA_BY_ID)) {
            preparedStatement.setInt(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Ricetta r = null;
                if(resultSet.next()){
                    r = new Ricetta(
                            resultSet.getInt("id"),
                            resultSet.getString("farmaco"),
                            resultSet.getBoolean("prescritta")
                    );
                }
                return r;
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting ricetta", e);
        }
    }

    @Override
    public List<Ricetta> getAll() throws DAOException {
        return null;
    }
}
