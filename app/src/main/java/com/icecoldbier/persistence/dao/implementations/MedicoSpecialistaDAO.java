package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.MedicoSpecialistaDAOInterface;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.Report;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaSpecialistica;
import it.unitn.disi.wp.commons.persistence.dao.DAO;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicoSpecialistaDAO extends JDBCDAO<User, Integer> implements MedicoSpecialistaDAOInterface{
    
    private static final String GET_LISTA_PAZIENTI = "SELECT * FROM visita_specialistica, users, paziente "
            + "WHERE paziente.id_user = users.id AND visita_specialistica.id_paziente = paziente.id_user "
            + "AND visita_specialistica.id_medico = ? GROUP BY users.id";
    private static final String GET_LISTA_VISITE = "SELECT * \n" +
        "FROM visita_specialistica\n" +
        "WHERE id_medico = ?\n" +
        "ORDER BY data_prescrizione DESC;";
    private static final String EROGA_VISITA = "UPDATE visita_specialistica\n" +
        "SET erogata = 'true'\n" +
        "	data_erogazione = ?\n" +
        "WHERE id = ? AND id_medico = ?";
    private static final String EROGA_VISITA_RICETTA = "";
    private static final String GET_INFO_PAZIENTE = "SELECT users.nome, users.cognome, users.provincia_appartenenza,\n" +
        "	 paziente.data_nascita, paziente.luogo_nascita, paziente.sesso,\n" +
        "	 paziente.email, A.nome, A.cognome\n" +
        "FROM paziente, users, (SELECT id ,nome, cognome FROM users WHERE user_type = 'medico_base') as A\n" +
        "WHERE paziente.id_user = users.id AND paziente.id_user = ? AND paziente.id_medico = A.id";
    
    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public MedicoSpecialistaDAO(Connection con) {
        super(con);
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
    public ArrayList<VisitaSpecialistica> getListaVisitePazienti() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Paziente getInfoPaziente(int id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void erogaVisita(int idv, Report report) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void erogaVisitaConRicetta(int idv, Report report) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
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
