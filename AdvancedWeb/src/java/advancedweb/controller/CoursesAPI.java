/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.controller;

import javax.ws.rs.Path;
import advancedweb.controller.data.DataConnection;
import advancedweb.controller.data.DataLayerException;
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
    @Path("cdl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseCDL(String json) throws DataLayerException, SQLException, NamingException, Exception {
        DataConnection data = new DataConnection();
        IgwDataLayer dl = data.getData();
        System.out.print("qui");
        Properties p = new Gson().fromJson(json, Properties.class);
        int id = Integer.parseInt(p.getProperty("id"));
        Corso corso = dl.getCorso(id);
        List<CDL> cdl = dl.getCDLInCorso(corso);
        String newJson = new Gson().toJson(cdl);
        dl.close();
        return Response.ok(newJson).build();
    }
}
