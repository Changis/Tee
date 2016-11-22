package se.kits.javaee.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by David Chang on 2016-11-18.
 */
@XmlRootElement(name = "usertaskinput")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserTaskInput {
    @XmlElement(required=true)
    private int personId;

    @XmlElement(required=true)
    private int taskId;

    public UserTaskInput(){}

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
