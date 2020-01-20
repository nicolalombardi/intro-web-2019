package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.VisitaSSPDAOInterface;
import com.icecoldbier.persistence.entities.*;
import com.icecoldbier.utils.pagination.PaginationParameters;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitaSSPDAO extends JDBCDAO<VisitaSSP, Integer> implements VisitaSSPDAOInterface {
    private static final String GET_VISITA_BY_ID = "SELECT * FROM visita_ssp WHERE id = ?";

    private static final String GET_VISITE_BY_SSP_PAGED = "SELECT * FROM visita_ssp WHERE id_ssp = ? LIMIT ? OFFSET ?";
    private static final String GET_COUNT_VISITE_BY_SSP = "SELECT COUNT(*) FROM visita_ssp WHERE id_ssp = ?";

    private static final String GET_VISITE_BY_PAZIENTE_COUNT = "SELECT COUNT(id) as count FROM visita_ssp v WHERE v.id_paziente = ?";
    private static final String GET_VISITE_FUTURE_COUNT = "SELECT COUNT(id) as count FROM visita_ssp v WHERE v.id_paziente = ? AND v.erogata = 'false'";

    private UserDAO userDAO;
    private PazienteDAO pazienteDAO;
    private SSPDAO sspDAO;
    private VisitePossibiliDAO visitePossibiliDAO;

    public VisitaSSPDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            sspDAO = daoFactory.getDAO(SSPDAO.class);
            visitePossibiliDAO = daoFactory.getDAO(VisitePossibiliDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long getVisiteByPazienteCount(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_VISITE_BY_PAZIENTE_COUNT))){
            preparedStatement.setInt(1,idp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visitaSSP", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public VisitaSSP getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(GET_VISITA_BY_ID)) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                if(rs.next()){
                    return getFromResultSet(rs);
                }else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the medico base for the passed primary key", ex);
        }
    }

    @Override
    public List<VisitaSSP> getAll() throws DAOException {
        return null;
    }

    @Override
    public ArrayList<VisitaSSP> getVisiteBySSPPaged(int idSSP, PaginationParameters pageParams) throws DAOException {
        ArrayList<VisitaSSP> visite = new ArrayList<>();


        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BY_SSP_PAGED)){
            preparedStatement.setInt(1, idSSP);
            preparedStatement.setInt(2, pageParams.getPageSize());
            preparedStatement.setInt(3, (pageParams.getPage()-1)*pageParams.getPageSize());

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaSSP visita = getFromResultSet(resultSet);
                    visite.add(visita);
                }
            }
        } catch (SQLException | DAOException ex) {
            throw new DAOException("Impossible to get the list of visite ssp", ex);
        }

        return visite;
    }

    @Override
    public Long getVisiteBySSPCount(int idSSP) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_COUNT_VISITE_BY_SSP)){
            preparedStatement.setInt(1, idSSP);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of esami by ssp", ex);
        }
    }

    private VisitaSSP getFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
        Paziente paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
        User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico_base"));
        SSP ssp = sspDAO.getByPrimaryKey(resultSet.getInt("id_ssp"));
        VisitaPossibile tipoVisita = visitePossibiliDAO.getByPrimaryKey(resultSet.getInt("id_visita"));


        return new VisitaSSP(
                resultSet.getInt("id"),
                paziente,
                resultSet.getDate("data_erogazione"),
                tipoVisita,
                resultSet.getBoolean("erogata"),
                resultSet.getDate("data_prescrizione"),
                ssp,
                medicoBase
        );
    }

    @Override
    public Long getCountVisiteFutureByPaziente(int idp) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement((GET_VISITE_FUTURE_COUNT))){
            preparedStatement.setInt(1,idp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visitaSSP", ex);
        }
    }
}
