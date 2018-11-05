package API;

import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("docenti")
public class DocentiAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocenti() {
        return Response.ok(DataAccess.getDocenti()).build();
    }

    @Path("byCorso/{corso}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocentiCorso(@PathParam("corso") int corso) {
        return Response.ok(DataAccess.getDocentiInCorso(corso)).build();
    }
}
