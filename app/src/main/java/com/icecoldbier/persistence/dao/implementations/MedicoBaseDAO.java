package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.MedicoBaseDAOInterface;
import com.icecoldbier.persistence.entities.*;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoBaseDAO extends JDBCDAO<User, Integer> implements MedicoBaseDAOInterface {
    private static final String GET_MEDICO_BY_ID = "SELECT * FROM users WHERE id = ? AND typ = user_type(?)";
    private static final String GET_USER_LIST = "SELECT id FROM visita_base WHERE id_medico = ?";

    private static final String CREATE_VISITA_SPECIALISTICA = "INSERT INTO visita_specialistica(id_visita, erogata, data_prescrizione, id_medico, id_paziente, id_medico_base) VALUES(?,?,NOW(),?,?,?)";
    private static final String CREATE_VISITA_SSP = "INSERT INTO visita_ssp(id_visita, erogata, data_prescrizione, id_ssp, id_paziente, id_medico_base) VALUES(?,?,NOW(),?,?,?)";
    private static final String CREATE_RICETTA = "INSERT INTO ricetta(farmaco, prescritta) VALUES(?,?) RETURNING id";
    private static final String CREATE_VISITA_BASE = "INSERT INTO visita_base(id_medico, id_paziente, data_erogazione, id_ricetta) VALUES(?, ?, NOW(), ?)";

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
    public void erogaVisitaBase(int idMedico, int idPaziente, String ricetta) throws DAOException {
        boolean hasRicetta = ricetta != null;
        int idRicetta = -1;
        try {
            if(hasRicetta){
                PreparedStatement pstm = CON.prepareStatement(CREATE_RICETTA);
                pstm.setString(1, ricetta);
                pstm.setBoolean(2, true);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    idRicetta = rs.getInt("id");;
                }else {
                    throw new DAOException("Error while creating ricetta");
                }

            }
            PreparedStatement pstm = CON.prepareStatement(CREATE_VISITA_BASE);
            pstm.setInt(1, idMedico);
            pstm.setInt(2, idPaziente);
            //Set id ricetta o null
            if(idRicetta != -1){
                pstm.setInt(3, idRicetta);
            }else{
                pstm.setNull(3, Types.INTEGER);
            }
            pstm.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prescrizioneEsameSSP(int idTipoVisita, int idSSP, int idPaziente, int idMedicoBase) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(CREATE_VISITA_SSP)) {
            preparedStatement.setInt(1, idTipoVisita);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setInt(3, idSSP);
            preparedStatement.setInt(4, idPaziente);
            preparedStatement.setInt(5, idMedicoBase);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DAOException("Error while creating a new visita_ssp", e);

        }
    }

    @Override
    public void prescrizioneEsameMS(int idTipoVisita, int idMedicoSpecialista, int idPaziente, int idMedicoBase) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(CREATE_VISITA_SPECIALISTICA)) {
            preparedStatement.setInt(1, idTipoVisita);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setInt(3, idMedicoSpecialista);
            preparedStatement.setInt(4, idPaziente);
            preparedStatement.setInt(5, idMedicoBase);
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

            VisitePossibiliDAO vis = new VisitePossibiliDAO(CON);
            if(id_report > 0){
                ReportDAO rep = new ReportDAO(CON);
                Report report = rep.getByPrimaryKey(id_report);
                infoVisita.setReport(report);
            }
            VisitaPossibile visitaPossibile = vis.getByPrimaryKey(id_visita);
            infoVisita.setNome(visitaPossibile.getNome());
            infoVisita.setDescrizione(visitaPossibile.getDescrizione());
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
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(GET_MEDICO_BY_ID)) {
            stm.setInt(1, primaryKey);
            stm.setString(2, User.UserType.medico_base.name());
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
            throw new DAOException("Impossible to get the medico base for the passed primary key", ex);
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        return null;
    }
}