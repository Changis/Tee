package se.kits.javaee.controller;

import se.kits.javaee.model.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by David Chang on 2016-11-01.
 */
@Stateless
public class MysqlManager {

    @PersistenceContext(unitName = "mysqljpu")
    private EntityManager em;

    public MysqlManager(){}

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

    public List listAll(){
        return em.createQuery("select p from Person p").getResultList();
    }

    public int updateNameById(int id, String name){
        return em.createQuery("update Person p set p.personname = :name where p.personid = :id")
                .setParameter("id", id).setParameter("name", name).executeUpdate();
    }

    public int deletePersonById(int id){
//        return em.createQuery("select a from User a where a.id = :id", User.class)
//                .setParameter("id", id).getSingleResult();
        return em.createQuery("delete from Person p where p.personid = :id").setParameter("id", id).executeUpdate();
    }
}
