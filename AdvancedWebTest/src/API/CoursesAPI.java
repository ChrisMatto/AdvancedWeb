package API;

import Classi.Corso;
import ClassiTemp.CorsoCompleto;
import Controller.Utils;
import DataAccess.DataAccess;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("courses")
public class CoursesAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() {
        return Response.ok(DataAccess.getCorsi()).build();
    }

    @Path("{year}")
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
                    corsi = DataAccess.getCorsiByCdl(anno,idCdl);
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
    }
}
