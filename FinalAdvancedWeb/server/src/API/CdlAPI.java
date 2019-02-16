package API;

import Classi.Cdl;
import Classi.Versioni;
import Views.CdlCompleto;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class CdlAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @Context
    ResourceContext context;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCdl(Cdl cdl) {
        if (cdl == null || (cdl.getNomeIt() == null && cdl.getNomeEn() == null)) {
            return Response.status(400).build();
        }
        cdl.setAnno(Utils.getYear("current"));
        dataAccess.saveLog(token, "ha inserito il nuovo CDL " + cdl.getNomeIt());
        return Response.ok().build();
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
            CdlCompleto cdlCompleto = context.getResource(CdlCompleto.class);
            cdlCompleto.init(cdl, baseUriCorsi);
            return Response.ok(cdlCompleto).build();
        }
        return Response.status(404).build();
    }
}
