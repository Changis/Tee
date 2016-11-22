package se.kits.javaee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
    //@JsonBackReference
    @JsonManagedReference
    private Team team;

    @ManyToMany(targetEntity = se.kits.javaee.model.Task.class, fetch=FetchType.EAGER)
    @JoinTable(
            name="persons_tasks",
            joinColumns=@JoinColumn(name="personID", referencedColumnName="personid"),
            inverseJoinColumns=@JoinColumn(name="taskID", referencedColumnName="id"))
    @JsonManagedReference
    private List<Task> tasks;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
