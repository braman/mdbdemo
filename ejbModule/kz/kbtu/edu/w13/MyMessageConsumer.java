package kz.kbtu.edu.w13;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;

import org.jboss.logging.Logger;

@MessageDriven(activationConfig = { 
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/MyQueue")
})
public class MyMessageConsumer implements MessageListener {

    Logger log = Logger.getLogger(getClass());
    
    @EJB
    MailServiceBean mailService;
    
    public MyMessageConsumer() {
    }
	
    public void onMessage(Message message) {
        try {
            TextMessage txt = (TextMessage) message;
            log.info("Message received - " + txt.getText());
            
            try {
                mailService.sendMail("owl.almaty@gmail.com", "Hello", txt.getText());
            } catch (MessagingException e) {
                log.error("Failed to send email");
            }
            
        } catch (JMSException e) {
            log.error("jms error", e);
        }
    }
}
