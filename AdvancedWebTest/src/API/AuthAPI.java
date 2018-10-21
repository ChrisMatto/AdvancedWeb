package API;

import Classi.Login;
import Classi.Utente;
import DataAccess.DataAccess;

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
        String token = null;
        if(login.getUsername() != null && login.getPassword() != null && !login.getUsername().isEmpty() && !login.getPassword().isEmpty()) {
            Utente utente = DataAccess.getUtente(login.getUsername(), login.getPassword());
            if(utente != null) {
                token = DataAccess.makeSessione(utente);
                return Response.ok(token).build();
            } else {
                return Response.status(401).build();
            }
        }
        return Response.status(400).build();
    }

    @Path("logout")
    @POST
    public Response logout(String token) {
        DataAccess.deleteSession(token);
        return Response.ok().build();
    }
}
