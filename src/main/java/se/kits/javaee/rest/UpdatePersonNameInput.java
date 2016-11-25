package se.kits.javaee.rest;

/**
 * Created by David Chang on 2016-11-23.
 */
public class UpdatePersonNameInput {

    private int id;
    private String name;

    public UpdatePersonNameInput(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
