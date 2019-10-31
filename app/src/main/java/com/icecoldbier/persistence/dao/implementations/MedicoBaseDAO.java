package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.factories.PostgresDAOFactory;
import com.icecoldbier.persistence.dao.interfaces.MedicoBaseDAOInterface;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaBase;

import java.sql.*;
import java.util.ArrayList;

public class MedicoBaseDAO implements MedicoBaseDAOInterface {

    private static final String GET_USER_LIST = "SELECT id FROM visita_base WHERE id_medico = ?";
    private static final String CREATE_VISITA_BASE = "INSERT INTO visita_base(id_medico, id_paziente, data_erogazione) VALUES(?,?,?)";


    @Override
    public ArrayList<VisitaBase> getListaPazienti(int id) {
        ArrayList<VisitaBase> lista = new ArrayList<VisitaBase>();
        Connection conn = PostgresDAOFactory.createConnection();
        int change=0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(GET_USER_LIST);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet != null && resultSet.next()) {
                VisitaBase vis = new VisitaBase(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getDate(4));
                for (VisitaBase x:lista) {
                    if(x.getIdPaziente() == vis.getIdPaziente()){
                        if(x.getDataErogazione().before(vis.getDataErogazione())){
                            lista.remove(x);
                            lista.add(vis);
                            change=1;
                        }

                    }
                }
                if(change == 0){
                    lista.add(vis);
                }
                change = 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void createVisitaBase(int idm, int idp, Date data) {
        Connection conn = PostgresDAOFactory.createConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_VISITA_BASE);
            preparedStatement.setInt(1, idm);
            preparedStatement.setInt(2, idp);
            preparedStatement.setDate(3, data);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}