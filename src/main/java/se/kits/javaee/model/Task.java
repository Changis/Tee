package se.kits.javaee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by David Chang on 2016-11-17.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private int taskId;

    @Column(name = "description")
    @NotNull
    private String description;

    @ManyToMany(targetEntity = se.kits.javaee.model.Person.class, mappedBy="tasks", fetch=FetchType.EAGER)
    @JsonBackReference
    private List<Person> persons;

    public Task(){}

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
