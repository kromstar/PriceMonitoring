package com.softvision.PriceMonitoring;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static final EmailSender INSTANCE = new EmailSender();
    private Session session;

    private EmailSender() {
        initialize();
    }

    public static EmailSender getInstance() {
        return INSTANCE;
    }

    private void initialize() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("catafrt@gmail.com", "fxlmrupqljupkxrn");
            }
        });
    }

    public void sendMessage(String itemUrl, double oldPrice, double newPrice, String emailAddress) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("catafrt@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject("The price for your item has been modified");
            message.setText("Item:" + itemUrl + "\nOld price:" + oldPrice + "\nNew price:" + newPrice);
            Transport.send(message);
            System.out.println("Sent message successfully.");
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
