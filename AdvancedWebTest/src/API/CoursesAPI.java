package API;

import Classi.Corso;
import ClassiTemp.CorsoCompleto;
import Controller.Utils;
import DataAccess.DataAccess;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("courses")
public class CoursesAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() {
        return Response.ok(DataAccess.getCorsi()).build();
    }

    /*@Path("{year}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(@PathParam("year") String year, @QueryParam("cdl") Integer idCdl) {
        List<Corso> corsi;
        if(year.equals("current")) {
            if(idCdl != null) {
                corsi = DataAccess.getCorsiByCdl(Utils.getCurrentYear(),idCdl);
            } else {
                corsi = DataAccess.getCorsi(Utils.getCurrentYear());
            }
            List<CorsoCompleto> corsiCompleti = new ArrayList();
            for(Corso corso: corsi) {
                corsiCompleti.add(new CorsoCompleto(corso));
            }
            return Response.ok(corsiCompleti).build();
        }
        if(NumberUtils.isParsable(year)) {
            int anno = Integer.parseInt(year);
            if(anno > 0) {
                if(year.length() != 4)
                    return Response.status(400).build();
                if(idCdl != null) {
                    corsi = DataAccess.getCorsiByCdl(anno, idCdl);
                } else {
                    corsi = DataAccess.getCorsi(anno);
                }
                List<CorsoCompleto> corsiCompleti = new ArrayList();
                for(Corso corso: corsi) {
                    corsiCompleti.add(new CorsoCompleto(corso));
                }
                return Response.ok(corsiCompleti).build();
            }
        }
        return Response.status(404).build();
    }*/
    @Path("{year}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsi(@PathParam("year") String year, @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
/*        List<Corso> corsi;
        if(year.equals("current")) {
            if(queryParams != null) {
                corsi = DataAccess.getCorsiByFilter(Utils.getCurrentYear(),queryParams);
            } else {
                corsi = DataAccess.getCorsi(Utils.getCurrentYear());
            }
            List<CorsoCompleto> corsiCompleti = new ArrayList();
            for(Corso corso: corsi) {
                corsiCompleti.add(new CorsoCompleto(corso));
            }
            return Response.ok(corsiCompleti).build();
        }
        if(NumberUtils.isParsable(year)) {
            int anno = Integer.parseInt(year);
            if(anno > 0) {
                if(year.length() != 4)
                    return Response.status(400).build();
                if(idCdl != null) {
                    corsi = DataAccess.getCorsiByCdl(anno, idCdl);
                } else {
                    corsi = DataAccess.getCorsi(anno);
                }
                List<CorsoCompleto> corsiCompleti = new ArrayList();
                for(Corso corso: corsi) {
                    corsiCompleti.add(new CorsoCompleto(corso));
                }
                return Response.ok(corsiCompleti).build();
            }
        }
        return Response.status(404).build();
    }*/
return Response.ok().build();
    }


    @Path("{year}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorso(@PathParam("year") String year, @PathParam("id") Integer id) {
        if(id == null)
            return Response.status(400).build();
        Corso corso;
        if(year.equals("current")) {
            corso = DataAccess.getCorso(Utils.getCurrentYear(), id);
            return Response.ok(corso).build();
        } else {
            if(NumberUtils.isParsable(year)) {
                int anno = Integer.parseInt(year);
                if(anno > 0) {
                    if(year.length() != 4)
                        return Response.status(400).build();
                    corso = DataAccess.getCorso(anno, id);
                    return Response.ok(corso).build();
                }
            }
        }
        return Response.status(404).build();
    }
}
