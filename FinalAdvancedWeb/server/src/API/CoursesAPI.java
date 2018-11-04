package API;

import Classi.Corso;
import ClassiTemp.CorsoCompleto;
import ClassiTemp.Views;
import ClassiTemp.HistoryCorso;
import Controller.Utils;
import Controller.YearError;
import DataAccess.DataAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
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

    @POST
    @Path("{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCorso(@PathParam("year") String year, @Context UriInfo uriInfo, CorsoCompleto corso) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        if (!queryParams.isEmpty()) {
            return Response.status(400).build();
        }
        int anno;
        Object obj = Utils.getYear(year);
        if (obj instanceof YearError) {
            return Response.status(((YearError) obj).getError()).build();
        } else {
            anno = (int)obj;
        }
        if (corso == null) {
            corso = new CorsoCompleto();
        }
        corso.setAnno(anno);
        DataAccess.insertCorso(corso);
        return Response.ok().build();
    }

    @Path("history/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory(@PathParam("id") int id, @Context UriInfo uriInfo) {
        if (id < 0) {
            return Response.status(404).build();
        }
        List<HistoryCorso> history = DataAccess.getHistoryCorso(id, uriInfo.getBaseUri() + "courses/");
        return Response.ok(history).build();
    }

    @Path("{year}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorso(@PathParam("id") int id, @PathParam("year") String year) {
        int anno;
        Object obj = Utils.getYear(year);
        if (obj instanceof YearError) {
            return Response.status(((YearError) obj).getError()).build();
        } else {
            anno = (int)obj;
        }
        Corso corso = DataAccess.getCorso(id, anno);
        if (corso != null) {
            return Response.ok(new CorsoCompleto(corso)).build();
        }
        return Response.noContent().build();
    }

    @Path("{year}/{id}/{lingua:it|en}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoIta(@PathParam("id") int id, @PathParam("year") String year, @PathParam("lingua") String lingua) throws JsonProcessingException {
        int anno;
        Object obj = Utils.getYear(year);
        if (obj instanceof YearError) {
            return Response.status(((YearError) obj).getError()).build();
        } else {
            anno = (int)obj;
        }
        Corso corso = DataAccess.getCorso(id, anno);
        if (corso != null) {
            CorsoCompleto corsoCompleto = new CorsoCompleto(corso);
            ObjectMapper mapper = new ObjectMapper();
            String jsonCorso;
            if (lingua.equals("it")) {
                jsonCorso = mapper.writerWithView(Views.CorsoIta.class).writeValueAsString(corsoCompleto);
            } else {
                jsonCorso = mapper.writerWithView(Views.CorsoEn.class).writeValueAsString(corsoCompleto);
            }
            return Response.ok(jsonCorso).build();
        }
        return Response.noContent().build();
    }

    @Path("{year}/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCorso(@PathParam("year") String year, @PathParam("id") int id, CorsoCompleto corsoCompleto) {
        int anno;
        Object obj = Utils.getYear(year);
        if (obj instanceof YearError) {
            return Response.status(((YearError) obj).getError()).build();
        } else {
            anno = (int)obj;
        }
        DataAccess.updateCorso(id, anno, corsoCompleto);
        return Response.ok().build();
    }
}
