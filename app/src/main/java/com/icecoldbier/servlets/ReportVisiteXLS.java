package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.implementations.SSPDAO;
import com.icecoldbier.persistence.entities.InfoRicetta;
import com.icecoldbier.persistence.entities.SSP;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.persistence.entities.VisitaSSP;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/ssp/genera-report-visite")


public class ReportVisiteXLS extends HttpServlet {
    SSPDAO sspDAO = null;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            sspDAO = daoFactory.getDAO(SSPDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for ssp storage system");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String xlsFolder = getServletContext().getInitParameter("xlsFolder");
        if (xlsFolder == null) {
            throw new ServletException("XLSs folder not configured");
        }
        xlsFolder = getServletContext().getRealPath(xlsFolder);

        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");
        String dateS = req.getParameter("data");
        System.out.println(dateS);

        if(dateS.trim().equals("")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data non inserita");
            return;
        }

        SSP ssp;
        try {
            ssp = sspDAO.getByPrimaryKey(u.getId());
        } catch (DAOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return;
        }


        java.sql.Date data = null;
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(dateS);
            data = new java.sql.Date(d.getTime());
        } catch (ParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato data non valido");
            e.printStackTrace();
            return;
        }

        ArrayList<VisitaSSP> list = new ArrayList<>();
        try {
            list = sspDAO.getListaVisite(data, ssp);
            System.out.println(list);
        } catch (DAOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return;
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        String name = "Elenco_visite_" + data.toString() + ".xls";
        XSSFSheet sheet = workbook.createSheet(name);

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);


        int rowCount = 0;
        Row r0 = sheet.createRow(0);
        Cell c0 = r0.createCell(0);
        c0.setCellValue("TIPO VISITA");
        c0.setCellStyle(cellStyle);
        Cell c1 = r0.createCell(1);
        c1.setCellValue("DATA EROGAZIONE");
        c1.setCellStyle(cellStyle);
        Cell c2 = r0.createCell(2);
        c2.setCellValue("COSTO");
        c2.setCellStyle(cellStyle);
        Cell c3 = r0.createCell(3);
        c3.setCellValue("PAZIENTE");
        c3.setCellStyle(cellStyle);
        Cell c4 = r0.createCell(4);
        c4.setCellValue("MEDICO ORDINANTE");
        c4.setCellStyle(cellStyle);

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (VisitaSSP i : list) {
            Row row = sheet.createRow(++rowCount);
            CellStyle rowStyle = sheet.getWorkbook().createCellStyle();
            rowStyle.setAlignment(HorizontalAlignment.CENTER);
            row.setRowStyle(rowStyle);
            row.createCell(0).setCellValue(i.getTipo_visita().getNome());
            Cell cell = row.createCell(1);
            cell.setCellValue(i.getDataErogazione());
            cell.setCellStyle(dateCellStyle);
            row.createCell(2).setCellValue(11);
            row.createCell(3).setCellValue(i.getPaziente().toStringNomeCognome());
            row.createCell(4).setCellValue(i.getMedicoBase().toStringNomeCognome());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        File fold = new File(xlsFolder);

        File result = new File(fold, name);
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-disposition", "attachment; filename=" + name + "");
        try (FileOutputStream outputStream = new FileOutputStream(result)) {
            workbook.write(outputStream);
            workbook.write(resp.getOutputStream());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
