package API;

import Classi.Corso;
import Classi.Libro;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @Path("course/{year}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLibriCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        return Response.ok(dataAccess.getLibriInCorso(id, anno)).build();
    }

    @Path("course/{year}/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertLibro(@PathParam("year") String year, @PathParam("id") int id, Libro libro) {
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(id, anno);
        dataAccess.insertLibro(id, anno, libro);
        dataAccess.saveLog(token, "ha aggiunto un libro per il corso " + corso.getNomeIt() + " dell'anno " + anno);
        return Response.ok().build();
    }

    @Path("course/{year}/{idCorso}/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLibro(@PathParam("year") String year, @PathParam("idCorso") int idCorso, @PathParam("id") int idLibro, Libro libro) {
        if (libro == null) {
            return Response.status(400).build();
        }
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(idCorso, anno);
        dataAccess.updateLibro(idLibro, libro);
        dataAccess.saveLog(token, "ha modificato il libro " + libro.getTitolo() + " per il corso " + corso.getNomeIt() + " dell'anno " + anno);
        return Response.ok().build();
    }

    @Path("course/{year}/{idCorso}/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLibro(@PathParam("year") String year, @PathParam("idCorso") int idCorso, @PathParam("id") int idLibro) {
        int anno = Utils.getYear(year);
        Libro libro = dataAccess.getLibro(idLibro);
        Corso corso = dataAccess.getCorso(idCorso, anno);
        dataAccess.deleteLibro(idLibro);
        dataAccess.saveLog(token, "ha eliminato il libro " + libro.getTitolo() + " per il corso " + corso.getNomeIt() + " dell'anno " + anno);
        return Response.ok().build();
    }
}
