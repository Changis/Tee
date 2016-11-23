package se.kits.javaee.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.*;

/**
 * Created by David Chang on 2016-11-09.
 */
@MessageDriven(mappedName="java:/myJmsTest/MyQueue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:/myJmsTest/MyQueue")
})
public class MsgBean implements MessageListener{

//    @Resource
//    private MessageDrivenContext mdc;

    @Resource(lookup = "java:/myJmsTest/MyConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/myJmsTest/MyQueue")
    private Queue queue;
//    Destination destination --> queue + topic

    private Connection connection;

    public MsgBean(){}

    @PostConstruct
    public void postConstruct(){
        try {
            connection = connectionFactory.createConnection("myJmsUser", "myJmsPassword");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void preDestroy(){
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage tm = (TextMessage) message;
            try{
                System.out.println("Message Received! : " + tm.getText());
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {

            }
        }
    }
}
