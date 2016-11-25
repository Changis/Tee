package se.kits.javaee.controller;



import se.kits.javaee.model.Person;
import se.kits.javaee.model.Task;
import se.kits.javaee.model.Team;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by David Chang on 2016-10-17.
 */
@Stateless
//@Stateful
public class PsqlManager {

    //@PersistenceContext(unitName = "postgresjpu", type = PersistenceContextType.EXTENDED)
    @PersistenceContext(unitName = "postgresjpu")
    private EntityManager em;

    public PsqlManager(){}

    public Person registerPerson(String name) {
        Person p = new Person();
        p.setPersonname(name);
        //em.persist(p);
        //em.getTransaction().commit();
        //em.close();
        //return p;
        //return em.merge(new Person(name));
        return em.merge(p);
    }

    public Person getPersonById(int id){
        try{
            return (Person) em.createQuery("select p from Person p where p.personid = :id")
                    .setParameter("id", id).getSingleResult();
        }catch(Exception e){
            System.err.println(e.toString());
            return null;
        }

    }

    public Team getTeamById(int teamid){
        try{
            return (Team) em.createQuery("select t from Team t where t.id = :teamid")
                    .setParameter("teamid", teamid).getSingleResult();
        }catch(Exception ex){
            System.err.println(ex.toString());
        }
        return null;
    }

    public List listAll(){
        return em.createQuery("select p from Person p").getResultList();
    }

    public List listAllTeams(){
        return em.createQuery("select t from Team t").getResultList();
    }

    public List listAllTasks(){
        return em.createQuery("select t from Task t").getResultList();
    }

    public List<Person> listAllMembers(int teamid){
//        Team t = em.find(Team.class, teamid);
        try{
            return ((Team)em.createQuery("select t from Team t where t.id = :teamid")
                    .setParameter("teamid", teamid).getSingleResult()).getMembersList();
        }catch(Exception ex){
            System.err.println(ex.toString());
            return (new ArrayList<Person>());
        }
        //return (t != null) ? t.getMembersList() : null;
        /*if(t != null){
            List<Person> list = t.getMembersList();
            System.out.println("Size: " + list.size());
            for(Person p : list){
                System.out.println(p.getPersonname());
            }
            return list;
        }*/
    }

    public List listMembersByTask(int id){
//        Task t = em.find(Task.class, id);
        try{
            return ((Task)em.createQuery("select t from Task t where t.id = :id")
                    .setParameter("id", id).getSingleResult()).getPersons();
        }catch(Exception ex){
            System.err.println(ex.toString());
            return (new ArrayList<Person>());
        }
    }

    public int updateNameById(int id, String name){
        return em.createQuery("update Person p set p.personname = :name where p.personid = :id")
                .setParameter("id", id).setParameter("name", name).executeUpdate();
    }

    public Person updateTeamByPersonId(int personid, int teamid){
        Person p = getPersonById(personid);
        Team t = getTeamById(teamid);
        if(p != null && t != null){
            p.setTeam(t);
            return em.merge(p);
        }
        return null;
        /*return em.createQuery("update Person p set p.teamid = :teamid where p.personid = :personid")
                .setParameter("personid", personid).setParameter("teamid", teamid).executeUpdate();*/
    }

    public boolean deletePersonById(int id){
//        return em.createQuery("select a from User a where a.id = :id", User.class)
//                .setParameter("id", id).getSingleResult();
//        return em.createQuery("delete from Person p where p.personid = :id").setParameter("id", id).executeUpdate();
        Person p = getPersonById(id);
        if(p != null){
            List<Task> taskList = p.getTasks();
            for(Task t : taskList){
                List<Person> taskMembers = t.getPersons();
                taskMembers.removeIf(p2 -> p2.getPersonid() == id);
                em.persist(t);
            }
            em.remove(p);
            return true;
        }
        return false;
    }

    public void deleteTeamById(int id){
//        Team team = em.find(Team.class, id);
        Team team = (Team) em.createQuery("select t from Team t where t.id = :id")
                .setParameter("id", id).getSingleResult();
        List<Person> membersList = team.getMembersList();
        for(Person p : membersList){
            p.setTeam(null);
            em.persist(p);
        }
        //return em.createQuery("delete from Team t where t.id = :id").setParameter("id", id).executeUpdate();
        em.remove(team);
        // database constraint: ON DELETE SET NULL (how to fix?, impossible in jpa?), ON DELETE CASCADE
    }

    public boolean deleteTask(int id){
        Task t = getTask(id);
        List<Person> taskMembers = t.getPersons();
        for(Person p : taskMembers){
            List<Task> taskList = p.getTasks();
            /*for(Iterator<Task> it = taskList.iterator(); it.hasNext(); ){
                if(it.next().getTaskId() == id){
                    it.remove();
                }
            }*/
            taskList.removeIf(t2 -> t2.getTaskId()== id);
            em.persist(p);
        }
        em.remove(t);
        return true;
    }

    public boolean unassignTask(int personId, int taskId){
        Person person = getPersonById(personId);
        Task task = getTask(taskId);
        if(person != null && task != null){
            List<Task> taskList = person.getTasks();
            taskList.removeIf(t2 -> t2.getTaskId() == taskId);
            em.persist(person);
            return true;
        }
        return false;
    }

    public Team registerTeam(String name, String shortname){
        Team t = new Team();
        t.setTeamName(name);
        if(!shortname.isEmpty()){
            t.setShortName(shortname);
        }
        return em.merge(t);
    }

    public Task createTask(String description){
        Task task = new Task();
        task.setDescription(description);
        return em.merge(task);
    }

    public Task getTask(int id){
        return (Task) em.createQuery("select t from Task t where t.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    public boolean assignTaskToPerson(int personid, int taskid) throws Exception{
//        Person person = em.find(Person.class, personid);
//        Task task = em.find(Task.class, taskid);
        Person person = getPersonById(personid);
        Task task = getTask(taskid);
        if(person != null && task != null){
//            person.getTasks().add(task);
            List<Task> taskList = person.getTasks();
            taskList.add(task);
            person.setTasks(taskList);
            em.persist(person);
            return true;
        }
        return false;
    }
}
