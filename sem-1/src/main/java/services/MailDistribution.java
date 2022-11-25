package services;

import database.ConnectionDataSource;
import database.DataBasePostgresInit;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import models.User;
import repositories.UsersRepositoryPostgres;

import java.io.IOException;
import java.util.Properties;

public class MailDistribution {

    private final Properties properties = new Properties();
    private final Session session;

    public MailDistribution(){

        try {
            properties.load(MailDistribution.class.getClassLoader().getResourceAsStream("mail/mail.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        session = Session.getDefaultInstance(properties);
    }

    public boolean sendMessage(String messages, String address){
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress("marsel.gabitov.74@gmail.com"));

            message.setSubject("Новая пропажа!");

            message.setText(messages);

            Transport transport = session.getTransport();
            transport.connect(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException mex){

            mex.printStackTrace();

        }
        return true;
    }
}
