package API;

import Controller.Utils;
import Controller.YearError;
import DataAccess.DataAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesAPI implements Resource {

    private String token;

    public CoursesAPI() {
        token = null;
    }

    public CoursesAPI(String token) {
        this.token = token;
    }

    @GET
    @Path("{year}")
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
        List<Integer> corsi = DataAccess.getCorsiByFilter(anno,queryParams);
        List<String> corsiUri = new ArrayList<>();
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token + "/courses/" + year + "/";
        } else {
            baseUri = uriInfo.getBaseUri() + "courses/" + year + "/";
        }
        for (int id: corsi) {
            corsiUri.add(baseUri + id);
        }
        return Response.ok(corsiUri).build();
    }
}
