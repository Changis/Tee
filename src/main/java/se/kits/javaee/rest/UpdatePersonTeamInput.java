package se.kits.javaee.rest;

/**
 * Created by David Chang on 2016-11-23.
 */
public class UpdatePersonTeamInput {

    private int personId;
    private int teamId;

    public UpdatePersonTeamInput(){}

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
