package se.kits.javaee.controller;

import se.kits.javaee.model.Person;
import se.kits.javaee.model.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
    //@Produces(APPLICATION_JSON)
    @Produces(TEXT_HTML)
    public Response registerPerson(@PathParam("name") String name){
        dbm.registerPerson(name);
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(name + " was (hopefully) added to dcdb/person").build();
    }


    @Path("/addteam/{name}/{shortname}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_HTML)
    public Response registerTeam(@PathParam("name") String name, @PathParam("shortname") String shortname){
        dbm.registerTeam(name, shortname);
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(name + " was (hopefully) added to dcdb/team").build();
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

        //return ""+id;
    }

    @Path("/get/all")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response listAll(){
        try{
            List<Person> l = dbm.listAll();
            /*StringBuilder sb = new StringBuilder();
            for(Person p : l){
                sb.append(p.getPersonname() + "\n");
            }
            return Response.ok(sb.toString()).build();*/
            return Response.ok(l).build();
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
            List<Team> l = dbm.listAllTeams();
            /*StringBuilder sb = new StringBuilder();
            for(Person p : l){
                sb.append(p.getPersonname() + "\n");
            }
            return Response.ok(sb.toString()).build();*/
            return Response.ok(l).build();
        }catch(Exception ex){
            return Response.ok(ex.toString()).build();
        }

    }

    @Path("/update/{id}/{name}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response updateNameById(@PathParam("id") int id, @PathParam("name") String name){
        int updatedRows = dbm.updateNameById(id, name);
        return Response.ok(updatedRows + " rows updated").build();
    }

    @Path("/updateteam/{personid}/{teamid}")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response updateTeamByPersonId(@PathParam("personid") int personid, @PathParam("teamid") int teamid){
        int updatedRows = 0;
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
        int deletedRows = dbm.deletePersonById(id);
        return Response.ok(deletedRows + " rows deleted").build();
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
    //@Produces(APPLICATION_JSON)
    @Produces(TEXT_HTML)
    public Response registerPerson(@PathParam("name") String name, @PathParam("teamid") int id){
        Person p = dbm.registerPerson(name);
        //return Response.created(URI.create("/rest/" + p.getPersonid())).build();
        return Response.ok(name + " was (hopefully) added to dcdb/person").build();
    }

}
