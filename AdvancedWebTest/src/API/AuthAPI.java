package API;

import Classi.Login;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class AuthAPI {

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        if(json.isEmpty())
            return Response.status(400).build();

        return Response.ok(json).build();
    }
}
