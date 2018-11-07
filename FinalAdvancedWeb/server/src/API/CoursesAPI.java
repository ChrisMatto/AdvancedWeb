package API;

import Classi.Corso;
import Classi.Docente;
import ClassiTemp.*;
import Controller.Utils;
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
        int anno = Utils.getYear(year);
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
        int anno = Utils.getYear(year);
        if (corso == null || (corso.getNomeIt() == null && corso.getNomeEn() == null)) {
            return Response.status(400).build();
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
        int anno = Utils.getYear(year);
        Corso corso = DataAccess.getCorso(id, anno);
        if (corso != null) {
            return Response.ok(new CorsoCompleto(corso)).build();
        }
        return Response.noContent().build();
    }

    @Path("{year}/{id}/{lingua:it|en}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoLingua(@PathParam("id") int id, @PathParam("year") String year, @PathParam("lingua") String lingua) throws JsonProcessingException {
        int anno = Utils.getYear(year);
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
        int anno = Utils.getYear(year);
        if (corsoCompleto != null) {
            DataAccess.updateCorso(id, anno, corsoCompleto);
        }
        return Response.ok().build();
    }

    @Path("{year}/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        DataAccess.deleteCorso(id, anno);
        return Response.ok().build();
    }

    @Path("{year}/{id}/basic")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBasicCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        Corso corso = DataAccess.getCorso(id, anno);
        return Response.ok(corso).build();
    }

    @Path("{year}/{id}/basic/{lingua:it|en}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBasicCorsoLingua(@PathParam("year") String year, @PathParam("id") int id, @PathParam("lingua") String lingua) throws JsonProcessingException {
        int anno = Utils.getYear(year);
        Corso corso = DataAccess.getCorso(id, anno);
        ObjectMapper mapper = new ObjectMapper();
        String jsonCorso;
        if (lingua.equals("it")) {
            jsonCorso = mapper.writerWithView(Views.CorsoIta.class).writeValueAsString(corso);
        } else {
            jsonCorso = mapper.writerWithView(Views.CorsoEn.class).writeValueAsString(corso);
        }
        return Response.ok(jsonCorso).build();
    }

    @Path("{year}/{id}/teachers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocentiCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        List<DocentePerCorso> docenti = new ArrayList<>();
        List<Docente> docList = DataAccess.getDocentiInCorso(id, anno);
        for (Docente doc : docList) {
            docenti.add(new DocentePerCorso(doc));
        }
        return Response.ok(docenti).build();
    }

    @Path("{year}/{id}/teachers")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDocentiCorso(@PathParam("year") String year, @PathParam("id") int id, List<DocentePerCorso> docenti) {
        int anno = Utils.getYear(year);
        DataAccess.updateDocentiCorso(id, anno, docenti);
        return Response.ok().build();
    }

    @Path("{year}/{id}/relations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRelazioniCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        RelazioniCorso relazioni = DataAccess.getRelazioniCorso(id, anno);
        return Response.ok(relazioni).build();
    }

    @Path("{year}/{id}/relations")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRelazioniCorso(@PathParam("year") String year, @PathParam("id") int id, RelazioniCorso relazioni) {
        int anno = Utils.getYear(year);
        if (relazioni == null) {
            DataAccess.updateRelazioniCorso(id, anno, null, "propedeutico");
            DataAccess.updateRelazioniCorso(id, anno, null, "modulo");
            DataAccess.updateRelazioniCorso(id, anno, null, "mutuato");
        } else {
            DataAccess.updateRelazioniCorso(id, anno, relazioni.getPropedeudici(), "propedeutico");
            DataAccess.updateRelazioniCorso(id, anno, relazioni.getModulo(), "modulo");
            DataAccess.updateRelazioniCorso(id, anno, relazioni.getMutuati(), "mutuato");
        }
        return Response.ok().build();
    }
}