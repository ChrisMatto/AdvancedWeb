package API;

import Classi.*;
import Views.*;
import Controller.Utils;
import DataAccess.DataAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoursesAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @Context
    ResourceContext context;

    @GET
    @Path("{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsi(@PathParam("year") String year, @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        int anno = Utils.getYear(year);
        String annoString = String.valueOf(anno);
        if (anno == Utils.getCurrentYear()) {
            annoString = "current";
        }
        List<Integer> corsi = dataAccess.getCorsiByFilter(anno, queryParams);
        List<String> corsiUri = new ArrayList<>();
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token + "/courses/" + annoString + "/";
        } else {
            baseUri = uriInfo.getBaseUri() + "courses/" + annoString + "/";
        }
        for (int id: corsi) {
            corsiUri.add(baseUri + id);
        }
        Versioni versione = dataAccess.getVersione("corso");
        if (versione != null) {
            return Response.ok(corsiUri).header("versione", versione.getVersione()).build();
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
        dataAccess.insertCorso(corso);
        dataAccess.saveLog(token, "ha inserito il nuovo corso " + corso.getNomeIt());
        return Response.ok().build();
    }

    @Path("history/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory(@PathParam("id") int id, @Context UriInfo uriInfo) {
        if (id < 0) {
            return Response.status(404).build();
        }
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/courses/";
        } else {
            baseUri = uriInfo.getBaseUri() + "courses/";
        }
        List<HistoryCorso> history = dataAccess.getHistoryCorso(id, baseUri);
        return Response.ok(history).build();
    }

    @Path("{year}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorso(@PathParam("id") int id, @PathParam("year") String year, @Context UriInfo uriInfo) {
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(id, anno);
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/courses/";
        } else {
            baseUri = uriInfo.getBaseUri() + "courses/";
        }
        if (corso != null) {
            CorsoCompleto corsoCompleto = context.getResource(CorsoCompleto.class);
            corsoCompleto.init(corso, baseUri);
            return Response.ok(corsoCompleto).build();
        }
        return Response.noContent().build();
    }

    @Path("{year}/{id}/{lingua:it|en}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoLingua(@PathParam("id") int id, @PathParam("year") String year, @PathParam("lingua") String lingua, @Context UriInfo uriInfo) throws JsonProcessingException {
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(id, anno);
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/courses/";
        } else {
            baseUri = uriInfo.getBaseUri() + "courses/";
        }
        if (corso != null) {
            CorsoCompleto corsoCompleto = context.getResource(CorsoCompleto.class);
            corsoCompleto.init(corso, baseUri);
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
            dataAccess.updateCorso(id, anno, corsoCompleto);
            dataAccess.saveLog(token, "ha modificato le informazioni del corso " + corsoCompleto.getNomeIt() + " dell'anno " + Utils.getYear(year));
        }
        return Response.ok().build();
    }

    @Path("{year}/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(id, Utils.getYear(year));
        dataAccess.saveLog(token, "ha eliminato il corso " + corso.getNomeIt() + " dell'anno " + Utils.getYear(year));
        dataAccess.deleteCorso(id, anno);
        return Response.ok().build();
    }

    @Path("{year}/{id}/basic")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBasicCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(id, anno);
        return Response.ok(corso).build();
    }

    @Path("{year}/{id}/basic/{lingua:it|en}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBasicCorsoLingua(@PathParam("year") String year, @PathParam("id") int id, @PathParam("lingua") String lingua) throws JsonProcessingException {
        int anno = Utils.getYear(year);
        Corso corso = dataAccess.getCorso(id, anno);
        ObjectMapper mapper = new ObjectMapper();
        String jsonCorso;
        if (lingua.equals("it")) {
            jsonCorso = mapper.writerWithView(Views.CorsoIta.class).writeValueAsString(corso);
        } else {
            jsonCorso = mapper.writerWithView(Views.CorsoEn.class).writeValueAsString(corso);
        }
        return Response.ok(jsonCorso).build();
    }

    @Path("{year}/{id}/syllabus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSyllabus(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        Sillabo sillabo = context.getResource(Sillabo.class);
        sillabo.init(id, anno);
        return Response.ok(sillabo).build();
    }

    @Path("{year}/{id}/syllabus/{lingua:it|en}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSyllabusLingua(@PathParam("year") String year, @PathParam("id") int id, @PathParam("lingua") String lingua) throws JsonProcessingException {
        int anno = Utils.getYear(year);
        Sillabo sillabo = context.getResource(Sillabo.class);
        sillabo.init(id, anno);
        ObjectMapper mapper = new ObjectMapper();
        String jsonSillabo;
        if (lingua.equals("it")) {
            jsonSillabo = mapper.writerWithView(Views.SillaboIt.class).writeValueAsString(sillabo);
        } else {
            jsonSillabo = mapper.writerWithView(Views.SillaboEn.class).writeValueAsString(sillabo);
        }
        return Response.ok(jsonSillabo).build();
    }

    @Path("{year}/{id}/teachers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocentiCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        List<DocentePerCorso> docenti = new ArrayList<>();
        List<Docente> docList = dataAccess.getDocentiInCorso(id, anno);
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
        if (docenti != null) {
            dataAccess.updateDocentiCorso(id, anno, docenti);
        } else {
            dataAccess.deleteDocentiCorso(id, anno);
        }
        Corso corso = dataAccess.getCorso(id, anno);
        dataAccess.saveLog(token, "ha modificato la lista dei docenti del corso " + corso.getNomeIt() + " dell'anno " + anno);
        dataAccess.updateVersione("corso");
        return Response.ok().build();
    }

    @Path("{year}/{id}/relations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRelazioniCorso(@PathParam("year") String year, @PathParam("id") int id, @Context UriInfo uriInfo) {
        int anno = Utils.getYear(year);
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/courses/";
        } else {
            baseUri = uriInfo.getBaseUri() + "courses/";
        }
        RelazioniCorso relazioni = dataAccess.getRelazioniCorso(id, anno, baseUri);
        return Response.ok(relazioni).build();
    }

    @Path("{year}/{id}/relations")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRelazioniCorso(@PathParam("year") String year, @PathParam("id") int id, RelazioniCorso relazioni) {
        int anno = Utils.getYear(year);
        if (relazioni == null) {
            dataAccess.updateRelazioniCorso(id, anno, null, "propedeutico");
            dataAccess.updateRelazioniCorso(id, anno, null, "modulo");
            dataAccess.updateRelazioniCorso(id, anno, null, "mutuato");
        } else {
            dataAccess.updateRelazioniCorso(id, anno, relazioni.getPropedeudici(), "propedeutico");
            dataAccess.updateRelazioniCorso(id, anno, relazioni.getModulo(), "modulo");
            dataAccess.updateRelazioniCorso(id, anno, relazioni.getMutuati(), "mutuato");
        }
        Corso corso = dataAccess.getCorso(id, anno);
        dataAccess.saveLog(token, "ha modificato la lista delle relazioni del corso " + corso.getNomeIt() + " dell'anno " + anno);
        dataAccess.updateVersione("corso");
        return Response.ok().build();
    }

    @Path("{year}/{id}/links")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLinksCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        return Response.ok(dataAccess.getLinks(id, anno)).build();
    }

    @Path("{year}/{id}/links")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLinksCorso(@PathParam("year") String year, @PathParam("id") int id, Links links) {
        int anno = Utils.getYear(year);
        if (links != null) {
            dataAccess.updateLinks(id, anno, links);
        } else {
            dataAccess.deleteLinks(id, anno);
        }
        Corso corso = dataAccess.getCorso(id, anno);
        dataAccess.saveLog(token, "ha modificato i link del corso " + corso.getNomeIt() + " dell'anno " + anno);
        dataAccess.updateVersione("corso");
        return Response.ok().build();
    }

    @Path("years")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnniCorsi() {
        return Response.ok(dataAccess.getAnniCorsi()).build();
    }

    @Path("{year}/{id}/material")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaterialeCorso(@PathParam("year") String year, @PathParam("id") int id) {
        int anno = Utils.getYear(year);
        return Response.ok(dataAccess.getMaterialeCorso(id, anno)).build();
    }

    @Path("material/{idMateriale}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getMateriale(@PathParam("idMateriale") int idMateriale) {
        Materiale materiale = dataAccess.getMateriale(idMateriale);
        File file = Utils.getFile(materiale.getLink());
        return Response.ok(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
    }
}
