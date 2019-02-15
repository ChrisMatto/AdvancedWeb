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
        String imageType = headers.getHeaderString("image-type");
        if (curriculumType != null && docente.getCurriculum() != null && !docente.getCurriculum().trim().isEmpty()) {
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
                docente.setCurriculum("curriculum/" + docente.getNome() + docente.getCognome() + curriculumType);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            docente.setCurriculum(null);
        }
        if (imageType != null && docente.getImmagine() != null && !docente.getImmagine().trim().isEmpty()) {
            imageType = "." + imageType;
            byte[] byteImage = Base64.getDecoder().decode(docente.getImmagine());
            String stringPath = context.getRealPath("") + "imgDocenti/" + docente.getNome() + docente.getCognome();
            File existingFile = new File(stringPath + imageType);
            int i = 1;
            String newStringPath = stringPath;
            while (existingFile.exists()) {
                newStringPath = stringPath + i;
                i++;
                existingFile = new File(newStringPath + imageType);
            }
            InputStream fileStream = new ByteArrayInputStream(byteImage);
            try {
                Files.copy(fileStream, Paths.get(newStringPath + imageType));
                docente.setImmagine("imgDocenti/" + docente.getNome() + docente.getCognome() + imageType);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            docente.setImmagine(null);
        }
        int idDocente = DataAccess.insertDocente(docente);
        return Response.ok(idDocente).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@Context ServletContext context, @Context HttpHeaders headers, @PathParam("id") int idDocente, Docente docente) {
        String curriculumType = headers.getHeaderString("file-type");
        String imageType = headers.getHeaderString("image-type");
        if (curriculumType != null && docente.getCurriculum() != null && !docente.getCurriculum().trim().isEmpty()) {
            curriculumType = "." + curriculumType;
            byte[] byteCurriculum = Base64.getDecoder().decode(docente.getCurriculum());
            String stringPath = context.getRealPath("") + "curriculum/" + docente.getNome() + docente.getCognome();
            File existingFile = new File(stringPath + curriculumType);
            if (existingFile.exists()) {
                try {
                    Files.delete(Paths.get(stringPath + curriculumType));
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
            InputStream fileStream = new ByteArrayInputStream(byteCurriculum);
            try {
                Files.copy(fileStream, Paths.get(stringPath + curriculumType));
                docente.setCurriculum("curriculum/" + docente.getNome() + docente.getCognome() + curriculumType);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            docente.setCurriculum(null);
        }
        if (imageType != null && docente.getImmagine() != null && !docente.getImmagine().trim().isEmpty()) {
            imageType = "." + imageType;
            byte[] byteImage = Base64.getDecoder().decode(docente.getImmagine());
            String stringPath = context.getRealPath("") + "imgDocenti/" + docente.getNome() + docente.getCognome();
            File existingFile = new File(stringPath + imageType);
            while (existingFile.exists()) {
                try {
                    Files.delete(Paths.get(stringPath + imageType));
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
            InputStream fileStream = new ByteArrayInputStream(byteImage);
            try {
                Files.copy(fileStream, Paths.get(stringPath + imageType));
                docente.setImmagine("imgDocenti/" + docente.getNome() + docente.getCognome() + imageType);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            docente.setImmagine(null);
        }
        DataAccess.updateDocente(idDocente, docente);
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@Context ServletContext context, @PathParam("id") int id) {
        Docente doc = DataAccess.getDocente(id);
        if (doc != null) {
            Docente docente = new Docente();
            docente.copyFrom(doc);
            if (docente.getImmagine() != null && !docente.getImmagine().trim().isEmpty()) {
                String imagePath = context.getRealPath("") + docente.getImmagine();
                File image = new File(imagePath);
                if (image.exists()) {
                    try {
                        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                        docente.setImmagine(Base64.getEncoder().encodeToString(imageBytes));
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                } else {
                    docente.setImmagine(null);
                }
            }
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
