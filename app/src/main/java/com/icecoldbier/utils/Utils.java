package com.icecoldbier.utils;

import javax.servlet.ServletContext;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Utils {
    public static String getServletContextPath(ServletContext context){
        String contextPath = context.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        return contextPath;
    }

    /**
     * Metodo che consente di inviare una mail
     * @param to Indirizzo email del destinatario
     * @param subject Oggetto della mail
     * @param text Testo della mail
     * @throws MessagingException
     */
    public static void sendMail(final String to, final String subject, final String text) throws MessagingException {
        final String accountEmail = "progettoweb0@gmail.com";
        final String accountPassword = "icecoldbier";
        final String senderAddress = "progettoweb0+admin@gmail.com";

        final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Properties prop = new Properties();
                    prop.put("mail.smtp.port", 587);
                    prop.put("mail.smtp.host", "smtp.gmail.com");
                    prop.put("mail.debug", "true");
                    prop.setProperty("mail.smtp.auth", "true");
                    prop.put("mail.smtp.starttls.enable", "true");

                    Authenticator auth = new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(accountEmail, accountPassword);
                        }
                    };

                    Session session = Session.getInstance(prop, auth);
                    InternetAddress[] mailTo = InternetAddress.parse(to);
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderAddress));
                    message.setRecipients(Message.RecipientType.TO, mailTo);
                    message.setSubject(subject);
                    message.setSentDate(new Date());
                    message.setText(text);

                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        emailExecutor.shutdown();
    }

    public static int coerceInt(int min, int max, int value){
        if(value <= min) return min;
        if(value > max) return max;
        return value;
    }
}
