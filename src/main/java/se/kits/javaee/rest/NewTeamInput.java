package se.kits.javaee.rest;

/**
 * Created by David Chang on 2016-11-23.
 */
public class NewTeamInput {

    private String name;
    private String shortName;

    public NewTeamInput(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
