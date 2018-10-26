package API;

import Classi.Cdl;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cdl")
public class CDLAPI {

    @Path("triennale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDL() {
        List<Cdl> cdl = DataAccess.getCDL();
        return Response.ok(cdl).build();
    }

    @Path("triennaleRandom")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomCDL() {
        List<Cdl> cdl = DataAccess.getCDL();
        List<Cdl> rcdl = Utils.randomizeCDL(cdl);
        return Response.ok(rcdl).build();
    }

    @Path("magistrale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDLM() {
        List<Cdl> cdlm = DataAccess.getCDLM();
        return Response.ok(cdlm).build();
    }

    @Path("magistraleRandom")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomCDLM() {
        List<Cdl> cdlm = DataAccess.getCDLM();
        List<Cdl> rcdlm = Utils.randomizeCDL(cdlm);
        return Response.ok(rcdlm).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdlById(@PathParam("id") int id) {
        if(id < 0) {
            return Response.status(400).build();
        }
        return Response.ok(DataAccess.getCdlById(id)).build();
    }
}
