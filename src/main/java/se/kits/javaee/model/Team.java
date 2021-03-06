package se.kits.javaee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by David Chang on 2016-11-02.
 */
@Entity
@Table(name="team")
public class Team implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private int id;

    @Column(name="teamname")
    @NotNull
    private String teamName;

    @Column(name="shortname")
    private String shortName;

//    @OneToMany(mappedBy="team", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @OneToMany(mappedBy="team", fetch=FetchType.EAGER)
    //@JsonManagedReference
    @JsonBackReference
    private List<Person> membersList;

    public Team(){}

    public Team(String name, String shortName){
        this.teamName = name;
        this.shortName = shortName;
    }

    public Team(String name){
        this.teamName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<Person> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Person> membersList) {
        this.membersList = membersList;
    }
}
