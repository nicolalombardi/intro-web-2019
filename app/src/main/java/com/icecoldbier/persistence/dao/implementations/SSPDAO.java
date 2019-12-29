package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.SSPDAOInterface;
import com.icecoldbier.persistence.entities.InfoRicetta;
import com.icecoldbier.persistence.entities.SSP;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SSPDAO extends JDBCDAO<SSP, Integer> implements SSPDAOInterface {
    private static final String EROGA_VISITA_BY_ID = "UPDATE visita_ssp SET erogata = TRUE, data_erogazione = NOW() WHERE id = ?";
    private static final String GET_VISITE = "SELECT v.data_erogazione AS data, r.farmaco AS farmaco, v.id_medico AS medico, v.id_paziente AS paziente FROM visita_base v, ricetta r, users m WHERE r.id_visita_base = v.id AND v.id_medico = m.id AND v.data_erogazione = CAST ( ? AS date) AND m.provincia_appartenenza = ?";
    private static final String GET_SSP_BY_ID = "SELECT * FROM users WHERE id = ?";



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
    public ArrayList<InfoRicetta> getListaRicette(Date data, SSP ssp) throws DAOException {
        ArrayList<InfoRicetta> list = new ArrayList<InfoRicetta>();
        /*
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE)) {
            preparedStatement.setDate(1, data);
            String prov = ssp.getProvinciaAppartenenza();
            preparedStatement.setString(2, prov);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(new InfoRicetta(resultSet.getDate("data"), resultSet.getString("farmaco"), resultSet.getInt("medico"), resultSet.getInt("paziente")));
                System.out.println("Aggiunta una visita alla lista");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting lista ricette erogate in un giorno ", e);
        }
         */
        return list;

    }



    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public SSP getByPrimaryKey(Integer primaryKey) throws DAOException {
        SSP ssp = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_SSP_BY_ID)){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                ssp = new SSP(
                        resultSet.getInt("id"),
                        resultSet.getString("provincia_appartenenza")
                );
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting report", e);
        }
        return ssp;
    }

    @Override
    public List<SSP> getAll() throws DAOException {
        return null;
    }
}
