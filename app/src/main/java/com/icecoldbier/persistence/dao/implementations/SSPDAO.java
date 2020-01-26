package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.SSPDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SSPDAO extends JDBCDAO<SSP, Integer> implements SSPDAOInterface {
    private static final String GET_ALL_SSP = "SELECT * FROM users WHERE typ = 'ssp'";
    private static final String EROGA_VISITA_BY_ID = "UPDATE visita_ssp SET erogata = TRUE, data_erogazione = NOW() WHERE id = ?";
    private static final String GET_INFO_RICETTE_BY_DATA_PROVINCIA = "" +
            "SELECT r.farmaco AS farmaco, vb.data_erogazione AS data_erogazione, vb.id_medico AS medico_base, vb.id_paziente AS paziente \n" +
            "FROM ricetta r, visita_base vb, users p\n" +
            "WHERE vb.id_ricetta = r.id AND p.id = vb.id_paziente AND data_erogazione = ? AND p.provincia_appartenenza = ?\n" +
            "UNION\n" +
            "SELECT r.farmaco AS farmaco, vs.data_erogazione AS data_erogazione, vs.id_medico_base AS medico_base, vs.id_paziente AS paziente \n" +
            "FROM ricetta r, visita_specialistica vs, report rep, users p\n" +
            "WHERE r.id = rep.id_ricetta AND vs.id_report = rep.id AND vs.id_paziente = p.id AND data_erogazione = ? AND p.provincia_appartenenza = ?";
    private static final String GET_SSP_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String GET_VISITE_BY_DATA = "SELECT v.id FROM visita_ssp v WHERE v.id_ssp = ? AND v.data_erogazione = ? ";
    private UserDAO userDAO;

    public SSPDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
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
        ArrayList<InfoRicetta> listaInfoRicette = new ArrayList<>();
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_INFO_RICETTE_BY_DATA_PROVINCIA)) {
            String provinciaAppartenenza = ssp.getProvinciaAppartenenza();
            preparedStatement.setDate(1, data);
            preparedStatement.setString(2, provinciaAppartenenza);
            preparedStatement.setDate(3, data);
            preparedStatement.setString(4, provinciaAppartenenza);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("medico_base"));
                Paziente paziente = JDBCDAOFactory.getInstance().getDAO(PazienteDAO.class).getByPrimaryKey(resultSet.getInt("paziente"));
                InfoRicetta infoRicetta =  new InfoRicetta(
                        resultSet.getDate("data_erogazione"),
                        resultSet.getString("farmaco"),
                        medicoBase,
                        paziente
                );
                listaInfoRicette.add(infoRicetta);
            }
        } catch (SQLException | DAOFactoryException e) {
            throw new DAOException("Error while getting lista ricette erogate in un giorno e per una provincia ", e);
        }
        return listaInfoRicette;

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
                        resultSet.getString("username"),
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
        List<SSP> ssp = new ArrayList<>();
        try(Statement stm = CON.createStatement()) {
            ResultSet rs = stm.executeQuery(GET_ALL_SSP);
            while (rs.next()){
                SSP s = new SSP(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("provincia_appartenenza")
                );
                ssp.add(s);
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting list of ssp", e);
        }
        return ssp;
    }

    @Override
    public ArrayList<VisitaSSP> getListaVisite(Date data, SSP ssp) throws DAOException {
        ArrayList<VisitaSSP> listaVisite = new ArrayList<>();
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_BY_DATA)) {
            preparedStatement.setInt(1, ssp.getId());
            preparedStatement.setDate(2, data);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                listaVisite.add(JDBCDAOFactory.getInstance().getDAO(VisitaSSPDAO.class).getByPrimaryKey(resultSet.getInt("id")));
            }
        } catch (SQLException | DAOFactoryException e) {
            throw new DAOException("Error while getting lista ricette erogate in un giorno e per una provincia ", e);
        }
        return listaVisite;
    }
}
