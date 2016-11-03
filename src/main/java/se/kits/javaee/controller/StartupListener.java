package se.kits.javaee.controller;

import se.kits.javaee.model.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by David Chang on 2016-10-18.
 */
//@Stateless
public class StartupListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("MYJPU");
        EntityManager entitymanager = emf.createEntityManager();
        entitymanager.getTransaction().begin();

        Person p = new Person();
        entitymanager.persist(p);
        p.setPersonname("darne");
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emf.close();*/


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
