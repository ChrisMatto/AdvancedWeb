package API;

import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LogsAPI implements Resource {

    private String token;

    public LogsAPI(String token) {
        this.token = token;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs() {
        return Response.ok(DataAccess.getLogs()).build();
    }
}
