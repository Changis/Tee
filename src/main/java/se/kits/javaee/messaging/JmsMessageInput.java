package se.kits.javaee.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jmsmessageinput")
@XmlAccessorType(XmlAccessType.FIELD)
public class JmsMessageInput {

    @XmlElement(required=true)
    private String messageForJMS;

    public JmsMessageInput(){}

    public String getMessageForJMS() {
        return messageForJMS;
    }

    public void setMessageForJMS(String messageForJMS) {
        this.messageForJMS = messageForJMS;
    }
}
