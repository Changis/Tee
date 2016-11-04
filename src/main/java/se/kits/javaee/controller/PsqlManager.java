package se.kits.javaee.controller;



import se.kits.javaee.model.Person;
import se.kits.javaee.model.Team;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by David Chang on 2016-10-17.
 */
@Stateless
public class PsqlManager {

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

    public Person showPersonById(int id) throws Exception{
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

    public List listAllMembers(int teamid) throws Exception{
        return getTeamById(teamid).getMembersList();
    }

    public int updateNameById(int id, String name){
        return em.createQuery("update Person p set p.personname = :name where p.personid = :id")
                .setParameter("id", id).setParameter("name", name).executeUpdate();
    }

    public Person updateTeamByPersonId(int personid, int teamid) throws Exception{
        Person p = showPersonById(personid);
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

    public int deleteTeamById(int id){
        return em.createQuery("delete from Team t where t.id = :id").setParameter("id", id).executeUpdate();
    }

    public Team registerTeam(String name, String shortname){
        Team t = new Team();
        t.setTeamName(name);
        if(!shortname.isEmpty()){
            t.setShortName(shortname);
        }
        return em.merge(t);
    }

}
