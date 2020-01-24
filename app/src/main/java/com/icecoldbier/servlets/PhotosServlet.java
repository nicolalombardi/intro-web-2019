package com.icecoldbier.servlets;

import com.icecoldbier.utils.Utils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/photos/*")
public class PhotosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        boolean thumbnail = (request.getParameter("size") != null && request.getParameter("size").equals("thumbnail"));

        String folderPath;
        if(thumbnail){
            String resizedPhotosFolderPath = getServletContext().getInitParameter("resizedPhotosFolder");
            if (resizedPhotosFolderPath == null) {
                throw new ServletException("Resized photos folder not configured");
            }
            folderPath = getServletContext().getRealPath(resizedPhotosFolderPath);

        }else{
            String photosFolderPath = getServletContext().getInitParameter("photosFolder");
            if (photosFolderPath == null) {
                throw new ServletException("Resized photos folder not configured");
            }
            folderPath = getServletContext().getRealPath(photosFolderPath);

        }
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File photoToServe = new File(folderPath, filename);

        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(photoToServe.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + photoToServe.getName() + "\"");

        Files.copy(photoToServe.toPath(), response.getOutputStream());
    }

}
