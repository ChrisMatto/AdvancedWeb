package API;

import Classi.Docente;
import Classi.Versioni;
import ClassiTemp.DocenteCompleto;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TeachersAPI implements Resource {

    private String token;

    public TeachersAPI() {
        token = null;
    }

    public TeachersAPI(String token) {
        this.token = token;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers(@Context UriInfo uriInfo) {
        String baseUri;
        if (token != null) {
            baseUri = uriInfo.getBaseUri() + "auth/" + token +"/teachers/";
        } else {
            baseUri = uriInfo.getBaseUri() + "teachers/";
        }
        Versioni versione = DataAccess.getVersione("docente");
        if (versione != null) {
            return Response.ok(DataAccess.getDocenti(baseUri)).header("versione", versione.getVersione()).build();
        }
        return Response.ok(DataAccess.getDocenti(baseUri)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTeacher(@Context ServletContext context, @Context HttpHeaders headers, Docente docente) {
        String curriculumType = headers.getHeaderString("file-type");
        if (curriculumType != null && docente.getCurriculum() != null) {
            curriculumType = "." + curriculumType;
            byte[] byteCurriculum = Base64.getDecoder().decode(docente.getCurriculum());
            String stringPath = context.getRealPath("") + "curriculum/" + docente.getNome() + docente.getCognome();
            File existingFile = new File(stringPath + curriculumType);
            int i = 1;
            String newStringPath = stringPath;
            while (existingFile.exists()) {
                newStringPath = stringPath + i;
                i++;
                existingFile = new File(newStringPath + curriculumType);
            }
            InputStream fileStream = new ByteArrayInputStream(byteCurriculum);
            try {
                Files.copy(fileStream, Paths.get(newStringPath + curriculumType));
                docente.setCurriculum(newStringPath + curriculumType);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        int idDocente = DataAccess.insertDocente(docente);
        return Response.ok(idDocente).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("id") int id) {
        Docente docente = DataAccess.getDocente(id);
        if (docente != null) {
            return Response.ok(new DocenteCompleto(docente)).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacherCourses(@PathParam("id") int id) {
        return Response.ok(DataAccess.getCorsiDocente(id)).build();
    }

    @GET
    @Path("{id}/curriculum")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadCurriculum(@PathParam("id") int id) {
        Docente docente = DataAccess.getDocente(id);
        File file = Utils.getFile(docente.getCurriculum());
        return Response.ok(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
    }

}
