package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.MedicoBaseDAOInterface;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaBase;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoBaseDAO extends JDBCDAO<User, Integer> implements MedicoBaseDAOInterface {

    private static final String GET_USER_LIST = "SELECT id FROM visita_base WHERE id_medico = ?";
    private static final String CREATE_VISITA_BASE = "INSERT INTO visita_base(id_medico, id_paziente, data_erogazione) VALUES(?,?,?)";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public MedicoBaseDAO(Connection con) {
        super(con);
    }


    @Override
    public ArrayList<VisitaBase> getListaPazienti(int id) {
        int change = 0;
        ArrayList<VisitaBase> lista = new ArrayList<>();
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_USER_LIST)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    VisitaBase vis = new VisitaBase(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getDate(4));
                    for (VisitaBase x : lista) {
                        if (x.getId_paziente() == vis.getId_paziente()) {
                            if (x.getDataErogazione().before(vis.getDataErogazione())) {
                                lista.remove(x);
                                lista.add(vis);
                                change = 1;
                            }

                        }
                    }
                    if (change == 0) {
                        lista.add(vis);
                    }
                    change = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void createVisitaBase(int idm, int idp, Date data) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(CREATE_VISITA_BASE)) {
            preparedStatement.setInt(1, idm);
            preparedStatement.setInt(2, idp);
            preparedStatement.setDate(3, data);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DAOException("Error while creating a new visita base", e);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public User getByPrimaryKey(Integer primaryKey) throws DAOException {
        return null;
    }

    @Override
    public List<User> getAll() throws DAOException {
        return null;
    }
}