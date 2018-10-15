package API;

import Classi.Cdl;
import DataAccess.DataAccess;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("cdl")
public class CDLAPI {
    private static Gson gson = new Gson();

    @Path("triennale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDL() {
        List<Cdl> cdl = DataAccess.getCDL();
        return Response.ok(gson.toJson(cdl)).build();
    }

    @Path("triennaleRandom")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomCDL() {
        List<Cdl> cdl = DataAccess.getCDL();
        Random rand = new Random();
        List<Cdl> rcdl = new ArrayList();
        int n=4;
        int cdlsize = cdl.size();
        for (int i = 0; i < n; i++){
            if(!cdl.isEmpty() && i <= cdlsize){
                int randomIndex=rand.nextInt(cdl.size());
                rcdl.add(cdl.get(randomIndex));
                cdl.remove(randomIndex);
            }
        }
        return Response.ok(gson.toJson(rcdl)).build();
    }

    @Path("magistrale")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDLM() {
        List<Cdl> cdlm = DataAccess.getCDLM();
        return Response.ok(gson.toJson(cdlm)).build();
    }

    @Path("magistraleRandom")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomCDLM() {
        List<Cdl> cdlm = DataAccess.getCDLM();
        Random rand = new Random();
        List<Cdl> rcdlm = new ArrayList();
        int n=4;
        int cdlmsize = cdlm.size();
        for (int i = 0; i < n; i++){
            if(!cdlm.isEmpty() && i <= cdlmsize){
                int randomIndex=rand.nextInt(cdlm.size());
                rcdlm.add(cdlm.get(randomIndex));
                cdlm.remove(randomIndex);
            }
        }
        return Response.ok(gson.toJson(rcdlm)).build();
    }
}
