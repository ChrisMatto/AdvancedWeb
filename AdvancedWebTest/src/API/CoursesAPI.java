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

    @Path("{year}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsi(@PathParam("year") String year, @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        int anno;
        if (year.equals("current")) {
            anno = Utils.getCurrentYear();
        } else {
            if (NumberUtils.isParsable(year)) {
                anno = Integer.parseInt(year);
                if (anno > 0) {
                    if (year.length() != 4) {
                        return Response.status(400).build();
                    }
                } else {
                    return Response.status(404).build();
                }
            } else {
                return Response.status(404).build();
            }
        }
        List<Corso> corsi = DataAccess.getCorsiByFilter(anno,queryParams);
        List<String> corsiUri = new ArrayList<>();
        String baseUri = "http://localhost:8080/AdvancedWeb/rest/courses/" + year + "/";
        for (Corso corso: corsi) {
            corsiUri.add(baseUri + corso.getIdCorso());
        }
        return Response.ok(corsiUri).build();
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
