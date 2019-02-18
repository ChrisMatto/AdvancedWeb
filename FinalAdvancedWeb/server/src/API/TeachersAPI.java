package API;

import Classi.Docente;
import Classi.Versioni;
import Views.DocenteCompleto;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TeachersAPI implements Resource {

    @Inject
    private DataAccess dataAccess;

    @PathParam("SID")
    private String token;

    @Context
    ResourceContext resourceContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers(@Context UriInfo uriInfo) {
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/teachers/";
        } else {
            baseUri = uriInfo.getBaseUri() + "teachers/";
        }
        Versioni versione = dataAccess.getVersione("docente");
        if (versione != null) {
            return Response.ok(dataAccess.getDocenti(baseUri)).header("versione", versione.getVersione()).build();
        }
        return Response.ok(dataAccess.getDocenti(baseUri)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTeacher(@Context HttpHeaders headers, Docente docente) {
        String curriculumType = headers.getHeaderString("file-type");
        String imageType = headers.getHeaderString("image-type");
        if (curriculumType != null && docente.getCurriculum() != null && !docente.getCurriculum().trim().isEmpty()) {
            docente.setCurriculum(Utils.saveNewFile(docente.getCurriculum(), "curriculum/" + docente.getNome() + docente.getCognome(), curriculumType));
        } else {
            docente.setCurriculum(null);
        }
        if (imageType != null && docente.getImmagine() != null && !docente.getImmagine().trim().isEmpty()) {
            docente.setImmagine(Utils.saveNewFile(docente.getImmagine(), "imgDocenti/" + docente.getNome() + docente.getCognome(), imageType));
        } else {
            docente.setImmagine(null);
        }
        int idDocente = dataAccess.insertDocente(docente);
        dataAccess.saveLog(token, "ha inserito il nuovo docente " + docente.getNome() + " " + docente.getCognome());
        return Response.ok(idDocente).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@Context ServletContext context, @Context HttpHeaders headers, @PathParam("id") int idDocente, Docente docente) {
        String curriculumType = headers.getHeaderString("file-type");
        String imageType = headers.getHeaderString("image-type");
        Docente dbDocente = dataAccess.getDocente(idDocente);
        if (curriculumType != null && docente.getCurriculum() != null && !docente.getCurriculum().trim().isEmpty()) {
            if (dbDocente != null) {
                docente.setCurriculum(Utils.updateFile(docente.getCurriculum(), dbDocente.getCurriculum(), "curriculum/" + docente.getNome() + docente.getCognome(), curriculumType));
            }
        } else {
            docente.setCurriculum(null);
        }
        if (imageType != null && docente.getImmagine() != null && !docente.getImmagine().trim().isEmpty()) {
            if (dbDocente != null) {
                docente.setImmagine(Utils.updateFile(docente.getImmagine(), dbDocente.getImmagine(), "imgDocenti/" + docente.getNome() + docente.getCognome(), imageType));
            }
        } else {
            docente.setImmagine(null);
        }
        dataAccess.updateDocente(idDocente, docente);
        dataAccess.saveLog(token, "ha modificato il profilo del docente " + docente.getNome() + " " + docente.getCognome());
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("id") int id) {
        Docente doc = dataAccess.getDocente(id);
        if (doc != null) {
            Docente docente = new Docente();
            docente.copyFrom(doc);
            if (docente.getImmagine() != null && !docente.getImmagine().trim().isEmpty()) {
                docente.setImmagine(Utils.getEncodedFile(docente.getImmagine()));
            }
            DocenteCompleto fullDoc = resourceContext.getResource(DocenteCompleto.class);
            fullDoc.init(docente);
            return Response.ok(fullDoc).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacherCourses(@PathParam("id") int id) {
        return Response.ok(dataAccess.getCorsiDocente(id)).build();
    }

    @GET
    @Path("{id}/curriculum")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadCurriculum(@PathParam("id") int id) {
        Docente docente = dataAccess.getDocente(id);
        File file = Utils.getFile(docente.getCurriculum());
        return Response.ok(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
    }

}
