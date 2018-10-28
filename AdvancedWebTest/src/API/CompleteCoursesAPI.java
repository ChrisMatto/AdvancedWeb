package API;

import Classi.Corso;
import ClassiTemp.CorsoCompleto;
import Controller.Utils;
import Controller.YearError;
import DataAccess.DataAccess;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("completeCourses")
public class CompleteCoursesAPI {

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
        List<CorsoCompleto> corsiCompleti = new ArrayList<>();
        for (Corso corso: corsi) {
            corsiCompleti.add(new CorsoCompleto(corso));
        }
        return Response.ok(corsiCompleti).build();
    }


    @Path("{year}/{id}")
    @GET
    public Response getCorso(@Context ContainerRequestContext containerRequestContext) {
        String methodPath = containerRequestContext.getUriInfo().getPath().replace("completeCourses", "courses");
        URI uri = URI.create(containerRequestContext.getUriInfo().getBaseUri() + methodPath);
        return Response.temporaryRedirect(uri).build();
    }
}
