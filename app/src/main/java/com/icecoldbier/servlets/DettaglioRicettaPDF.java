/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Lab 12 - Shopping List Implementation
 * UniTN
 */
package com.icecoldbier.servlets;

import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PDStreamUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.dao.implementations.RicettaDAO;
import com.icecoldbier.persistence.dao.implementations.VisitaBaseDAO;
import com.icecoldbier.persistence.dao.implementations.VisitaSpecialisticaDAO;
import com.icecoldbier.persistence.entities.*;
import com.icecoldbier.utils.Utils;
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
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/paziente/stampa-ricetta")
public class DettaglioRicettaPDF extends HttpServlet {


    private static final int TITLE_LEFT_MARGIN = 80;
    private static final int TITLE_FONT_SIZE = 20;

    private static final int SUBTITLE_FONT_SIZE = 14;
    private static final int SUBTITLE_LEFT_MARGIN = 90;

    private static final int CONTENT_FONT_SIZE = 14;
    private static final int CONTENT_LEFT_MARGIN = 300;


    PazienteDAO pazienteDAO;
    RicettaDAO ricettaDAO;
    VisitaBaseDAO visitaBaseDAO;
    VisitaSpecialisticaDAO visitaSpecialisticaDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            ricettaDAO = daoFactory.getDAO(RicettaDAO.class);
            visitaBaseDAO = daoFactory.getDAO(VisitaBaseDAO.class);
            visitaSpecialisticaDAO = daoFactory.getDAO(VisitaSpecialisticaDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for paziente storage system");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ricetteUrl = "http://192.168.1.185:8080/paziente/elenco-prescrizioni-ricette";
        int idRicetta = Integer.parseInt(request.getParameter("idRicetta"));
        HttpSession session = request.getSession(false);
        String contextPath = Utils.getServletContextPath(request.getServletContext());
        User user = (User)session.getAttribute("user");
        ArrayList<InfoRicetta> listaricette = null;
        boolean ok = false;
        try {
            listaricette = pazienteDAO.getRicette(user.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        for (InfoRicetta i:listaricette) {
            if(i.getId()==idRicetta){
                ok = true;
            }
        }
        if(ok){
            try (PDDocument doc = new PDDocument()) {
                PDPage page = new PDPage();
                doc.addPage(page);

                try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {

                    Ricetta ricetta;

                    try {
                        ricetta = ricettaDAO.getByPrimaryKey(idRicetta);

                        VisitaSpecialistica visitaSpecialistica = visitaSpecialisticaDAO.getContainingRicetta(ricetta.getId());
                        VisitaBase visitaBase = visitaBaseDAO.getContainingRicetta(ricetta.getId());
                        if (visitaBase != null) {
                            drawTitle(contents, "Ricetta", 650);

                            drawSubtitle(contents, "Descrizione farmaco: ", 615);
                            drawContent(contents, visitaBase.getRicetta().getNome(), 615);

                            drawSubtitle(contents, "Codice QR:", 590);

                            Image qrImage = getQRCode(ricetteUrl + "#modaleRicetta" + ricetta.getId());
                            qrImage.draw(doc, contents, CONTENT_LEFT_MARGIN - 9, 600);


                            drawTitle(contents, "Paziente", 500);

                            drawSubtitle(contents, "Nome e cognome: ", 465);
                            drawContent(contents, visitaBase.getPaziente().getNome() + " " + visitaBase.getPaziente().getCognome(), 465);

                            drawSubtitle(contents, "Codice fiscale: ", 440);
                            drawContent(contents, visitaBase.getPaziente().getCodiceFiscale(), 440);

                            drawTitle(contents, "Info erogazione ", 390);

                            drawSubtitle(contents, "Medico base: ", 355);
                            drawContent(contents, visitaBase.getMedicoBase().toStringNomeCognome(), 355);

                            drawSubtitle(contents, "Data e ora erogazione: ", 330);
                            drawContent(contents, visitaBase.getPrettyDataErogazione(), 330);

                        } else if (visitaSpecialistica != null) {
                            drawTitle(contents, "Ricetta", 650);

                            drawSubtitle(contents, "Descrizione farmaco: ", 615);
                            drawContent(contents, visitaSpecialistica.getReport().getRicetta().getNome(), 615);

                            drawSubtitle(contents, "Codice QR:", 590);

                            Image qrImage = getQRCode(ricetteUrl + "#modaleRicetta" + ricetta.getId());
                            qrImage.draw(doc, contents, CONTENT_LEFT_MARGIN - 9, 600);


                            drawTitle(contents, "Paziente", 500);

                            drawSubtitle(contents, "Nome e cognome: ", 465);
                            drawContent(contents, visitaSpecialistica.getPaziente().getNome() + " " + visitaSpecialistica.getPaziente().getCognome(), 465);

                            drawSubtitle(contents, "Codice fiscale: ", 440);
                            drawContent(contents, visitaSpecialistica.getPaziente().getCodiceFiscale(), 440);

                            drawTitle(contents, "Info erogazione ", 390);

                            drawSubtitle(contents, "Medico base che ha approvato: ", 355);
                            drawContent(contents, visitaSpecialistica.getMedicoBase().toStringNomeCognome(), 355);

                            drawSubtitle(contents, "Medico specialista che ha erogato: ", 330);
                            drawContent(contents, visitaSpecialistica.getMedicoSpecialista().toStringNomeCognome(), 330);

                            drawSubtitle(contents, "Data prescrizione: ", 305);
                            drawContent(contents, visitaSpecialistica.getPrettyDataPrescrizione(), 305);

                            drawSubtitle(contents, "Data erogazione: ", 280);
                            drawContent(contents, visitaSpecialistica.getPrettyDataErogazione(), 280);

                        } else {
                            drawString(contents, "Errore: Ricetta non trovata", 20, 80, 500);
                        }


                    } catch (DAOException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore con l'id");
                        e.printStackTrace();
                        return;
                    }
                }

                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=dettaglio_ricetta.pdf");
                doc.save(response.getOutputStream());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            session.setAttribute("errorMessage", "Non puoi vedere i dettagli di una ricetta che non ti riguarda");
            response.sendRedirect(response.encodeRedirectURL(contextPath + "paziente/elenco-prescrizioni-ricette"));
        }

    }

    private void drawTitle(PDPageContentStream contents, String text, int y) {
        drawString(contents, text, TITLE_FONT_SIZE, TITLE_LEFT_MARGIN, y);
    }

    private void drawSubtitle(PDPageContentStream contents, String text, int y) {
        drawString(contents, text, SUBTITLE_FONT_SIZE, SUBTITLE_LEFT_MARGIN, y);
    }

    private void drawContent(PDPageContentStream contents, String text, int y) {
        drawString(contents, text, CONTENT_FONT_SIZE, CONTENT_LEFT_MARGIN, y);
    }

    private void drawString(PDPageContentStream contents, String string, int fontSize, int x, int y) {
        PDStreamUtils.write(
                contents,
                string,
                PDType1Font.TIMES_ROMAN,
                fontSize,
                x,
                y,
                Color.BLACK);
    }


    private Image getQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix qrMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(qrMatrix);
        return new Image(qrImage);
    }
}
