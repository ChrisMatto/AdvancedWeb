package API;

import Classi.Corso;
import Controller.Utils;
import Controller.YearError;
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
        Object obj = Utils.getYear(year);
        if (obj instanceof YearError) {
            return Response.status(((YearError) obj).getError()).build();
        } else {
            anno = (int)obj;
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
        int anno;
        Object obj = Utils.getYear(year);
        if (obj instanceof YearError) {
            return Response.status(((YearError) obj).getError()).build();
        } else {
            anno = (int)obj;
        }
        corso = DataAccess.getCorso(anno, id);
        return Response.ok(corso).build();
    }
}
