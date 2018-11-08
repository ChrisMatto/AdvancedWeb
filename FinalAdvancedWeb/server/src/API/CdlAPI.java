package API;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class CdlAPI implements Resource {

    private String token;

    public CdlAPI() {
        token = null;
    }

    public CdlAPI(String token) {
        this.token = token;
    }

    @Path("triennale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdl(@Context UriInfo uriInfo) {
        return Response.ok().build();
    }
}
