/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Lab 12 - Shopping List Implementation
 * UniTN
 */
package com.icecoldbier.servlets;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.utils.PDStreamUtils;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.entities.Ticket;
import com.icecoldbier.persistence.entities.User;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

@WebServlet("/paziente/tickets/pdf")
public class ListaTicketsPDF extends HttpServlet {

    PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for paziente storage system");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User u = (User) request.getSession().getAttribute("user");


        ArrayList<Ticket> tickets;
        try {
            tickets = pazienteDAO.getTickets(u.getId());
        } catch (DAOException e) {
            throw new ServletException("Error while accessing DAO");
        }

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {

                PDStreamUtils.write(
                        contents,
                        "Lista dei ticket di " + u.getNome() + " " + u.getCognome(),
                        PDType1Font.TIMES_ROMAN,
                        22,
                        80,
                        700,
                        Color.BLACK);
                PDStreamUtils.write(
                        contents,
                        "La seguente tabella contiene i dettagli dei ticket pagati dal paziente",
                        PDType1Font.TIMES_ROMAN,
                        14,
                        80,
                        675,
                        Color.BLACK);

                float margin = 80;
                float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
                float tableWidth = page.getMediaBox().getWidth() - (2 * margin);


                boolean drawContent = true;
                float bottomMargin = 70;
                float yPosition = 660;

                BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);

                Row<PDPage> header = table.createRow(20);
                header.createCell(4, "#");
                header.createCell(15, "Data");
                header.createCell(15, "Tipo");
                header.createCell(72, "Visita");
                header.createCell(10, "Costo");
                for (Cell<PDPage> cell : header.getCells()) {
                    cell.setFont(PDType1Font.TIMES_BOLD);
                }
                table.addHeaderRow(header);

                Color oddBG = new Color(180, 180, 180);
                for (int i = 0; i < tickets.size(); i++) {
                    Ticket t = tickets.get(i);
                    Row<PDPage> row = table.createRow(0);
                    row.createCell(String.valueOf(i + 1));
                    row.createCell(t.getData().toString());
                    row.createCell(t.getTipoVisita());
                    row.createCell(t.getNomeVisita());
                    row.createCell(String.valueOf(t.getCosto()));
                    for (Cell<PDPage> cell : row.getCells()) {
                        cell.setFont(PDType1Font.TIMES_ROMAN);
                        if (i % 2 == 0) {
                            cell.setFillColor(oddBG);
                        }
                    }
                }

                table.draw();
            }

            String name = "Lista_tickets_" + u.getNome() + "_" + u.getCognome() + ".xls";
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + name);
            doc.save(response.getOutputStream());
        }
    }
}
