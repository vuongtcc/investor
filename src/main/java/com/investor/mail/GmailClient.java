package com.investor.mail;

import com.investor.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;

public class GmailClient {

    private static final Logger logger = LogManager.getLogger(GmailClient.class);
    private static String host = Main.p.getProperty("gmail.host");
    private static String user = Main.p.getProperty("gmail.from");
    private static String pass = Main.p.getProperty("gmail.pw");
    private static String emailTo = Main.p.getProperty("gmail.to");

    public static void sendStandard(String subject, String body) {
        GmailClient client = new GmailClient();
        try {
            logger.info("====Start send mail=====");
            logger.info(subject);
            logger.info(body);
            client.sendMailAttach(subject, emailTo, null, null, body, null);
            logger.info("==========END=========");
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public void sendMailAttach(String subject, String to, String cc, String bcc, String body, String[] attachments) throws MessagingException, AuthenticationFailedException {
        try {
            boolean debug = false;
            //Set the host smtp address
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);

            session.setDebug(debug);
            // create a message
            Message msg = new MimeMessage(session);

            // set the from and to address
            InternetAddress addressFrom = new InternetAddress(user);
            msg.setFrom(addressFrom);

            String[] tos = to.split(",|;");
            for (int i = 0; i < tos.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(tos[i]));
            }
            if (cc != null && !"".equals(cc)) {
                String[] ccs = cc.split(",|;");
                for (int i = 0; i < ccs.length; i++) {
                    msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccs[i]));
                }
            }
            if (bcc != null && !"".equals(bcc)) {
                String[] bccs = bcc.split(",|;");
                for (int i = 0; i < bccs.length; i++) {
                    msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccs[i]));
                }
            }
            // Create a message part to represent the body text
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html;charset=UTF-8");

            //use a MimeMultipart as we need to handle the file attachments
            Multipart multipart = new MimeMultipart();

            //add the message body to the mime message
            multipart.addBodyPart(messageBodyPart);

            if (attachments != null && attachments.length > 0) {
                // add any file attachments to the message
                addAtachments(attachments, multipart);
            }


            // Setting the Subject and Content Type
            msg.setSubject(subject);

            // Put all message parts in the message
            msg.setContent(multipart);

            Transport.send(msg);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    protected void addAtachments(String[] attachments, Multipart multipart)
            throws MessagingException, AddressException {
        for (int i = 0; i <= attachments.length - 1; i++) {
            String filename = attachments[i];
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();

            //use a JAF FileDataSource as it does MIME type detection
            DataSource source = new FileDataSource(filename);
            attachmentBodyPart.setDataHandler(new DataHandler(source));

            //assume that the filename you want to send is the same as the
            //actual file name - could alter this to remove the file path
            int charLast = 0;
            if (filename.contains("\\")) {
                charLast = filename.lastIndexOf("\\");
            } else if (filename.contains("/")) {
                charLast = filename.lastIndexOf("/");
            }

            attachmentBodyPart.setFileName(filename.substring(charLast + 1, filename.length()));

            //add the attachment
            multipart.addBodyPart(attachmentBodyPart);
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = user;
            String password = pass;
            return new PasswordAuthentication(username, password);
        }
    }


}
