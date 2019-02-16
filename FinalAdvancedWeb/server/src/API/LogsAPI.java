package API;

import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LogsAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    private String token;

    public LogsAPI() {
        token = null;
    }

    public LogsAPI(String token) {
        this.token = token;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs() {
        return Response.ok(dataAccess.getLogs()).build();
    }
}
