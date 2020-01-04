package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.MedicoSpecialistaDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicoSpecialistaDAO extends JDBCDAO<User, Integer> implements MedicoSpecialistaDAOInterface{

    private static final String GET_MEDICO_SPECIALISTA_BY_ID = "SELECT * FROM users WHERE id = ? AND typ = CAST(? AS user_type)";
    private static final String GET_ALL_MEDICI = "SELECT * FROM users WHERE typ = 'medico_specialista'";


    private static final String GET_LISTA_PAZIENTI = "SELECT DISTINCT paziente.id_user, users.typ,users.username,users.pass,users.nome, "
            + "users.cognome, paziente.data_nascita, paziente.luogo_nascita, paziente.codice_fiscale,paziente.sesso,"
            + " users.provincia_appartenenza, paziente.foto, paziente.id_medico "
            + "FROM visita_specialistica, users, paziente "
            + "WHERE visita_specialistica.id_medico = ? AND paziente.id_user = users.id AND visita_specialistica.id_paziente = paziente.id_user;";
    private static final String GET_LISTA_VISITE = "SELECT * \n" +
        "FROM visita_specialistica\n" +
        "WHERE id_medico = ?\n" +
        "ORDER BY data_prescrizione DESC;";
    private static final String EROGA_VISITA = "UPDATE visita_specialistica\n" +
        "SET erogata = 'true', data_erogazione = NOW(), id_report = ?\n" +
        "        WHERE id = ? AND id_paziente = ? AND id_medico_base = ?";
    private static final String EROGA_VISITA_RICETTA = "";
    private static final String GET_INFO_PAZIENTE = "SELECT users.nome, users.cognome, users.provincia_appartenenza,\n" +
        "	 paziente.data_nascita, paziente.luogo_nascita, paziente.sesso,\n" +
        "	 paziente.email, A.nome, A.cognome\n" +
        "FROM paziente, users, (SELECT id ,nome, cognome FROM users WHERE user_type = 'medico_base') as A\n" +
        "WHERE paziente.id_user = users.id AND paziente.id_user = ? AND paziente.id_medico = A.id";
    private static final String INSERT_REPORT = "INSERT INTO report (esito) VALUES (?)";
    private static final String GET_PAZIENTI_COUNT = "SELECT COUNT(*) as count FROM (SELECT DISTINCT id_paziente FROM visita_specialistica "
            + "WHERE id_medico = ?) AS A";
    private static final String GET_LISTA_PAZIENTI_PAGED = "SELECT DISTINCT paziente.id_user, users.typ,users.username,users.pass,users.nome, "
            + "users.cognome, paziente.data_nascita, paziente.luogo_nascita, paziente.codice_fiscale,paziente.sesso,"
            + " users.provincia_appartenenza, paziente.foto, paziente.id_medico "
            + "FROM visita_specialistica, users, paziente "
            + "WHERE visita_specialistica.id_medico = ? AND paziente.id_user = users.id AND visita_specialistica.id_paziente = paziente.id_user LIMIT ? OFFSET ?";

    private UserDAO userDAO;
    private PazienteDAO pazienteDAO;
    private ReportDAO reportDAO;
    private VisitePossibiliDAO visitePossibiliDAO;
    
    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public MedicoSpecialistaDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
            reportDAO = daoFactory.getDAO(ReportDAO.class);
            visitePossibiliDAO = daoFactory.getDAO(VisitePossibiliDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public User getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(GET_MEDICO_SPECIALISTA_BY_ID)) {
            stm.setInt(1, primaryKey);
            stm.setString(2, User.UserType.medico_specialista.name());
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();

                return new User(
                        rs.getInt("id"),
                        User.UserType.valueOf(rs.getString("typ")),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("provincia_appartenenza")
                );
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the medico specialista for the passed primary key", ex);
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        List<User> mediciSpecialisti = new ArrayList<>();
        try(Statement stm = CON.createStatement()) {
            ResultSet rs = stm.executeQuery(GET_ALL_MEDICI);
            while (rs.next()){
                User m = new User(
                        rs.getInt("id"),
                        User.UserType.valueOf(rs.getString("typ")),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("provincia_appartenenza")
                );
                mediciSpecialisti.add(m);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting list of medici specialisti", e);
        }
        return mediciSpecialisti;
    }

    @Override
    public ArrayList<Paziente> getListaPazientiAssociati(int id) throws DAOException {
        ArrayList<Paziente> pazienti = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = CON.prepareStatement(GET_LISTA_PAZIENTI);
            preparedStatement.setInt(1, id);
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
    public ArrayList<VisitaSpecialistica> getListaVisitePazienti(int id) throws DAOException {
        ArrayList<VisitaSpecialistica> visite = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = CON.prepareStatement(GET_LISTA_VISITE);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaSpecialistica visita = getVisitaSpecialisticaFromResultSet(resultSet);
                    visite.add(visita);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of pazienti", ex);
        }
        return visite;
    }

    @Override
    public void erogaVisita(int idVisita,int idPaziente,int idMedicoBase,Report report) throws DAOException {
        try {
            PreparedStatement preparedStatement = CON.prepareStatement(INSERT_REPORT,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, report.getEsito());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idReport = 0;
            if(generatedKeys.next()){
                idReport = generatedKeys.getInt(1);
            }
            preparedStatement = CON.prepareStatement(EROGA_VISITA);
            preparedStatement.setInt(1, idReport);
            preparedStatement.setInt(2, idVisita);
            preparedStatement.setInt(3, idPaziente);
            preparedStatement.setInt(4, idMedicoBase);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Non è stato possibile erogare una nuova visita", ex);
        }
    }

    @Override
    public void erogaVisitaConRicetta(int idv, Report report) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
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
    
    private VisitaSpecialistica getVisitaSpecialisticaFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
        Paziente paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
        User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico_base"));
        User medicoSpecialista = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
        Report report = reportDAO.getByPrimaryKey(resultSet.getInt("id_report"));
        VisitaPossibile tipoVisita = visitePossibiliDAO.getByPrimaryKey(resultSet.getInt("id_visita"));

        return new VisitaSpecialistica(
                resultSet.getInt("id"),
                paziente,
                resultSet.getDate("data_erogazione"),
                tipoVisita,
                resultSet.getBoolean("erogata"),
                resultSet.getDate("data_prescrizione"),
                medicoSpecialista,
                report,
                medicoBase
        );
    }

    @Override
    public Long getCountPazienti(int idMedico) throws DAOException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CON.prepareStatement(GET_PAZIENTI_COUNT);
            preparedStatement.setInt(1, idMedico);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of pazienti", ex);
        }
    }

    @Override
    public ArrayList<Paziente> getListaPazientiAssociatiPaged(int id, int pageSize, int page) throws DAOException {
        ArrayList<Paziente> pazienti = new ArrayList<>();

        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_LISTA_PAZIENTI_PAGED)){
            preparedStatement.setInt(1, id);
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
}
