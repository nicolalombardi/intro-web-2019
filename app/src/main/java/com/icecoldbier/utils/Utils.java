package com.icecoldbier.utils;

import javax.servlet.ServletContext;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class Utils {
    public static String getServletContextPath(ServletContext context){
        String contextPath = context.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        return contextPath;
    }

    // "uname" e "to" sono indirizzi email
    public void sendMail(final String uname, final String psw, String to, String subject, String text) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.debug", "true");
        prop.setProperty("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, psw);
            }
        };

        Session session = Session.getInstance(prop, auth);
        InternetAddress[] mailTo = InternetAddress.parse(to);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(uname));
        message.setRecipients(Message.RecipientType.TO, mailTo);
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setText(text);

        Transport.send(message);
    }
}
