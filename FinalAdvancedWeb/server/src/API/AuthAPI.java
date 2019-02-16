package API;

import Views.Login;
import Classi.Utente;
import Views.Views;
import Controller.Controllers;
import Controller.Utils;
import DataAccess.DataAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class AuthAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @Context
    ResourceContext context;

    @Path("login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Login login) {
        if(login == null)
            return Response.status(400).build();
        String token;
        if(login.getUsername() != null && login.getPassword() != null && !login.getUsername().isEmpty() && !login.getPassword().isEmpty()) {
            Utente utente = dataAccess.getUtente(login.getUsername(), login.getPassword());
            if(utente != null) {
                token = dataAccess.getSessionToken(utente.getIdUtente());
                if (token == null) {
                    token = dataAccess.makeSessione(utente);
                }
                dataAccess.saveLog(token, "ha effettuato l'accesso");
                return Response.ok(token).build();
            } else {
                return Response.status(401).build();
            }
        }
        return Response.status(400).build();
    }

    @Path("checkSession")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkSession(String token) throws JsonProcessingException {
        if (token == null) {
            return Response.status(400).build();
        }
        if (dataAccess.existsSessione(token)) {
            Utente utente = dataAccess.getSessionUtente(token);
            ObjectMapper mapper = new ObjectMapper();
            String jsonUtente = mapper.writerWithView(Views.SimpleUtente.class).writeValueAsString(utente);
            return Response.ok(jsonUtente).build();
        }
        return Response.status(404).build();
    }

    @Path("logout")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(String token) {
        dataAccess.saveLog(token, "ha effettuato il logout");
        dataAccess.deleteSession(token);
        return Response.ok().build();
    }

    @Path("{SID}/{controller}")
    public Resource getController(@PathParam("SID") String token, @PathParam("controller") String controllerName, @Context Request request) {
        Controllers controller = Utils.getController(controllerName);
        if (!dataAccess.checkAccessToken(token, controllerName, request.getMethod())) {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            throw new WebApplicationException(responseBuilder.build());
        }
        switch (controller) {
            case courses:
                return context.getResource(CoursesAPI.class);
            case users:
                return context.getResource(UsersAPI.class);
            case cdl:
                return context.getResource(CdlAPI.class);
            case teachers:
                return context.getResource(TeachersAPI.class);
            case logs:
                return context.getResource(LogsAPI.class);
            default:
                Response.ResponseBuilder responseBuilder = Response.status(404);
                throw new WebApplicationException(responseBuilder.build());
        }
    }
}