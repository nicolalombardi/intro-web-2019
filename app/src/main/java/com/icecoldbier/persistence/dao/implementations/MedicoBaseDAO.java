package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.MedicoBaseDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoBaseDAO extends JDBCDAO<User, Integer> implements MedicoBaseDAOInterface {

    private static final String GET_USER_LIST = "SELECT id FROM visita_base WHERE id_medico = ?";
    private static final String CREATE_VISITA_BASE = "INSERT INTO visita_base(id_medico, id_paziente, data_erogazione) VALUES(?,?,?)";
    private static final String CREATE_VISITA_SSP = "INSERT INTO visita_ssp(id_visita, erogata, data_prescrizione, id_ssp, id_paziente, id_medico_base) VALUES(?,?,?,?,?,?)";
    private static final String CREATE_VISITA_SPECIALISTICA = "INSERT INTO visita_specialistica(id_visita, erogata, data_prescrizione, id_medico, id_paziente, id_medico_base) VALUES(?,?,?,?,?,?)";
    private static final String GET_VISITA_SPECIALISTICA = "SELECT id_visita, id_report FROM visita_specialistica WHERE id = ? ";



    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public MedicoBaseDAO(Connection con) {
        super(con);
    }


    @Override
    public ArrayList<VisitaBase> getListaPazienti(int id) {
        int change = 0;
        ArrayList<VisitaBase> lista = new ArrayList<>();
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_USER_LIST)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    VisitaBase vis = new VisitaBase(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getDate(4));
                    for (VisitaBase x : lista) {
                        if (x.getId_paziente() == vis.getId_paziente()) {
                            if (x.getDataErogazione().before(vis.getDataErogazione())) {
                                lista.remove(x);
                                lista.add(vis);
                                change = 1;
                            }

                        }
                    }
                    if (change == 0) {
                        lista.add(vis);
                    }
                    change = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void createVisitaBase(int idm, int idp, Date data) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(CREATE_VISITA_BASE)) {
            preparedStatement.setInt(1, idm);
            preparedStatement.setInt(2, idp);
            preparedStatement.setDate(3, data);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DAOException("Error while creating a new visita base", e);
        }
    }

    @Override
    public void prescrizioneEsameSSP(VisitaBase visitaBase, int idSSP, VisitaPossibile vis, Date dataPrescrizione) throws DAOException {


        try (PreparedStatement preparedStatement = CON.prepareStatement(CREATE_VISITA_SSP)) {
            preparedStatement.setInt(1, vis.getId());
            preparedStatement.setBoolean(2, false);
            preparedStatement.setDate(3, dataPrescrizione);
            preparedStatement.setInt(4, idSSP);
            preparedStatement.setInt(5, visitaBase.getId_paziente());
            preparedStatement.setInt(6, visitaBase.getIdMedicoBase());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DAOException("Error while creating a new visita_ssp", e);

        }

    }

    @Override
    public void prescrizioneEsameMS(VisitaBase visitaBase, int idMS, VisitaPossibile vis, Date dataPrescrizione) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(CREATE_VISITA_SPECIALISTICA)) {
            preparedStatement.setInt(1, vis.getId());
            preparedStatement.setBoolean(2, false);
            preparedStatement.setDate(3, dataPrescrizione);
            preparedStatement.setInt(4, idMS);
            preparedStatement.setInt(5, visitaBase.getId_paziente());
            preparedStatement.setInt(6, visitaBase.getIdMedicoBase());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DAOException("Error while creating a new visita_specialistica", e);

        }

    }

    @Override
    public InfoVisita getInfoVisita(int idv) throws DAOException {
        InfoVisita infoVisita = new InfoVisita();
        int id_visita=-1;
        int id_report=-1;
        try (PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITA_SPECIALISTICA)) {
            preparedStatement.setInt(1, idv);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            id_visita = resultSet.getInt("id_visita");
            id_report = resultSet.getInt("id_report");

            //System.out.println(id_visita+"  "+id_report);

            VisitePossibiliDAO vis = new VisitePossibiliDAO(CON);
            //ReportDAO rep = new ReportDAO(CON);
            VisitaPossibile visitaPossibile = vis.getByPrimaryKey(id_visita);
            //Report report = rep.getByPrimaryKey(id_report);
            infoVisita.setNome(visitaPossibile.getNome());
            infoVisita.setDescrizione(visitaPossibile.getDescrizione());

            //infoVisita.setReport(report);
        } catch (SQLException e) {
            throw new DAOException("Error while getting a visita_specialistica", e);
        }
        return infoVisita;

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
}