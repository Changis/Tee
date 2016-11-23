package se.kits.javaee.controller;

import se.kits.javaee.model.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by David Chang on 2016-10-14.
 */
//@Startup
//@Stateless
//@Singleton
//@Path("/test")
public class Setup {

    @Inject
    private PsqlManager dbm;

    //@PostConstruct
    public void start(){

        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("MYJPU");
        EntityManager entitymanager = emf.createEntityManager();
        entitymanager.getTransaction().begin();
        Person p = new Person();
        p.setPersonname("darne");
        entitymanager.persist(p);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emf.close();*/

//        Person p = dbm.registerPerson("darne");
    }
}

