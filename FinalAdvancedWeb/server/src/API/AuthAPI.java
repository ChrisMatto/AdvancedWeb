package API;

import ClassiTemp.Login;
import Classi.Utente;
import ClassiTemp.Views;
import Controller.Controllers;
import Controller.Utils;
import DataAccess.DataAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

public class AuthAPI implements Resource {

    @Path("login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Login login) {
        if(login == null)
            return Response.status(400).build();
        String token;
        if(login.getUsername() != null && login.getPassword() != null && !login.getUsername().isEmpty() && !login.getPassword().isEmpty()) {
            Utente utente = DataAccess.getUtente(login.getUsername(), login.getPassword());
            if(utente != null) {
                token = DataAccess.getSessionToken(utente.getIdUtente());
                if (token != null) {
                    DataAccess.deleteSession(token);
                }
                token = DataAccess.makeSessione(utente);
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
        if (DataAccess.existsSessione(token)) {
            Utente utente = DataAccess.getSessionUtente(token);
            ObjectMapper mapper = new ObjectMapper();
            String jsonUtente = mapper.writerWithView(Views.SimpleUtente.class).writeValueAsString(utente);
            return Response.ok(jsonUtente).build();
        }
        return Response.status(404).build();
    }

    @Path("logout")
    @POST
    public Response logout(String token) {
        DataAccess.deleteSession(token);
        return Response.ok().build();
    }

    @Path("{SID}/{controller}")
    public Resource getController(@PathParam("SID") String token, @PathParam("controller") String controllerName, @Context Request request) {
        Controllers controller = Utils.getController(controllerName);
        if (!DataAccess.checkAccessToken(token, controllerName, request.getMethod())) {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            throw new WebApplicationException(responseBuilder.build());
        }
        switch (controller) {
            case courses:
                return new CoursesAPI(token);
            case users:
                return new UsersAPI(token);
            case cdl:
                return new CdlAPI(token);
            case teachers:
                return new TeachersAPI(token);
            default:
                Response.ResponseBuilder responseBuilder = Response.status(404);
                throw new WebApplicationException(responseBuilder.build());
        }
    }
}