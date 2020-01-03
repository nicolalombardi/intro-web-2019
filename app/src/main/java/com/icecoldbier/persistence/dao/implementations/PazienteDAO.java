package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.dao.interfaces.PazienteDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAO extends JDBCDAO<Paziente, Integer> implements PazienteDAOInterface {
    private static final String GET_PAZIENTE_BY_ID = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id WHERE id_user = ?";
    private static final String GET_PAZIENTI_COUNT = "SELECT COUNT(*) as count FROM paziente";
    private static final String GET_PAZIENTI_ASSOCIATI_COUNT = "SELECT COUNT(*) as count FROM paziente WHERE paziente.id_medico = ? ";
    private static final String GET_PAZIENTI_PAGED = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id ORDER BY users.nome LIMIT ? OFFSET ?";
    private static final String GET_PAZIENTI_ASSOCIATI_PAGED = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id WHERE paziente.id_medico = ? ORDER BY users.nome LIMIT ? OFFSET ?";
    private static final String GET_ALL_PAZIENTI = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id ORDER BY users.nome";
    private static final String SEARCH_PAZIENTI = "SELECT * FROM paziente INNER JOIN users ON paziente.id_user = users.id  WHERE LOWER(users.username) LIKE ? OR LOWER(users.nome) LIKE ? OR LOWER(users.cognome) LIKE ? ORDER BY users.nome LIMIT 5";
    private static final String GET_VISITE_BASE= "SELECT v.id, v.id_medico, v.id_paziente, v.id_ricetta, v.data_erogazione, users.nome AS nome_medico, users.cognome AS cognome_medico FROM visita_base v INNER JOIN users ON v.id_medico = users.id WHERE v.id_paziente = ? LIMIT ? OFFSET ?";
    private static final String GET_VISITE_SPECIALISTICHE= "SELECT v.id, v.id_visita AS tipo, erogata, v.id_medico, v.id_medico_base, v.id_paziente, v.data_prescrizione, v.data_erogazione, v.id_report FROM visita_specialistica v INNER JOIN users ON v.id_medico = users.id WHERE v.id_paziente = ? LIMIT ? OFFSET ?";
    private static final String GET_ALL_TICKETS = "SELECT visita_specialistica.data_erogazione AS data, 'specialistica' as type, elenco_visite_possibili.nome AS nome, elenco_visite_possibili.costo_ticket AS costo FROM visita_specialistica LEFT JOIN elenco_visite_possibili ON visita_specialistica.id_visita = elenco_visite_possibili.id WHERE id_paziente = ? AND data_erogazione IS NOT NULL UNION SELECT visita_ssp.data_erogazione, 'ssp' AS type, elenco_visite_possibili.nome AS nome, elenco_visite_possibili.costo_ticket AS costo FROM visita_ssp LEFT JOIN elenco_visite_possibili ON visita_ssp.id_visita = elenco_visite_possibili.id WHERE id_paziente = ? AND data_erogazione IS NOT NULL;";
    private static final String GET_ALL_RICETTE = "SELECT ricetta.id, ricetta.farmaco, ricetta.prescritta FROM ricetta, visita_base WHERE ricetta.id = visita_base.id_ricetta AND visita_base.id_paziente = ? UNION SELECT ricetta.id, ricetta.farmaco, ricetta.prescritta FROM ricetta, Visita_specialistica, report WHERE ricetta.id = report.id_ricetta AND report.id = visita_specialistica.id_report AND visita_specialistica.id_paziente = ? LIMIT ? OFFSET ?;";
    private static final String CHANGE_PROFILE_PICTURE = "UPDATE paziente SET foto = ? WHERE id_user = ?";
    private static final String CHANGE_MEDICO_BASE = "UPDATE paziente SET id_medico = ? WHERE id_user = ?";

    private UserDAO userDAO;
    private ReportDAO reportDAO;
    private RicettaDAO ricettaDAO;

    public PazienteDAO(Connection con) {

        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
            reportDAO = daoFactory.getDAO(ReportDAO.class);
            ricettaDAO = daoFactory.getDAO(RicettaDAO.class);

        } catch (DAOFactoryException e) {
            e.printStackTrace();
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
    public Long getAssociatiCount(int idMedico) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_PAZIENTI_ASSOCIATI_COUNT)){
            preparedStatement.setInt(1, idMedico);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of pazienti associati", ex);
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
    public ArrayList<Paziente> getPazientiAssociatiPaged(int idMedico, int pageSize, int page) throws DAOException {
        ArrayList<Paziente> pazienti = new ArrayList<>();

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_PAZIENTI_ASSOCIATI_PAGED)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);

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
    public ArrayList<VisitaBase> getVisiteBase(Integer id, int pageSize, int page) throws DAOException {
        ArrayList<VisitaBase> visite = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BASE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);
            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    User medico = userDAO.getByPrimaryKey(rs.getInt("id_medico"));
                    Paziente paziente = getByPrimaryKey(rs.getInt("id_paziente"));
                    Ricetta ricetta = ricettaDAO.getByPrimaryKey(rs.getInt("id_ricetta"));

                    VisitaBase visitaBase = new VisitaBase(
                            rs.getInt("id"),
                            paziente,
                            rs.getDate("data_erogazione"),
                            medico,
                            ricetta
                    );
                    visite.add(visitaBase);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return visite;
    }

    @Override
    public ArrayList<VisitaSpecialistica> getVisiteSpecialistiche(Integer id, int pageSize, int page) throws DAOException {
        ArrayList<VisitaSpecialistica> visite = new ArrayList<>();
        Report report;
        User medicoBase;
        User medicoSpecialista;
        Paziente paziente;

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_SPECIALISTICHE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);
            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    report = reportDAO.getByPrimaryKey(rs.getInt("id_report"));
                    paziente = getByPrimaryKey(rs.getInt("id_paziente"));
                    medicoSpecialista = userDAO.getByPrimaryKey(rs.getInt("id_medico"));
                    medicoBase = userDAO.getByPrimaryKey(rs.getInt("id_medico_base"));
                    VisitaSpecialistica visitaSpecialistica = new VisitaSpecialistica(
                            rs.getInt("id"),
                            paziente,
                            rs.getDate("data_erogazione"),
                            rs.getInt("tipo"),
                            rs.getBoolean("erogata"),
                            rs.getDate("data_prescrizione"),
                            medicoSpecialista,
                            report,
                            medicoBase);
                    visite.add(visitaSpecialistica);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return visite;
    }

    @Override
    public ArrayList<Ricetta> getRicette(Integer id, int pageSize, int page) throws DAOException {
        ArrayList<Ricetta> ricette = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_ALL_RICETTE)){
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3, pageSize);
            preparedStatement.setInt(4, (page-1)*pageSize);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    ricette.add(new Ricetta(
                            rs.getInt("id"),
                            rs.getString("farmaco"),
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
                    tickets.add(t);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting list of visite pagate", e);
        }
        return tickets;
    }

    private Paziente getPazienteFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
        User medico = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
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
                medico
        );
    }
}
