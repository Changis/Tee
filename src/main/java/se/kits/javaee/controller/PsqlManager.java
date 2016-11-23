package se.kits.javaee.controller;



import se.kits.javaee.model.Person;
import se.kits.javaee.model.Task;
import se.kits.javaee.model.Team;

import javax.ejb.Stateless;
import javax.persistence.*;
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

    public Person getPersonById(int id) throws Exception{
        return (Person) em.createQuery("select p from Person p where p.personid = :id")
                .setParameter("id", id).getSingleResult();
    }

    public Team getTeamById(int teamid) throws Exception{
        return (Team) em.createQuery("select t from Team t where t.id = :teamid")
                .setParameter("teamid", teamid).getSingleResult();
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

    public List<Person> listAllMembers(int teamid) throws Exception{
//        Team t = em.find(Team.class, teamid);
        Team t = (Team) em.createQuery("select t from Team t where t.id = :teamid")
                .setParameter("teamid", teamid).getSingleResult();
        //return (t != null) ? t.getMembersList() : null;
        if(t != null){
            List<Person> list = t.getMembersList();
            System.out.println("Size: " + list.size());
            for(Person p : list){
                System.out.println(p.getPersonname());
            }
            return list;
        }
        return null;
    }

    public List listMembersByTask(int id){
//        Task t = em.find(Task.class, id);
        Task t = (Task) em.createQuery("select t from Task t where t.id = :id")
                .setParameter("id", id).getSingleResult();
        return (t != null) ? t.getPersons() : null;
    }

    public int updateNameById(int id, String name){
        return em.createQuery("update Person p set p.personname = :name where p.personid = :id")
                .setParameter("id", id).setParameter("name", name).executeUpdate();
    }

    public Person updateTeamByPersonId(int personid, int teamid) throws Exception{
        Person p = getPersonById(personid);
        p.setTeam(getTeamById(teamid));

        return em.merge(p);
        /*return em.createQuery("update Person p set p.teamid = :teamid where p.personid = :personid")
                .setParameter("personid", personid).setParameter("teamid", teamid).executeUpdate();*/
    }

    public int deletePersonById(int id){
//        return em.createQuery("select a from User a where a.id = :id", User.class)
//                .setParameter("id", id).getSingleResult();
        return em.createQuery("delete from Person p where p.personid = :id").setParameter("id", id).executeUpdate();
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
