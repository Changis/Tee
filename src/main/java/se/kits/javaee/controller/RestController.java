package se.kits.javaee.controller;

import se.kits.javaee.rest.*;
import se.kits.javaee.model.Person;

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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
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

//    @Resource(lookup = "java:/myJmsTest/MyConnectionFactory")
//    private TopicConnectionFactory tcf;

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
    // https://www.tutorialspoint.com/restful/restful_quick_guide.htm

    @Path("/persons")
    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    //@Produces(TEXT_HTML)
    public Response registerPerson(NewPersonInput newPersonInput){
        return Response.ok(dbm.registerPerson(newPersonInput.getName())).build();
//        return Response.ok(name + " was (hopefully) added to dcdb/person").build();
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
    }

    @Path("/teams")
    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response registerTeam(NewTeamInput newTeamInput){
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(dbm.registerTeam(newTeamInput.getName(), newTeamInput.getShortName())).build();
    }

    @Path("/persons/{id}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response getPerson(@PathParam("id") int id){
        try{
            //return dbm.getPersonById(id);
            return Response.ok(dbm.getPersonById(id)).build();
        }catch(Exception ex){
            //return ex.toString();
            return Response.serverError().build();
        }
    }

    @Path("/persons")
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

    @Path("/teams")
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

    @Path("/tasks")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listAllTasks(){
        try{
            return Response.ok(dbm.listAllTasks()).build();
        }catch(Exception ex){
            return Response.ok(ex.toString()).build();
        }
    }

    @Path("/teammembers/{teamid}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listAllMembers(@PathParam("teamid") int teamid){
        try {
            return Response.ok(dbm.listAllMembers(teamid)).build();
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println(e.toString());
            return Response.serverError().build();
        }
    }

    @Path("/taskmembers/{id}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listMembersByTask(@PathParam("id") int id){
        try {
            return Response.ok(dbm.listMembersByTask(id)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path("/personnameupdate")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updatePersonNameById(UpdatePersonNameInput u){
        return Response.ok(dbm.updateNameById(u.getId(), u.getName()) + " rows updated").build();
    }

    @Path("/personteamupdate")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updatePersonTeamByPersonId(UpdatePersonTeamInput u){
        Person p = p = dbm.updateTeamByPersonId(u.getPersonId(), u.getTeamId());
        try {

            return Response.ok("PersonID " + p.getPersonid() + " updated").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path("/persons")
    @DELETE
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response deletePersonById(IdInput idInput){
        return Response.ok(dbm.deletePersonById(idInput.getId())).build();
    }

    @Path("/teams")
    @DELETE
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response deleteTeamById(IdInput idInput){
        dbm.deleteTeamById(idInput.getId());
        return Response.ok().build();
    }

    @Path("/tasks")
    @DELETE
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response deleteTask(IdInput idInput){
        return Response.ok(dbm.deleteTask(idInput.getId())).build();
    }

    /*@Path("/post/add/{name}/{teamid}")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_HTML)
    public Response registerPerson(@PathParam("name") String name, @PathParam("teamid") int id){
        Person p = dbm.registerPerson(name);
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(name + " was (hopefully) added to dcdb/person").build();
    }*/

    @Path("/tasks")
    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createTask(TaskInput taskInput){
        //System.out.println("TASK@CONTROLLER: " + taskInput.getTaskDescription());
        return Response.ok(dbm.createTask(taskInput.getTaskDescription())).build();
    }

    @Path("/taskassignment")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response assignTask(PersonTaskInput uti){
        try {
            return Response.ok(dbm.assignTaskToPerson(uti.getPersonId(), uti.getTaskId())).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(false).build();
    }

    @Path("/taskunassignment")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response unassignTask(PersonTaskInput pti){
        return Response.ok(dbm.unassignTask(pti.getPersonId(), pti.getTaskId())).build();
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
        System.out.println("MSG TO TOPIC ARRIVED AT REST CONTROLLER: " + jmsg.getMessageForJMS());
        return sendMessage(jmsg.getMessageForJMS(), false);
        /*try {
            TopicConnection tconnection = tcf.createTopicConnection("myJmsUser", "myJmsPassword");
            TopicSession tsession = tconnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = tsession.createTextMessage();
            message.setText(jmsg.getMessageForJMS());
            TopicPublisher tpublisher = tsession.createPublisher(topic);
            tpublisher.publish(message);
            tpublisher.close();
            tsession.close();
            tconnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Response.ok().build();*/
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
