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
    public Response login(Login login) {
        if(login == null)
            return Response.status(400).build();

        return Response.ok(login).build();
    }
}
