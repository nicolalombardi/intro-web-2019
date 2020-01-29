package com.icecoldbier.servlets;

import com.icecoldbier.persistence.dao.exceptions.ProvincieNotMatchingException;
import com.icecoldbier.persistence.dao.implementations.PazienteDAO;
import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import com.icecoldbier.utils.Password;
import com.icecoldbier.utils.Utils;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@WebServlet(name = "PazienteServlet", urlPatterns = {"/paziente/cambia-medico", "/paziente/cambia-password", "/paziente/cambia-foto"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class PazienteServlet extends HttpServlet {

    private PazienteDAO pazienteDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            throw new ServletException("Impossible to get dao factory for paziente or user storage system");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userPath = req.getServletPath();
        HttpSession session =  req.getSession(false);
        User user = (User) session.getAttribute("user");
        String contextPath = Utils.getServletContextPath(req.getServletContext());

        if(userPath.equals("/paziente/cambia-medico")){
            String idp = req.getParameter("idPaziente");
            String idm = req.getParameter("changeMedico");

            if(idp == null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
            }
            if(idm == null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id medico mancante");
            }

            int idPaziente = Integer.parseInt(idp);
            int idMedico = Integer.parseInt(idm);

            if(idPaziente != user.getId()){
                session.setAttribute("errorMessage", "Non puoi modificare il medico di un altro paziente");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/profilo"));
            }else{
                try {
                    User p = userDAO.getByPrimaryKey(idPaziente);
                    User m = userDAO.getByPrimaryKey(idMedico);
                    pazienteDAO.changeMedicoBase(p,m);
                    session.setAttribute("successMessage", "Medico cambiato");
                    resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/profilo"));
                } catch (DAOException | ProvincieNotMatchingException e) {
                    session.setAttribute("errorMessage", "Errore nel cambiare il medico, riprova più tardi");
                    e.printStackTrace();
                }
            }
        }
        if(userPath.equals("/paziente/cambia-password")){
            String changePassword = req.getParameter("cambiaPassword");

            if(changePassword == null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametro mancante");
            }

            if(Integer.parseInt(changePassword) == 1){
                session.invalidate();

                resp.sendRedirect(contextPath + "login?changepassword=true");
            }else{
                String idp = req.getParameter("idPaziente");
                String psw = req.getParameter("password");
                if(idp == null){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id paziente mancante");
                }
                if(psw == null){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password nuova mancante");
                }
                int idPaziente = Integer.parseInt(idp);
                if(idPaziente != user.getId()){
                    session.setAttribute("errorMessage", "Non puoi modificare la password di un altro paziente");
                    resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/profilo"));
                }else{
                    String hashedSaltedPassword = null;
                    try {
                        hashedSaltedPassword = Password.generatePasswordHash(psw);

                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                    try {
                        User p = userDAO.getByPrimaryKey(idPaziente);
                        userDAO.changePassword(idPaziente, hashedSaltedPassword);
                        session.setAttribute("successMessage", "Password aggiornata");
                        session.removeAttribute("changePassword");
                        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/profilo"));
                    } catch (DAOException e) {
                        session.setAttribute("errorMessage", "Errore nel cambiare la password, riprova più tardi");
                        e.printStackTrace();
                    }
                }
            }


        }else if(userPath.equals("/paziente/cambia-foto")){
            //Ottieni i percorsi per il salvataggio delle foto
            String photosFolderPath = getServletContext().getInitParameter("photosFolder");
            String resizedPhotosFolderPath = getServletContext().getInitParameter("resizedPhotosFolder");

            if (photosFolderPath == null || resizedPhotosFolderPath == null) {
                throw new ServletException("Photos folder not configured");
            }

            //Impostali correttamente
            photosFolderPath = getServletContext().getRealPath(photosFolderPath);
            resizedPhotosFolderPath = getServletContext().getRealPath(resizedPhotosFolderPath);

            File photosFolder = new File(photosFolderPath);

            Part filePart = req.getPart("photo");

            //Controllo dimesione foto
            long maxSizeInBytes = 1024 * 1024 * 10;
            if(filePart.getSize() > maxSizeInBytes){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "La dimensione dell'immagine deve essere inferiore a 10MB");
                return;
            }

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            fileName = new Date().getTime() + "_" + fileName;

            File photo = new File(photosFolder, fileName);

            //Salva la foto
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, photo.toPath());

                //Salva la copia ridimensionata
                File resizedPhotoFile = new File(resizedPhotosFolderPath, fileName);
                BufferedImage resizedImage = Utils.resize(photo.toPath().toString(), 128);
                String formatName = fileName.substring(fileName
                        .lastIndexOf(".") + 1);
                ImageIO.write(resizedImage, formatName, resizedPhotoFile);
            }
            try {
                pazienteDAO.changeProfilePicture(user.getId(), "/photos/" + fileName);
            } catch (DAOException e) {
                e.printStackTrace();
            }
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "paziente/profilo"));

        }
    }
}
