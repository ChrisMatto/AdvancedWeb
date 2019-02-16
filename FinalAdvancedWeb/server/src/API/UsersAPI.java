package API;

import Classi.Utente;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class UsersAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUtenti(@Context UriInfo uriInfo) {
        List<Integer> utenti = dataAccess.getUtenti();
        List<String> utentiUri = new ArrayList<>();
        for (int id : utenti) {
            utentiUri.add(uriInfo.getBaseUri() + "auth/" + token + "/users/" + id);
        }
        return Response.ok(utentiUri).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUtente(Utente utente) {
        if (utente == null) {
            return Response.status(400).build();
        }
        if (utente.getUsername() == null || utente.getPassword() == null || utente.getUsername().trim().isEmpty() || utente.getPassword().trim().isEmpty()) {
            return Response.status(400).build();
        }
        if (dataAccess.existUsernameUtente(utente.getUsername())) {
            return Response.status(409).build();
        }
        if (utente.getDocente() != null) {
            if (!dataAccess.existDocente(utente.getDocente()) || dataAccess.existDocenteInUtente(utente.getDocente())) {
                return Response.status(409).build();
            }
        }
        if (utente.getGruppo() != null && !dataAccess.existGruppo(utente.getGruppo())) {
            return Response.status(409).build();
        }
        dataAccess.insertUtente(utente);
        dataAccess.saveLog(token, "ha inserito il nuovo utente " + utente.getUsername());
        return Response.ok().build();
    }

    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUtente(@PathParam("id") int id) {
        if (id < 0) {
            return Response.status(404).build();
        }
        return Response.ok(dataAccess.getUtenteNoPassword(id)).build();
    }

    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response updateUtente(@PathParam("id") int id, Utente utente) {
        if (id < 0) {
            return Response.status(404).build();
        }
        dataAccess.updateUtente(id, utente);
        dataAccess.saveLog(token, "ha modificato il profilo utente " + utente.getUsername());
        return Response.ok().build();
    }

    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public Response deleteUtente(@PathParam("id") int id) {
        if (id < 0) {
            return Response.status(404).build();
        }
        if (dataAccess.existSessioneUtente(id)) {
            return Response.status(409).build();
        }
        Utente utente = dataAccess.getUtenteNoPassword(id);
        dataAccess.deleteUtente(id);
        dataAccess.saveLog(token, "ha eliminato l'utente " + utente.getUsername());
        return Response.ok().build();
    }
}
