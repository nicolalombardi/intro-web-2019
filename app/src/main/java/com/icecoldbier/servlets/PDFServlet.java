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
public class PDFServlet extends HttpServlet {

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
        String pdfFolder = getServletContext().getInitParameter("pdfFolder");
        if (pdfFolder == null) {
            throw new ServletException("PDFs folder not configured");
        }

        //TODO: probabilmente da cambiare quando si farà l'autenticazione nel modo giusto, bisognerà controllare che solo i pazienti possano accedere a questa pagina
//        User user;
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            user = (User) session.getAttribute("user");
//        }else{
//            throw new ServletException("User not logged in");
//        }

        //Per ora passare un id
        int id = Integer.parseInt(request.getParameter("idPaziente"));


        ArrayList<Ticket> tickets;
        try {
            tickets = pazienteDAO.getTickets(id);
        } catch (DAOException e) {
            throw new ServletException("Error while accessing DAO");
        }

        pdfFolder = getServletContext().getRealPath(pdfFolder);


        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {

                PDStreamUtils.write(
                        contents,
                        "Tickets list for patient " + "Nome" + " " + "Cognome",
//                        "Tickets list for patient " + user.getCognome() + " " + user.getNome(),
                        PDType1Font.TIMES_ROMAN,
                        22,
                        80,
                        700,
                        Color.BLACK);
                PDStreamUtils.write(
                        contents,
                        "The following table lists all the tickets paid by the patient",
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
                header.createCell(15, "Date");
                header.createCell(15, "Type");
                header.createCell(72, "Visit");
                header.createCell(10, "Cost");
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

            doc.save(new File(pdfFolder, "user-" + "prova" + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf"));

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=prova.pdf");
            doc.save(response.getOutputStream());
        }

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        response.sendRedirect(response.encodeRedirectURL(contextPath + "test"));
    }
}
