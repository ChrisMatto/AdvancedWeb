/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.controller;

import advancedweb.controller.data.DataConnection;
import advancedweb.controller.data.DataLayerException;
import advancedweb.model.interfacce.CDL;
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

/**
 *
 * @author Chris-PC
 */
@Path("cdlm")
public class CDLMAPI {
    /*HTTP GET Request*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDLM() throws DataLayerException, SQLException, NamingException, Exception{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        List<CDL> cdl=dl.getCDLMag();
        String json = new Gson().toJson(cdl);
        dl.close();
        return Response.ok(json).build();
       
    }
}
