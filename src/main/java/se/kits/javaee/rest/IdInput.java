package se.kits.javaee.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by David Chang on 2016-11-21.
 */
@XmlRootElement(name = "idinput")
@XmlAccessorType(XmlAccessType.FIELD)
public class IdInput {

    @XmlElement(required=true)
    private int id;

    public IdInput(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
