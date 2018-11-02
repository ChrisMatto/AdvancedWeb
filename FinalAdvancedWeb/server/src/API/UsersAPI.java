package API;

import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class UsersAPI implements Resource {

    private String token;

    public UsersAPI(String token) {
        this.token = token;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUtenti(@Context UriInfo uriInfo) {
        List<Integer> utenti = DataAccess.getUtenti();
        List<String> utentiUri = new ArrayList<>();
        for (int id : utenti) {
            utentiUri.add(uriInfo.getBaseUri() + "auth/" + token + "/users/" + id);
        }
        return Response.ok(utentiUri).build();
    }
}
