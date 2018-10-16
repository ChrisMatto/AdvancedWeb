package API;

import Classi.Corso;
import Controller.Utils;
import DataAccess.DataAccess;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("courses")
public class CoursesAPI {

    @Path("{year}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(@PathParam("year") String year) {
        List<Corso> corsi;
        if(year.equals("current")) {
            corsi = DataAccess.getCorsi(Utils.getCurrentYear());
            return Response.ok(corsi).build();
        }
        if(NumberUtils.isParsable(year)) {
            int anno = Integer.parseInt(year);
            if(anno > 0) {
                if(year.length() != 4)
                    return Response.status(400).build();
                corsi = DataAccess.getCorsi(anno);
                return Response.ok(corsi).build();
            }
        }
        return Response.status(404).build();
    }
}
