package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.ReportDAOInterface;
import com.icecoldbier.persistence.entities.Report;
import com.icecoldbier.persistence.entities.Ricetta;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.jdbc.JDBCDAO;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReportDAO extends JDBCDAO<Report, Integer> implements ReportDAOInterface {
    private static final String GET_REPORT_BY_ID = "SELECT * FROM report WHERE id = ?";
    private static final String GET_RICETTA_BY_ID = "SELECT * FROM ricetta WHERE id = ?";


    /**
     * The base constructor for all the JDBC DAOs.
     *
     * @param con the internal {@code Connection}.
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public ReportDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        return null;
    }

    @Override
    public Report getByPrimaryKey(Integer primaryKey) throws DAOException {
        //report
        Report report = null;
        Ricetta ricetta = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_REPORT_BY_ID)){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                //Se presente un risultato, processalo. Altrimenti null
                if(resultSet.next()){
                    try (PreparedStatement preparedStatement1 = CON.prepareStatement(GET_RICETTA_BY_ID)) {
                        System.out.println("Prima");
                        preparedStatement1.setInt(1, resultSet.getInt("id_ricetta"));
                        System.out.println("Dopo");
                        try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                            if(resultSet1.next()){
                                ricetta = new Ricetta(
                                    resultSet1.getInt("id"),
                                    resultSet1.getString("farmaco"),
                                    resultSet1.getBoolean("prescritta")
                                );
                            }
                        }
                    } catch (SQLException e) {
                        throw new DAOException("Error while getting ricetta", e);
                    }
                    report = new Report(
                            resultSet.getInt("id"),
                            resultSet.getString("esito"),
                            ricetta
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting report", e);
        }
        return report;
    }

    @Override
    public List<Report> getAll() throws DAOException {
        return null;
    }
}
