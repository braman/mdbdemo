package kz.kbtu.edu.w13;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
@LocalBean
public class MailServiceBean {

    @Resource(name = "java:jboss/mail/MyMail")
    Session mailRuSession;
    
    
    public void sendMail(String receiver, String subject, String text) throws AddressException, MessagingException {
        Message msg = new MimeMessage(mailRuSession);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        msg.setSubject(subject);
        msg.setText(text);
        
        Transport.send(msg);
        
    }
}
