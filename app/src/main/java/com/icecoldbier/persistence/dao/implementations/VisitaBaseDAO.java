package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaBaseDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitaBaseDAO extends JDBCDAO<VisitaBase, Integer> implements VisitaBaseDAOInterface {
    private static final String GET_VISITE_BY_MEDICO_PAGED = "SELECT * FROM visita_base WHERE id_medico = ? LIMIT ? OFFSET ?";
    private static final String GET_COUNT_VISITE_BY_MEDICO = "SELECT COUNT(*) FROM visita_base WHERE id_medico = ?";

    private static final String GET_VISITE_BY_PAZIENTE_PAGED = "SELECT * FROM visita_base WHERE id_paziente = ? LIMIT ? OFFSET ?";
    private static final String GET_COUNT_VISITE_BY_PAZIENTE = "SELECT COUNT(*) FROM visita_base WHERE id_paziente = ?";

    private static final String GET_VISITE_BY_MEDICO_PAZIENTE_PAGED = "SELECT COUNT * FROM visita_base WHERE id_medico = ? AND id_paziente = ? LIMIT ? OFFSET ?";
    private static final String GET_COUNT_VISITE_BY_MEDICO_PAZIENTE = "SELECT COUNT(*) FROM visita_base WHERE id_medico = ? AND id_paziente = ?";

    private static final String GET_BY_ID = "SELECT * FROM visita_base WHERE id = ? ";
    private static final String GET_VISITE_COUNT = "SELECT COUNT(*) as count FROM visita_base";

    private UserDAO userDAO;
    private PazienteDAO pazienteDAO;
    private RicettaDAO ricettaDAO;

    public VisitaBaseDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            ricettaDAO = daoFactory.getDAO(RicettaDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long getCount() throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_VISITE_COUNT))){
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visitaBase", ex);
        }
    }

    @Override
    public VisitaBase getByPrimaryKey(Integer primaryKey) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return getFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting visita base", e);
        }
    }

    @Override
    public List<VisitaBase> getAll() throws DAOException {
        return null;
    }

    @Override
    public ArrayList<VisitaBase> getByMedicoPaged(int medicoId, int pageSize, int page) throws DAOException {
        ArrayList<VisitaBase> visiteBase = new ArrayList<>();


        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BY_MEDICO_PAGED)){
            preparedStatement.setInt(1, medicoId);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaBase visitaBase = getFromResultSet(resultSet);
                    visiteBase.add(visitaBase);
                }
            }
        } catch (SQLException | DAOException ex) {
            throw new DAOException("Impossible to get the list of visite base", ex);
        }

        return visiteBase;
    }

    @Override
    public ArrayList<VisitaBase> getByPazientePaged(int pazienteId, int pageSize, int page) throws DAOException {
        ArrayList<VisitaBase> visiteBase = new ArrayList<>();


        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BY_PAZIENTE_PAGED)){
            preparedStatement.setInt(1, pazienteId);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (page-1)*pageSize);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaBase visitaBase = getFromResultSet(resultSet);
                    visiteBase.add(visitaBase);
                }
            }
        } catch (SQLException | DAOException ex) {
            throw new DAOException("Impossible to get the list of visite base", ex);
        }

        return visiteBase;
    }

    @Override
    public ArrayList<VisitaBase> getByMedicoAndPazientePaged(int idMedico, int idPaziente, int pageSize, int page) throws DAOException {
        ArrayList<VisitaBase> visiteBase = new ArrayList<>();


        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BY_MEDICO_PAZIENTE_PAGED)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, idPaziente);
            preparedStatement.setInt(3, pageSize);
            preparedStatement.setInt(4, (page-1)*pageSize);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaBase visitaBase = getFromResultSet(resultSet);
                    visiteBase.add(visitaBase);
                }
            }
        } catch (SQLException | DAOException ex) {
            throw new DAOException("Impossible to get the list of visite base", ex);
        }

        return visiteBase;
    }

    @Override
    public Long getByMedicoCount(int idMedico) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_COUNT_VISITE_BY_MEDICO)){
            preparedStatement.setInt(1, idMedico);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visite by medico", ex);
        }
    }

    @Override
    public Long getByPazienteCount(int idPaziente) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_COUNT_VISITE_BY_PAZIENTE)){
            preparedStatement.setInt(1, idPaziente);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visite by paziente", ex);
        }
    }

    @Override
    public Long getByMedicoAndPazienteCount(int idMedico, int idPaziente) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_COUNT_VISITE_BY_MEDICO_PAZIENTE)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, idPaziente);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visite by medico and paziente", ex);
        }
    }

    private VisitaBase getFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
        User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
        Paziente paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
        Ricetta ricetta = ricettaDAO.getByPrimaryKey(resultSet.getInt("id_ricetta"));
        return new VisitaBase(
                resultSet.getInt("id"),
                paziente,
                resultSet.getDate("data_erogazione"),
                medicoBase,
                ricetta
        );
    }

}
