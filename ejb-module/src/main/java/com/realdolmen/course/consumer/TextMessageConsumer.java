package com.realdolmen.course.consumer;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/rd/queues/RealDolmenQueue"),
})
public class TextMessageConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("**********************");
            System.out.println(textMessage.getText());
            System.out.println("**********************");
        } catch (JMSException e) {
            throw new RuntimeException("Deal with this", e);
        }
    }
}
