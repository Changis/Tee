package se.kits.javaee.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by David Chang on 2016-11-17.
 */
@XmlRootElement(name = "taskinput")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskInput {

    @XmlElement(required=true)
    private String taskDescription;

    public TaskInput(){}


    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
