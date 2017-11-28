package kz.kbtu.edu.w13;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

@Stateless
@LocalBean
public class MessageProducerBean {
    
    Logger log = Logger.getLogger(getClass());
	
	@Resource(mappedName = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:jboss/exported/jms/queue/MyQueue")
    private Queue queue;

    public void sendTextMessage(String textMessage) {

    try {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(queue);

        TextMessage objMsg = session.createTextMessage();
        objMsg.setText(textMessage);

        messageProducer.send(objMsg);
        
        System.out.println("Sent ObjectMessage to the Queue");
        session.close();

    } catch (JMSException e) {
        log.error("ERROR", e);
    }
}}
