package services;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;

/**
 * The java file of a service layer for managing all activities related to a mailing.
 * @author Eunji Elly Lee
 * @version Jan 20, 2022
 */
public class GmailService {
    public static void sendMail(String to, String subject, String template,
            HashMap<String, String> tags) throws Exception {
        String body = "";
        
        try(BufferedReader br = new BufferedReader(new FileReader(new File(template)))) {
            String line = br.readLine();
            
            while(line != null) {
                body += line;
                line = br.readLine();
            }

            for(String key : tags.keySet()) {
                body = body.replace("{{" + key + "}}", tags.get(key));
            }
        } catch(Exception e) {
            Logger.getLogger(GmailService.class.getName()).log(Level.SEVERE, null, e);
        }

        sendMail(to, subject, body, true);
    }

    public static void sendMail(String to, String subject, String body, boolean bodyIsHTML)
            throws MessagingException, NamingException {
        Context env = (Context) new InitialContext().lookup("java:comp/env");
        String username = (String) env.lookup("webmail-username");
        String password = (String) env.lookup("webmail-password");

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setSubject(subject);
        
        if(bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        Address fromAddress = new InternetAddress(username);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}