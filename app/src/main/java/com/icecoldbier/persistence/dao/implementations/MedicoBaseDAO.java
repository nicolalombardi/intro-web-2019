package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.MedicoBaseDAOInterface;
import com.icecoldbier.persistence.entities.*;
import com.icecoldbier.utils.pagination.PaginationParameters;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoBaseDAO extends JDBCDAO<User, Integer> implements MedicoBaseDAOInterface {
    private static final String GET_MEDICO_BY_ID = "SELECT * FROM users WHERE id = ? AND typ = CAST(? AS user_type)";

    private static final String CREATE_VISITA_SPECIALISTICA = "INSERT INTO visita_specialistica(id_visita, erogata, data_prescrizione, id_medico, id_paziente, id_medico_base) VALUES(?,?,NOW(),?,?,?)";
    private static final String CREATE_VISITA_SSP = "INSERT INTO visita_ssp(id_visita, erogata, data_prescrizione, id_ssp, id_paziente, id_medico_base) VALUES(?,?,NOW(),?,?,?)";
    private static final String CREATE_RICETTA = "INSERT INTO ricetta(farmaco, prescritta) VALUES(?,?) RETURNING id";
    private static final String CREATE_VISITA_BASE = "INSERT INTO visita_base(id_medico, id_paziente, data_erogazione, id_ricetta) VALUES(?, ?, NOW(), ?)";

    private static final String APPROVA_RICETTA = "UPDATE ricetta SET prescritta=true WHERE id = ?";

    private static final String GET_VISITE_ESAMI_BY_MEDICO_PAZIENTE_PAGED =
            "SELECT * FROM (\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_medico_base, id_report, NULL AS id_ssp, 'specialistica' AS tipo from visita_specialistica WHERE id_medico_base = ? AND id_paziente = ?\n" +
                    "UNION\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, id_medico_base, NULL AS id_report, id_ssp, 'ssp' AS tipo from visita_ssp WHERE id_medico_base = ? AND id_paziente = ?\n" +
                    ")as visite ORDER BY data_prescrizione LIMIT ? OFFSET ?";
    private static final String GET_COUNT_VISITE_ESAMI_BY_MEDICO_PAZIENTE =
            "SELECT COUNT(*) FROM (\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_medico_base, id_report, NULL AS id_ssp, 'specialistica' AS tipo from visita_specialistica WHERE id_medico_base = ? AND id_paziente = ?\n" +
                    "UNION\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, id_medico_base, NULL AS id_report, id_ssp, 'ssp' AS tipo from visita_ssp WHERE id_medico_base = ? AND id_paziente = ?\n" +
                    ")as visite";

    private static final String GET_VISITE_ESAMI_BY_MEDICO_PAGED =
            "SELECT * FROM (\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_medico_base, id_report, NULL AS id_ssp, 'specialistica' AS tipo from visita_specialistica WHERE id_medico_base = ?\n" +
                    "UNION\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, id_medico_base, NULL AS id_report, id_ssp, 'ssp' AS tipo from visita_ssp WHERE id_medico_base = ?\n" +
                    ")as visite ORDER BY data_prescrizione LIMIT ? OFFSET ?";
    private static final String GET_COUNT_VISITE_ESAMI_BY_MEDICO =
            "SELECT COUNT(*) FROM (\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, id_medico, id_paziente, id_medico_base, id_report, NULL AS id_ssp, 'specialistica' AS tipo from visita_specialistica WHERE id_medico_base = ?\n" +
                    "UNION\n" +
                    "SELECT id, id_visita, erogata, data_prescrizione, data_erogazione, NULL AS id_medico, id_paziente, id_medico_base, NULL AS id_report, id_ssp, 'ssp' AS tipo from visita_ssp WHERE id_medico_base = ?\n" +
                    ")as visite";


    private static final String GET_VISITA_SPECIALISTICA = "SELECT id_visita, id_report FROM visita_specialistica WHERE id = ? ";

    private UserDAO userDAO;
    private ReportDAO reportDAO;
    private PazienteDAO pazienteDAO;
    private SSPDAO sspDAO;
    private VisitePossibiliDAO visitePossibiliDAO;

    public MedicoBaseDAO(Connection con) {
        super(con);
        try {
            JDBCDAOFactory daoFactory = JDBCDAOFactory.getInstance();
            userDAO = daoFactory.getDAO(UserDAO.class);
            reportDAO = daoFactory.getDAO(ReportDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            sspDAO = daoFactory.getDAO(SSPDAO.class);
            visitePossibiliDAO = daoFactory.getDAO(VisitePossibiliDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
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
    public void approvaRicetta(int idRicetta) throws DAOException {
        try (PreparedStatement preparedStatement = CON.prepareStatement(APPROVA_RICETTA)) {
            preparedStatement.setInt(1, idRicetta);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DAOException("Error while approving a ricetta", e);
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
    public ArrayList<VisitaSpecialisticaOrSSP> getVisiteEsamiByMedicoPaged(int idMedico, PaginationParameters pageParams) throws DAOException {
        ArrayList<VisitaSpecialisticaOrSSP> visite = new ArrayList<>();


        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_ESAMI_BY_MEDICO_PAGED)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, idMedico);
            preparedStatement.setInt(3, pageParams.getPageSize());
            preparedStatement.setInt(4, (pageParams.getPage()-1)*pageParams.getPageSize());

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaSpecialisticaOrSSP visita = getVisitaSpecialisticaOrSSPFromResultSet(resultSet);
                    visite.add(visita);
                }
            }
        } catch (SQLException | DAOException ex) {
            throw new DAOException("Impossible to get the list of visite base", ex);
        }

        return visite;
    }

    @Override
    public ArrayList<VisitaSpecialisticaOrSSP> getVisiteEsamiByMedicoAndPazientePaged(int idMedico, int idPaziente, PaginationParameters pageParams) throws DAOException {
        ArrayList<VisitaSpecialisticaOrSSP> visite = new ArrayList<>();


        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_VISITE_ESAMI_BY_MEDICO_PAZIENTE_PAGED)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, idPaziente);
            preparedStatement.setInt(3, idMedico);
            preparedStatement.setInt(4, idPaziente);
            preparedStatement.setInt(5, pageParams.getPageSize());
            preparedStatement.setInt(6, (pageParams.getPage()-1)*pageParams.getPageSize());

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    VisitaSpecialisticaOrSSP visita = getVisitaSpecialisticaOrSSPFromResultSet(resultSet);
                    visite.add(visita);
                }
            }
        } catch (SQLException | DAOException ex) {
            throw new DAOException("Impossible to get the list of visite base", ex);
        }

        return visite;
    }

    @Override
    public Long getVisiteEsamiByMedicoCount(int idMedico) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_COUNT_VISITE_ESAMI_BY_MEDICO)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, idMedico);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visite and esami by medico", ex);
        }
    }

    @Override
    public Long getVisiteEsamiByMedicoAndPazienteCount(int idMedico, int idPaziente) throws DAOException {
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_COUNT_VISITE_ESAMI_BY_MEDICO_PAZIENTE)){
            preparedStatement.setInt(1, idMedico);
            preparedStatement.setInt(2, idPaziente);
            preparedStatement.setInt(3, idMedico);
            preparedStatement.setInt(4, idPaziente);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong("count");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the count of visite and esami by medico and paziente", ex);
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
    private VisitaSpecialisticaOrSSP getVisitaSpecialisticaOrSSPFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
        if(resultSet.getString("tipo").equals("specialistica")){
            Report report = reportDAO.getByPrimaryKey(resultSet.getInt("id_report"));
            Paziente paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
            User medicoSpecialista = userDAO.getByPrimaryKey(resultSet.getInt("id_medico"));
            User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico_base"));
            VisitaPossibile tipoVisita = visitePossibiliDAO.getByPrimaryKey(resultSet.getInt("id_visita"));

            VisitaSpecialistica visitaSpecialistica =  new VisitaSpecialistica(
                    resultSet.getInt("id"),
                    paziente,
                    resultSet.getDate("data_erogazione"),
                    tipoVisita,
                    resultSet.getBoolean("erogata"),
                    resultSet.getDate("data_prescrizione"),
                    medicoSpecialista,
                    report,
                    medicoBase);
            return new VisitaSpecialisticaOrSSP(visitaSpecialistica);
        }else if(resultSet.getString("tipo").equals("ssp")){
            Paziente paziente = pazienteDAO.getByPrimaryKey(resultSet.getInt("id_paziente"));
            User medicoBase = userDAO.getByPrimaryKey(resultSet.getInt("id_medico_base"));
            SSP ssp = sspDAO.getByPrimaryKey(resultSet.getInt("id_ssp"));
            VisitaPossibile tipoVisita = visitePossibiliDAO.getByPrimaryKey(resultSet.getInt("id_visita"));


            VisitaSSP visitaSSP = new VisitaSSP(
                    resultSet.getInt("id"),
                    paziente,
                    resultSet.getDate("data_erogazione"),
                    tipoVisita,
                    resultSet.getBoolean("erogata"),
                    resultSet.getDate("data_prescrizione"),
                    ssp,
                    medicoBase
            );
            return new VisitaSpecialisticaOrSSP(visitaSSP);
        }else{
            return null;
        }
    }
}