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
    private static final String GET_PAZIENTI_COUNT = "SELECT COUNT(*) as count FROM paziente";
    private static final String GET_PAZIENTE_BY_ID = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id WHERE id_user = ?";
    private static final String GET_PAZIENTI_PAGED = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id ORDER BY users.nome LIMIT ? OFFSET ?";
    private static final String GET_ALL_PAZIENTI = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id ORDER BY users.nome";
    private static final String SEARCH_PAZIENTI = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id  WHERE LOWER(users.username) LIKE ? OR LOWER(users.nome) LIKE ? OR LOWER(users.cognome) LIKE ? ORDER BY users.nome LIMIT 5";
    private static final String GET_ALL_VISITE = "SELECT 'specialistica' as type, id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_report, NULL AS id_ssp FROM visita_specialistica WHERE id_paziente = ? UNION SELECT 'ssp' AS type, id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, NULL AS id_report, id_ssp FROM visita_ssp WHERE id_paziente = ? UNION SELECT 'base' AS type, id, NULL AS id_visita, NULL AS erogata, NULL AS data_prescrizione, data_erogazione, id_medico, id_paziente, NULL AS id_report, NULL AS id_ssp FROM visita_base WHERE id_paziente = ? LIMIT ? OFFSET ?;";
    private static final String GET_VISITE_BASE= "SELECT v.id, v.id_medico, v.id_paziente, v.data_erogazione, users.nome AS nome_medico, users.cognome AS cognome_medico FROM visita_base v INNER JOIN users ON v.id_medico = users.id WHERE v.id_paziente = ? LIMIT ? OFFSET ?";
    private static final String GET_VISITE_SPECIALISTICHE= "SELECT v.id, v.id_visita AS tipo, erogata, v.id_medico, v.id_paziente, v.data_prescrizione, v.data_erogazione, v.id_report, users.nome AS nome_medico, users.cognome AS cognome_medico FROM visita_specialistica v INNER JOIN users ON v.id_medico = users.id WHERE v.id_paziente = ? LIMIT ? OFFSET ?";
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
        try (Statement stm = CON.createStatement()) {
            try (ResultSet resultSet = stm.executeQuery(GET_PAZIENTI_COUNT)) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of pazienti", ex);
        }
    }

    @Override
    public Paziente getByPrimaryKey(Integer primaryKey) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_PAZIENTE_BY_ID)){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return getPazienteFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting paziente", e);
        }
    }

    @Override
    public List<Paziente> getAll() throws DAOException {
        List<Paziente> pazienti = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet resultSet = stm.executeQuery(GET_ALL_PAZIENTI)) {
                while (resultSet.next()) {
                    Paziente paziente = getPazienteFromResultSet(resultSet);
                    pazienti.add(paziente);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of pazienti", ex);
        }

        return pazienti;
    }

    @Override
    public ArrayList<Paziente> getPazientiPaged(int pageSize, int page) throws DAOException {
        ArrayList<Paziente> pazienti = new ArrayList<>();

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_PAZIENTI_PAGED)){
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, (page-1)*pageSize);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Paziente paziente = getPazienteFromResultSet(resultSet);
                    pazienti.add(paziente);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of pazienti", ex);
        }

        return pazienti;
    }

    @Override
    public ArrayList<Paziente> searchPazienti(String query) throws DAOException {
        ArrayList<Paziente> pazienti = new ArrayList<>();

        query = query.toLowerCase() + "%";
        try(PreparedStatement preparedStatement = CON.prepareStatement(SEARCH_PAZIENTI)){
            preparedStatement.setString(1, query);
            preparedStatement.setString(2, query);
            preparedStatement.setString(3, query);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Paziente paziente = getPazienteFromResultSet(resultSet);
                    pazienti.add(paziente);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to search the list of pazienti", ex);
        }

        return pazienti;
    }

    @Override
    public ArrayList<Visita> getVisite(Integer id, int pageSize, int page) throws DAOException {
        ArrayList<Visita> visite = new ArrayList<>();

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_ALL_VISITE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(4, pageSize);
            preparedStatement.setInt(5, (page-1)*pageSize);

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
    public ArrayList<VisitaBase> getVisiteBase(Integer id, int pageSize, int page) throws DAOException {
        ArrayList<VisitaBase> visite = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BASE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);
            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    VisitaBase visitaBase = new VisitaBase(
                            rs.getInt("id"),
                            rs.getInt("id_paziente"),
                            rs.getDate("data_erogazione"),
                            rs.getInt("id_medico"),
                            rs.getString("nome_medico"),
                            rs.getString("cognome_medico"));
                    visite.add(visitaBase);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        };
        return visite;
    }

    @Override
    public ArrayList<VisitaSpecialistica> getVisiteSpecialistiche(Integer id, int pageSize, int page) throws DAOException {
        ArrayList<VisitaSpecialistica> visite = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_SPECIALISTICHE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);
            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    VisitaSpecialistica visitaSpecialistica = new VisitaSpecialistica(
                            rs.getInt("id"),
                            rs.getInt("id_paziente"),
                            rs.getDate("data_erogazione"),
                            rs.getInt("tipo"),
                            rs.getBoolean("erogata"),
                            rs.getDate("data_prescrizione"),
                            rs.getInt("id_medico"),
                            rs.getInt("id_report"),
                            rs.getString("nome_medico"),
                            rs.getString("cognome_medico"));
                    visite.add(visitaSpecialistica);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        };
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

    private Paziente getPazienteFromResultSet(ResultSet resultSet) throws SQLException {
        return new Paziente(
                resultSet.getInt("id_user"),
                User.UserType.valueOf(resultSet.getString("typ")),
                resultSet.getString("username"),
                resultSet.getString("pass"),
                resultSet.getString("nome"),
                resultSet.getString("cognome"),
                resultSet.getString("provincia_appartenenza"),
                resultSet.getDate("data_nascita"),
                resultSet.getString("luogo_nascita"),
                resultSet.getString("codice_fiscale"),
                resultSet.getString("sesso").charAt(0),
                resultSet.getString("foto"),
                resultSet.getInt("id_medico")
        );
    }
}
