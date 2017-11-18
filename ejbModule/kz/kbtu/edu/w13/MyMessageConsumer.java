package kz.kbtu.edu.w13;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

@MessageDriven(activationConfig = { 
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/MyQueue")
})
public class MyMessageConsumer implements MessageListener {

    Logger log = Logger.getLogger(getClass());
    
    public MyMessageConsumer() {
    }
	
    public void onMessage(Message message) {
        try {
            TextMessage txt = (TextMessage) message;
            log.info("getJMSCorrelationID - " + txt.getText());
        } catch (JMSException e) {
            log.error("error", e);
        }
    }
}
