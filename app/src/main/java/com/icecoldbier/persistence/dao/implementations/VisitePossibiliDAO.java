package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitePossibiliDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaPossibile;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VisitePossibiliDAO extends JDBCDAO<VisitaPossibile, Integer> implements VisitePossibiliDAOInterface {
    private static final String GET_VISITE_BY_PRATICANTE = "SELECT * FROM elenco_visite_possibili WHERE praticante = CAST(? AS user_type)";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public VisitePossibiliDAO(Connection con) {
        super(con);
    }

    @Override
    public ArrayList<VisitaPossibile> getVisitePossibili(User.UserType praticante) throws DAOException {
        ArrayList<VisitaPossibile> visitePossibili = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet resultSet = stm.executeQuery(GET_VISITE_BY_PRATICANTE)) {

                while (resultSet.next()) {
                    VisitaPossibile visitaPossibile = new VisitaPossibile(
                            resultSet.getInt(1),
                            User.UserType.valueOf(resultSet.getString(2)),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)
                    );

                    visitePossibili.add(visitaPossibile);
                }
                return visitePossibili;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while getting the possible visits list", ex);
        }

    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public VisitaPossibile getByPrimaryKey(Integer primaryKey) throws DAOException {
        return null;
    }

    @Override
    public List<VisitaPossibile> getAll() throws DAOException {
        return null;
    }
}
