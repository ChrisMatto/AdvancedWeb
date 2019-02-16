package API;

import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LogsAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs() {
        return Response.ok(dataAccess.getLogs()).build();
    }
}
