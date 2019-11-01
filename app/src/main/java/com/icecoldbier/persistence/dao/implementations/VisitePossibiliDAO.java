package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.dao.interfaces.VisitePossibiliDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaPossibile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisitePossibiliDAO implements VisitePossibiliDAOInterface {
    private static final String GET_VISITE_BY_PRATICANTE = "SELECT * FROM elenco_visite_possibili WHERE praticante = CAST(? AS user_type)";

    @Override
    public ArrayList<VisitaPossibile> getVisitePossibili(User.UserType praticante) {
        Connection conn = PostgresDAOFactory.createConnection();

        ArrayList<VisitaPossibile> elencoVisite = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(GET_VISITE_BY_PRATICANTE);
            preparedStatement.setString(1, praticante.toString());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet != null && resultSet.next()) {
                elencoVisite.add(new VisitaPossibile(
                        resultSet.getInt(1),
                        User.UserType.valueOf(resultSet.getString(2)),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elencoVisite;
    }
}
