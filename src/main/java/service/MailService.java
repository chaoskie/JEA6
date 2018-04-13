package service;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import java.io.UnsupportedEncodingException;

// Source: https://medium.com/@swhp/sending-email-with-payara-and-gmail-56b0b5d56882

@Stateless
public class MailService {
    @Resource(name = "java/mail/kwetter")
    Session mailSession;

    @Asynchronous
    public void sendEmail(String recipientMail, String subject, String content) throws MessagingException, UnsupportedEncodingException

    {
        Message message = new MimeMessage(mailSession);

        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientMail, recipientMail));
        message.setContent(content, "text/html; charset=utf-8");

        Transport.send(message);
    }
}

