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
public class MyQueueConsumerMDB implements MessageListener {

    Logger log = Logger.getLogger(getClass());
    
    @EJB
    MailServiceBean mailService;
    
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public MyQueueConsumerMDB() {
    }
	
    public void onMessage(Message message) {
        try {
            TextMessage txt = (TextMessage) message;
            log.info("Message received - " + txt.getText());
            
            String emailReceiver = txt.getStringProperty("emailReceiver");
            String emailSubject  = txt.getStringProperty("emailSubject");
            String emailText     = txt.getText();
            
            boolean validEmail = emailReceiver != null && emailReceiver.matches(EMAIL_PATTERN);
            
            
            if (!validEmail) {
                log.error("Invalid email receiver" + emailReceiver + ", skipping subject " + emailSubject);
                return;
            }
            
            
            
            try {
                mailService.sendMail(emailReceiver, emailSubject, txt.getText());
            } catch (MessagingException e) {
                log.error("Failed to send email");
            }
            
        } catch (JMSException e) {
            log.error("jms error", e);
        }
    }
}
