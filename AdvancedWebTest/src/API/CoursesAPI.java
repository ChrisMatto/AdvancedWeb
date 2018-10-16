package API;

import com.google.gson.Gson;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("courses")
public class CoursesAPI {
    private static Gson gson = new Gson();

    @Path("{year}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(@PathParam("year") String year) {
        if(year.equals("current"))
            return Response.ok().build();
        if(NumberUtils.isParsable(year) && Integer.parseInt(year) > 0)
            return Response.ok().build();
        return Response.status(404).build();
    }
}
