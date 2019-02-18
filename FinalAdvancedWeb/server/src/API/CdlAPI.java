package API;

import Classi.Cdl;
import Classi.Versioni;
import Views.CdlCompleto;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CdlAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @Context
    ResourceContext resourceContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCdl(@Context UriInfo uriInfo) {
        List<Cdl> cdl = dataAccess.getAllCdl();
        List<String> cdlUri = new ArrayList<>();
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token + "/cdl/";
        } else {
            baseUri = uriInfo.getBaseUri() + "cdl/";
        }
        for(Cdl c : cdl) {
            cdlUri.add(baseUri + c.getIdcdl());
        }
        return Response.ok(cdlUri).build();
    }

    @Path("triennale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdlTriennali(@Context UriInfo uriInfo) {
        List<Cdl> cdl = dataAccess.getCdlTriennali();
        List<String> cdlUri = new ArrayList<>();
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token + "/cdl/";
        } else {
            baseUri = uriInfo.getBaseUri() + "cdl/";
        }
        for(Cdl c : cdl) {
            cdlUri.add(baseUri + c.getIdcdl());
        }
        Versioni versione = dataAccess.getVersione("cdl");
        if (versione != null) {
            return Response.ok(cdlUri).header("versione", versione.getVersione()).build();
        }
        return Response.ok(cdlUri).build();
    }

    @Path("magistrale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdlMagistrali(@Context UriInfo uriInfo) {
        List<Cdl> cdl = dataAccess.getCdlMagistrali();
        List<String> cdlUri = new ArrayList<>();
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token + "/cdl/";
        } else {
            baseUri = uriInfo.getBaseUri() + "cdl/";
        }
        for(Cdl c : cdl) {
            cdlUri.add(baseUri + c.getIdcdl());
        }
        Versioni versione = dataAccess.getVersione("cdl");
        if (versione != null) {
            return Response.ok(cdlUri).header("versione", versione.getVersione()).build();
        }
        return Response.ok(cdlUri).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdl(@PathParam("id") int id, @Context UriInfo uriInfo) {
        if (id < 0) {
            return Response.status(404).build();
        }
        String baseUriCorsi;
        if (token != null) {
            baseUriCorsi = uriInfo.getBaseUri() + "auth/" + token + "/courses/";
        } else {
            baseUriCorsi = uriInfo.getBaseUri() + "courses/";
        }
        Cdl cdl = dataAccess.getCdl(id);
        if (cdl != null) {
            if (cdl.getImmagine() != null && !cdl.getImmagine().trim().isEmpty()) {
                cdl.setImmagine(Utils.getEncodedFile(cdl.getImmagine()));
            }
            CdlCompleto cdlCompleto = resourceContext.getResource(CdlCompleto.class);
            cdlCompleto.init(cdl, baseUriCorsi);
            return Response.ok(cdlCompleto).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCdl(Cdl cdl, @Context HttpHeaders headers) {
        if (cdl == null || (cdl.getNomeIt() == null && cdl.getAbbrIt() == null)) {
            return Response.status(400).build();
        }
        String imageType = headers.getHeaderString("image-type");
        if (imageType != null && cdl.getImmagine() != null && !cdl.getImmagine().trim().isEmpty()) {
            cdl.setImmagine(Utils.saveNewFile(cdl.getImmagine(), "imgCDL/" + cdl.getNomeIt(), imageType));
        } else {
            cdl.setImmagine(null);
        }
        cdl.setAnno(Utils.getYear("current"));
        dataAccess.insertCdl(cdl);
        dataAccess.saveLog(token, "ha inserito il nuovo CDL " + cdl.getNomeIt());
        return Response.ok().build();
    }

    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCdl(Cdl cdl, @Context HttpHeaders headers, @PathParam("id") int idCdl) {
        if (cdl == null || (cdl.getNomeIt() == null && cdl.getAbbrIt() == null)) {
            return Response.status(400).build();
        }
        String imageType = headers.getHeaderString("image-type");
        if (imageType != null && cdl.getImmagine() != null && !cdl.getImmagine().trim().isEmpty()) {
            Cdl dbCdl = dataAccess.getCdl(idCdl);
            if (dbCdl != null) {
                cdl.setImmagine(Utils.updateFile(cdl.getImmagine(), dbCdl.getImmagine(), "imgCDL/" + cdl.getNomeIt(), imageType));
            }
        } else {
            cdl.setImmagine(null);
        }
        dataAccess.updateCdl(idCdl, cdl);
        dataAccess.saveLog(token, "ha modificato il CDL " + cdl.getNomeIt());
        return Response.ok().build();
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCdl(@PathParam("id") int idCdl) {
        Cdl cdl = dataAccess.getCdl(idCdl);
        if (cdl != null && cdl.getImmagine() != null && !cdl.getImmagine().trim().isEmpty()) {
            Utils.deleteFile(cdl.getImmagine());
        }
        dataAccess.deleteCdl(idCdl);
        return Response.ok().build();
    }
}
