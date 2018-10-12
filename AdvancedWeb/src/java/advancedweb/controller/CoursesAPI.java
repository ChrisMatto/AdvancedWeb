/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.controller;

import javax.ws.rs.Path;
import advancedweb.controller.data.DataConnection;
import advancedweb.controller.data.DataLayerException;
import advancedweb.model.classi.CorsoImpl;
import advancedweb.model.interfacce.CDL;
import advancedweb.model.interfacce.Corso;
import advancedweb.model.interfacce.IgwDataLayer;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Properties;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

/**
 *
 * @author Chris-PC
 */
@Path("courses")
public class CoursesAPI {
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() throws DataLayerException, SQLException, NamingException, Exception {
        DataConnection data = new DataConnection();
        IgwDataLayer dl = data.getData();
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        if(month <= 8)
            year=year-1;
        List<Corso> corsi = dl.getCorsiByAnno(year);
        String json = new Gson().toJson(corsi);
        dl.close();
        return Response.ok(json).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(String json) throws SQLException, NamingException, DataLayerException {
        DataConnection data = new DataConnection();
        IgwDataLayer dl = data.getData();
        Corso corso = new Gson().fromJson(json, CorsoImpl.class);
        dl.storeCorso(corso);
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        if(month <= 8)
            year=year-1;
        List<Corso> corsi = dl.getCorsiByAnno(year);
        String newJson = new Gson().toJson(corsi);
        return Response.ok(newJson).build();
    }
    
    @Path("{CDL}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseCDL(@PathParam("CDL") int idCDL) throws DataLayerException, SQLException, NamingException, Exception {
        DataConnection data = new DataConnection();
        IgwDataLayer dl = data.getData();
        CDL cdl = dl.getCDL(idCDL);
        List<Corso> corsi = cdl.getCorsiInCdl();
        String json = new Gson().toJson(corsi);
        dl.close();
        return Response.ok(json).build();
    }
}
