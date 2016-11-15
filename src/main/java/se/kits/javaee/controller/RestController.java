package se.kits.javaee.controller;

import se.kits.javaee.messaging.JmsMessageInput;
import se.kits.javaee.model.Person;
import se.kits.javaee.model.Team;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * Created by David Chang on 2016-10-17.
 */
@Stateless
@Path("")
//@WebServlet(name = "Rest")
public class RestController {

    @Inject
    private PsqlManager dbm;

    @Inject
    private MysqlManager dbm2;

    @Resource(lookup = "java:/myJmsTest/MyConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/myJmsTest/MyQueue")
    private Queue queue;
    @Resource(lookup = "java:/myJmsTest/DCTopic")
    private Topic topic;
    //private Destination destination;

    /* How to return a HTML.
    TO DO: Adress to HTML file in webapp folder */
    @Path("/about")
    @GET
    @Produces("text/html")
    public String getAbout(@Context HttpServletResponse response,
                           @Context HttpServletRequest request) throws ServletException, IOException {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>About</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1> GET /about/ </h1>\n" +
                "<p> Hi! Thank you for visiting my website.</p>\n" +
                "</body>\n" +
                "</html>";
    }

    //CREATE READ UPDATE DELETE

    @Path("/add/{name}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    //@Produces(TEXT_HTML)
    public Response registerPerson(@PathParam("name") String name){
        return Response.ok(dbm.registerPerson(name)).build();
//        return Response.ok(name + " was (hopefully) added to dcdb/person").build();
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
    }

    @Path("/addteam/{name}/{shortname}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response registerTeam(@PathParam("name") String name, @PathParam("shortname") String shortname){
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(dbm.registerTeam(name, shortname)).build();
    }

    @Path("/get/{id}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response showNameById(@PathParam("id") int id){
        try{
            //return dbm.showPersonById(id);
            return Response.ok(dbm.showPersonById(id)).build();
        }catch(Exception ex){
            //return ex.toString();
            return Response.serverError().build();
        }
    }

    @Path("/get/all")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listAll(){
        try{
            return Response.ok(dbm.listAll()).build();
        }catch(Exception ex){
            return Response.ok(ex.toString()).build();
        }
    }

    @Path("/get/allteams")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listAllTeams(){
        try{
            return Response.ok(dbm.listAllTeams()).build();
        }catch(Exception ex){
            return Response.ok(ex.toString()).build();
        }
    }

    @Path("/getmembers/{teamid}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listAllMembers(@PathParam("teamid") int teamid){
        try {
            return Response.ok(dbm.listAllMembers(teamid)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path("/update/{id}/{name}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response updateNameById(@PathParam("id") int id, @PathParam("name") String name){
        return Response.ok(dbm.updateNameById(id, name) + " rows updated").build();
    }

    @Path("/updateteam/{personid}/{teamid}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response updateTeamByPersonId(@PathParam("personid") int personid, @PathParam("teamid") int teamid){
        Person p;
        try {
            p = dbm.updateTeamByPersonId(personid, teamid);
            return Response.ok("PersonID " + p.getPersonid() + " updated").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path("/delete/{id}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response deleteById(@PathParam("id") int id){
        return Response.ok(dbm.deletePersonById(id) + " rows deleted").build();
    }

    @Path("/deleteteam/{id}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response deleteTeamById(@PathParam("id") int id){
        int deletedRows = dbm.deleteTeamById(id);
        return Response.ok(deletedRows + " rows deleted").build();
    }

    @Path("/post/add/{name}/{teamid}")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_HTML)
    public Response registerPerson(@PathParam("name") String name, @PathParam("teamid") int id){
        Person p = dbm.registerPerson(name);
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(name + " was (hopefully) added to dcdb/person").build();
    }

    private Response sendMessage(String messagebody, boolean isPTP){
        try {
            Connection connection = connectionFactory.createConnection("myJmsUser", "myJmsPassword");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage();
            message.setText(messagebody);
            MessageProducer producer = isPTP ? session.createProducer(queue) : session.createProducer(topic);
            producer.send(message);
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Response.ok().build();
    }

    @Path("/queuemsg")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response queueMsg(JmsMessageInput jmsg){
        return sendMessage(jmsg.getMessageForJMS(), true);
    }

    @Path("/topicmsg")
    @POST
//    @Consumes("application/x-www-form-urlencoded")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response topicMsg(JmsMessageInput jmsg){
        return sendMessage(jmsg.getMessageForJMS(), false);
    }

    @Path("/getqueuemsg")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response getQueueMsg(){
        String str = "failed msg consumption";
        try {
            Connection connection = connectionFactory.createConnection("myJmsUser", "myJmsPassword");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            Message m = consumer.receive();
            if(m instanceof Message){
                TextMessage message = (TextMessage) m;
                str = message.getText();
            }
            consumer.close();
            session.close();
            connection.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Response.ok(str).build();
    }

    @Path("/gettopicmsg")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response getTopicMsg(){
        String str = "failed msg consumption";
        try {
            Connection connection = connectionFactory.createConnection("myJmsUser", "myJmsPassword");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(topic);
            connection.start();
            Message m = consumer.receive();
            if(m instanceof Message){
                TextMessage message = (TextMessage) m;
                str = message.getText();
            }
            consumer.close();
            session.close();
            connection.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Response.ok(str).build();
    }

    // break out code! some day.
}
