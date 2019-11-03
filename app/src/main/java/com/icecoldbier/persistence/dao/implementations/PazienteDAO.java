package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.PazienteDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAO extends JDBCDAO<Paziente, Integer> implements PazienteDAOInterface {
    private static final String GET_PAZIENTE_BY_ID = "SELECT * FROM paziente WHERE id_user = ?";

    //These are for the separate queries approach
    private static final String GET_VISITE_BASE = "SELECT * FROM visita_base WHERE id_paziente = ?";
    private static final String GET_VISITE_SPECIALISTICHE = "SELECT * FROM visita_specialistica WHERE id_paziente = ?";
    private static final String GET_VISITE_SSP = "SELECT * FROM visita_ssp WHERE id_paziente = ?";

    //This is for the single query approach
    private static final String GET_ALL_VISITE = "SELECT 'specialistica' as type, id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_report, NULL AS id_ssp from visita_specialistica WHERE id_paziente = ? UNION SELECT 'ssp' AS type, id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, NULL AS id_report, id_ssp from visita_ssp WHERE id_paziente = ? UNION SELECT 'base' AS type, id, NULL AS id_visita, NULL AS erogata, NULL AS data_prescrizione, data_erogazione, id_medico, id_paziente, NULL AS id_report, NULL AS id_ssp FROM visita_base WHERE id_paziente = ?;";

    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public PazienteDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public Paziente getByPrimaryKey(Integer primaryKey) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_PAZIENTE_BY_ID)){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return new Paziente(
                        resultSet.getInt("id_user"),
                        resultSet.getDate("data_nascita"),
                        resultSet.getString("luogo_nascita"),
                        resultSet.getString("codice_fiscale"),
                        resultSet.getString("sesso").charAt(0),
                        resultSet.getString("foto"),
                        resultSet.getInt("id_medico"),
                        resultSet.getString("email")

                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting paziente", e);
        }
    }

    @Override
    public List<Paziente> getAll() throws DAOException {
        return null;
    }

    @Override
    public ArrayList<Visita> getVisiteSingleQuery(Integer id) throws DAOException {
        ArrayList<Visita> visite = new ArrayList<>();

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_ALL_VISITE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, id);

            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    String type = rs.getString("type");
                    if(type.equals("base")){
                        VisitaBase visitaBase = new VisitaBase(
                            rs.getInt("id"),
                            rs.getInt("id_medico"),
                            rs.getInt("id_paziente"),
                            rs.getDate("data_erogazione")
                    );
                    visite.add(visitaBase);
                    }
                    else if(type.equals("specialistica")){
                        VisitaSpecialistica visitaSpecialistica = new VisitaSpecialistica(
                            rs.getInt("id"),
                            rs.getInt("id_visita"),
                            rs.getBoolean("erogata"),
                            rs.getDate("data_prescrizione"),
                            rs.getDate("data_erogazione"),
                            rs.getInt("id_medico"),
                            rs.getInt("id_paziente"),
                            rs.getInt("id_report")
                    );
                    visite.add(visitaSpecialistica);
                    }
                    if(type.equals("ssp")){
                        VisitaSSP visitaSSP = new VisitaSSP(
                            rs.getInt("id"),
                            rs.getInt("id_visita"),
                            rs.getBoolean("erogata"),
                            rs.getDate("data_prescrizione"),
                            rs.getDate("data_erogazione"),
                            rs.getInt("id_ssp"),
                            rs.getInt("id_paziente")
                    );
                    visite.add(visitaSSP);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting list of visite", e);
        }
        return visite;
    }

    @Override
    public ArrayList<Visita> getVisiteMultipleQueries(Integer id) throws DAOException {
        ArrayList<Visita> visite = new ArrayList<>();

        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BASE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    VisitaBase visitaBase = new VisitaBase(
                            rs.getInt("id"),
                            rs.getInt("id_medico"),
                            rs.getInt("id_paziente"),
                            rs.getDate("data_erogazione")
                    );
                    visite.add(visitaBase);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of visite base", ex);
        }

        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_SPECIALISTICHE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    VisitaSpecialistica visitaSpecialistica = new VisitaSpecialistica(
                            rs.getInt("id"),
                            rs.getInt("id_visita"),
                            rs.getBoolean("erogata"),
                            rs.getDate("data_prescrizione"),
                            rs.getDate("data_erogazione"),
                            rs.getInt("id_medico"),
                            rs.getInt("id_paziente"),
                            rs.getInt("id_report")
                    );
                    visite.add(visitaSpecialistica);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of visite specialistiche", ex);
        }

        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_SSP)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    VisitaSSP visitaSSP = new VisitaSSP(
                            rs.getInt("id"),
                            rs.getInt("id_visita"),
                            rs.getBoolean("erogata"),
                            rs.getDate("data_prescrizione"),
                            rs.getDate("data_erogazione"),
                            rs.getInt("id_ssp"),
                            rs.getInt("id_paziente")
                    );
                    visite.add(visitaSSP);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of visite ssp", ex);
        }

        return visite;
    }
}
