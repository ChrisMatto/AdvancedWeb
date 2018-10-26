package API;

import Classi.Utente;
import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users")
public class UsersAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUtenti(ContainerRequestContext containerRequestContext) {
        String token = (String)containerRequestContext.getProperty("token");
        List<Utente> utenti = DataAccess.getUtenti();
        String baseUri = containerRequestContext.getUriInfo().getBaseUri() + "auth/" + token + "/users/";
        List<String> utentiUri = new ArrayList<>();
        for (Utente utente: utenti) {
            utentiUri.add(baseUri + utente.getIdUtente());
        }
        return Response.ok(utentiUri).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUtente(Utente utente) {
        if (utente.getUsername() == null || utente.getPassword() == null || utente.getUsername().trim().isEmpty() || utente.getPassword().trim().isEmpty()) {
            return Response.status(400).build();
        }
        if (DataAccess.existUtente(utente)) {
            return Response.status(409).build();
        }
        if (utente.getDocente() != null) {
            if (!DataAccess.existDocente(utente.getDocente()) || DataAccess.existDocenteInUtente(utente.getDocente())) {
                return Response.status(409).build();
            }
        }
        if (utente.getGruppo() != null && !DataAccess.existGruppo(utente.getGruppo())) {
            return Response.status(409).build();
        }
        DataAccess.insertUtente(utente);
        return Response.ok().build();
    }
}
