package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.dao.interfaces.PazienteDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAO extends JDBCDAO<Paziente, Integer> implements PazienteDAOInterface {
    private static final String GET_PAZIENTE_BY_ID = "SELECT * FROM paziente WHERE id_user = ?";
    private static final String GET_ALL_VISITE = "SELECT 'specialistica' as type, id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_report, NULL AS id_ssp FROM visita_specialistica WHERE id_paziente = ? UNION SELECT 'ssp' AS type, id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, NULL AS id_report, id_ssp FROM visita_ssp WHERE id_paziente = ? UNION SELECT 'base' AS type, id, NULL AS id_visita, NULL AS erogata, NULL AS data_prescrizione, data_erogazione, id_medico, id_paziente, NULL AS id_report, NULL AS id_ssp FROM visita_base WHERE id_paziente = ?;";
    private static final String GET_ALL_TICKETS = "SELECT visita_specialistica.data_erogazione AS data, 'specialistica' as type, elenco_visite_possibili.nome AS nome, elenco_visite_possibili.costo_ticket AS costo FROM visita_specialistica LEFT JOIN elenco_visite_possibili ON visita_specialistica.id_visita = elenco_visite_possibili.id WHERE id_paziente = ? AND data_erogazione IS NOT NULL UNION SELECT visita_ssp.data_erogazione, 'ssp' AS type, elenco_visite_possibili.nome AS nome, elenco_visite_possibili.costo_ticket AS costo FROM visita_ssp LEFT JOIN elenco_visite_possibili ON visita_ssp.id_visita = elenco_visite_possibili.id WHERE id_paziente = ? AND data_erogazione IS NOT NULL;";
    private static final String GET_ALL_RICETTE = "SELECT * FROM ricetta LEFT JOIN visita_base ON ricetta.id_visita_base = visita_base.id WHERE id_paziente = ?";
    private static final String CHANGE_PROFILE_PICTURE = "UPDATE paziente SET foto = ? WHERE id_user = ?";
    private static final String CHANGE_MEDICO_BASE = "UPDATE paziente SET id_medico = ? WHERE id_user = ?";

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
    public ArrayList<Visita> getVisite(Integer id) throws DAOException {
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
    public ArrayList<Ricetta> getRicette(Integer id) throws DAOException {
        ArrayList<Ricetta> ricette = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_ALL_RICETTE)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    ricette.add(new Ricetta(
                            rs.getInt("id"),
                            rs.getString("farmaco"),
                            rs.getInt("id_visita_base"),
                            rs.getBoolean("prescritta")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting ricette", e);
        }
        return ricette;
    }

    @Override
    public void changeProfilePicture(Integer pazienteId, String newPath) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(CHANGE_PROFILE_PICTURE)){
            preparedStatement.setString(1, newPath);
            preparedStatement.setInt(2, pazienteId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while changing profile picture", e);
        }
    }

    @Override
    public void changeMedicoBase(User paziente, User newMedicoBase) throws DAOException, ProvincieNotMatchingException {
        if(!paziente.getProvinciaAppartenenza().equals(newMedicoBase.getProvinciaAppartenenza())){
            throw new ProvincieNotMatchingException();
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement(CHANGE_MEDICO_BASE)){
            preparedStatement.setInt(1, newMedicoBase.getId());
            preparedStatement.setInt(2, paziente.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while changing medico base", e);
        }
    }

    @Override
    public ArrayList<Ticket> getTickets(Integer pazienteId) throws DAOException {
        ArrayList<Ticket> tickets = new ArrayList<>();

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_ALL_TICKETS)){
            preparedStatement.setInt(1, pazienteId);
            preparedStatement.setInt(2, pazienteId);

            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    Ticket t = new Ticket(
                            rs.getDate("data"),
                            rs.getString("type"),
                            rs.getString("nome"),
                            rs.getInt("costo")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting list of visite pagate", e);
        }
        return tickets;
    }
}
