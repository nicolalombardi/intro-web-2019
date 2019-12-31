package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitePossibiliDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaPossibile;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitePossibiliDAO extends JDBCDAO<VisitaPossibile, Integer> implements VisitePossibiliDAOInterface {
    private static final String GET_VISITE_BY_PRATICANTE = "SELECT * FROM elenco_visite_possibili WHERE praticante = CAST(? AS user_type)";
    private static final String GET_VISITA_FROM_ID = "SELECT * FROM elenco_visite_possibili WHERE id = ?";


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

        try (PreparedStatement stm = CON.prepareStatement(GET_VISITE_BY_PRATICANTE)) {
            stm.setString(1, praticante.name());
            try (ResultSet resultSet = stm.executeQuery()) {

                while (resultSet.next()) {
                    VisitaPossibile visitaPossibile = new VisitaPossibile(
                            resultSet.getInt("id"),
                            User.UserType.valueOf(resultSet.getString("praticante")),
                            resultSet.getString("nome"),
                            resultSet.getString("descrizione"),
                            resultSet.getInt("costo_ticket")
                    );
                    visitePossibili.add(visitaPossibile);
                }
                return visitePossibili;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Error while getting the possible visits list", ex);
        }

    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public VisitaPossibile getByPrimaryKey(Integer primaryKey) throws DAOException {
        VisitaPossibile v = null;
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITA_FROM_ID)) {
            preparedStatement.setInt(1, primaryKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String praticante = resultSet.getString("praticante");
            String nome = resultSet.getString("nome");
            String descrizione = resultSet.getString("descrizione");
            int costo_ticket = resultSet.getInt("costo_ticket");

            v = new VisitaPossibile(primaryKey, User.UserType.valueOf(praticante), nome, descrizione, costo_ticket);
        } catch (SQLException e) {
            throw new DAOException("Error while getting a visita_specialistica", e);
        }
        return v;
    }

    @Override
    public List<VisitaPossibile> getAll() throws DAOException {
        return null;
    }
}
