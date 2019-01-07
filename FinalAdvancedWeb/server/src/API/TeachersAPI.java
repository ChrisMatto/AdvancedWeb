package API;

import Classi.Docente;
import Classi.Versioni;
import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class TeachersAPI implements Resource {

    private String token;

    public TeachersAPI() {
        token = null;
    }

    public TeachersAPI(String token) {
        this.token = token;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers(@Context UriInfo uriInfo) {
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/teachers/";
        } else {
            baseUri = uriInfo.getBaseUri() + "teachers/";
        }
        Versioni versione = DataAccess.getVersione("docente");
        if (versione != null) {
            return Response.ok(DataAccess.getDocenti(baseUri)).header("versione", versione.getVersione()).build();
        }
        return Response.ok(DataAccess.getDocenti(baseUri)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("id") int id) {
        Docente docente = DataAccess.getDocente(id);
        if (docente != null) {
            return Response.ok(docente).build();
        }
        return Response.status(404).build();
    }

}