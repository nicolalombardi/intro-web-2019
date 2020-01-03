package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaSpecialisticaDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VisitaSpecialisticaDAO extends JDBCDAO<VisitaSpecialistica, Integer> implements VisitaSpecialisticaDAOInterface {
    private static final String GET_BY_ID = "SELECT * FROM visita_specialistica WHERE id = ?";
    private static final String GET_VISITE_COUNT = "SELECT COUNT(id) as count FROM visita_specialistica v WHERE v.id_paziente = ?";


    private PazienteDAO pazienteDAO;
    private UserDAO userDAO;
    private ReportDAO reportDAO;
    private VisitePossibiliDAO visitePossibiliDAO;


    public VisitaSpecialisticaDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
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
    public Long getCount(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_VISITE_COUNT))){
            preparedStatement.setInt(1,idp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visitaSpecialistica", ex);
        }
    }

    @Override
    public VisitaSpecialistica getByPrimaryKey(Integer primaryKey) throws DAOException {
        User medicoBase;
        User medicoSpecialista;
        Paziente paziente;
        Report report;
        VisitaPossibile tipoVisita;
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                report = reportDAO.getByPrimaryKey(resultSet.getInt("id_report"));
                paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
                medicoSpecialista = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
                medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico_base"));
                tipoVisita = visitePossibiliDAO.getByPrimaryKey(resultSet.getInt("id_visita"));
                return new VisitaSpecialistica(
                        resultSet.getInt("id"),
                        paziente,
                        resultSet.getDate("data_erogazione"),
                        tipoVisita,
                        resultSet.getBoolean("erogata"),
                        resultSet.getDate("data_prescrizione"),
                        medicoSpecialista,
                        report,
                        medicoBase);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting visita specialistica", e);
        }
    }

    @Override
    public List<VisitaSpecialistica> getAll() throws DAOException {
        return null;
    }
}