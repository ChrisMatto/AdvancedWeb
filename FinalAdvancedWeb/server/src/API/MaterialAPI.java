package API;

import Classi.Corso;
import Classi.Materiale;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

public class MaterialAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @Path("course/{year}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaterialeCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        return Response.ok(dataAccess.getMaterialeCorso(id, anno)).build();
    }

    @Path("{idMateriale}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getMateriale(@PathParam("idMateriale") int idMateriale) {
        Materiale materiale = dataAccess.getMateriale(idMateriale);
        File file = Utils.getFile(materiale.getLink());
        return Response.ok(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
    }

    @Path("course/{year}/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertMateriale(@PathParam("year") String year, @PathParam("id") int id, @Context HttpHeaders headers, Materiale materiale) {
        if (materiale == null) {
            return Response.status(400).build();
        }
        int anno = Utils.getYear(year);
        String fileType = headers.getHeaderString("file-type");
        if (fileType != null && materiale.getLink() != null && !materiale.getLink().trim().isEmpty()) {
            materiale.setLink(Utils.saveNewFile(materiale.getLink(), "Materiale/" + materiale.getNome(), fileType));
        } else {
            return Response.status(400).build();
        }
        dataAccess.insertMateriale(id, anno, materiale);
        Corso corso = dataAccess.getCorso(id, anno);
        dataAccess.saveLog(token, "ha aggiunto un materiale per il corso " + corso.getNomeIt() + " dell'anno " + anno);
        return Response.ok().build();
    }

    @Path("course/{year}/{idCorso}/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMateriale(@PathParam("year") String year, @PathParam("idCorso") int idCorso, @Context HttpHeaders headers, @PathParam("id") int idMateriale, Materiale materiale) {
        if (materiale == null) {
            return Response.status(400).build();
        }
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(idCorso, anno);
        Materiale mat = dataAccess.getMateriale(idMateriale);
        String fileType = headers.getHeaderString("file-type");
        if (fileType != null && materiale.getLink() != null && !materiale.getLink().trim().isEmpty()) {
            Materiale dbMateriale = dataAccess.getMateriale(idMateriale);
            if (dbMateriale != null) {
                materiale.setLink(Utils.updateFile(materiale.getLink(), dbMateriale.getLink(), "Materiale/" + materiale.getNome(), fileType));
            } else {
                return Response.status(404).build();
            }
        } else {
            return Response.status(400).build();
        }
        dataAccess.updateMateriale(idMateriale, materiale);
        dataAccess.saveLog(token, "ha modificato il materiale " + mat.getNome() + " per il corso " + corso.getNomeIt() + " dell'anno " + anno);
        return Response.ok().build();
    }

    @Path("course/{year}/{idCorso}/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMateriale(@PathParam("year") String year, @PathParam("idCorso") int idCorso, @PathParam("id") int idMateriale) {
        int anno = Utils.getYear(year);
        Materiale materiale = dataAccess.getMateriale(idMateriale);
        Corso corso = dataAccess.getCorso(idCorso, anno);
        if (materiale != null && materiale.getLink() != null && !materiale.getLink().trim().isEmpty()) {
            Utils.deleteFile(materiale.getLink());
        }
        dataAccess.deleteMateriale(idMateriale);
        dataAccess.saveLog(token, "ha eliminato il libro " + materiale.getNome() + " per il corso " + corso.getNomeIt() + " dell'anno " + anno);
        return Response.ok().build();
    }
}
