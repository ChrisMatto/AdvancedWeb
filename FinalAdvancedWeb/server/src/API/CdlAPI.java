package API;

import Classi.Cdl;
import ClassiTemp.CdlCompleto;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class CdlAPI implements Resource {

    private String token;

    public CdlAPI() {
        token = null;
    }

    public CdlAPI(String token) {
        this.token = token;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCdl(@Context UriInfo uriInfo) {
        List<Cdl> cdl = DataAccess.getAllCdl();
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
        return Response.ok().build();
    }

    @Path("triennale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdlTriennali(@Context UriInfo uriInfo) {
        List<Cdl> cdl = DataAccess.getCdlTriennali();
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

    @Path("magistrale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdlMagistrali(@Context UriInfo uriInfo) {
        List<Cdl> cdl = DataAccess.getCdlMagistrali();
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
        Cdl cdl = DataAccess.getCdl(id);
        if (cdl != null) {
            return Response.ok(new CdlCompleto(cdl, baseUriCorsi)).build();
        }
        return Response.ok().build();
    }
}