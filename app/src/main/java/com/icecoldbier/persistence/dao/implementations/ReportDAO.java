package com.icecoldbier.persistence.dao.implementations;

import com.icecoldbier.persistence.dao.interfaces.ReportDAOInterface;
import com.icecoldbier.persistence.entities.Report;
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
        try(PreparedStatement preparedStatement = CON.prepareStatement(GET_REPORT_BY_ID)){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                Report r = new Report(
                        resultSet.getInt("id"),
                        resultSet.getString("esito"),
                        resultSet.getInt("id_ricetta")
                );
                return r;
            }

        } catch (SQLException e) {
            throw new DAOException("Error while getting report", e);
        }
    }

    @Override
    public List<Report> getAll() throws DAOException {
        return null;
    }
}
