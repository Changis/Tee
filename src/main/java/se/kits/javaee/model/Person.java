package se.kits.javaee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by David Chang on 2016-10-18.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "findUserById",
                query = "SELECT p " +
                        "FROM Person p " +
                        "WHERE p.personid = :id"
        ),
        @NamedQuery(
                name = "findUserByName",
                query = "SELECT p " +
                        "FROM Person p " +
                        "WHERE p.personname = :name"
        )
})
@Table(name = "person")
public class Person implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "personid")
    private int personid;

    @Column(name = "personname")
    @NotNull
    private String personname;

    @ManyToOne
    @JoinColumn(name="teamid")
    private Team team;

    /*@ManyToOne
    @JoinColumn(name="teamid")
    private int teamid;*/

    public Person(){}

    public Person(String personname){
        this.personname = personname;
    }

    public Person(String personname, int teamid){
        this.personname = personname;
//        this.teamid = teamid;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    /*public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }*/

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
