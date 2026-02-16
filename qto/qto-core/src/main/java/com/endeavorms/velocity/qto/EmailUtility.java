package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.config.ConfigPropertyManager;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Component
public class EmailUtility {

    @Autowired
    private ConfigPropertyManager configPropertyManager;

    public void sendEmail(String subject, String msg, List<String> recipients) {
        String host = configPropertyManager.findByKey("SMTP_HOSTNAME").getValue();
        String port = configPropertyManager.findByKey("SMTP_PORT_NUMBER").getValue();
        String user = configPropertyManager.findByKey("SMTP_USERNAME").getValue();
        String password = configPropertyManager.getDecryptedString("SMTP_PASSWORD");

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress("i90@vertek.com"));

            // Set To: header field of the header.
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
//            message.setText(msg);
            message.setContent(msg, "text/html; charset=UTF-8");


            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
