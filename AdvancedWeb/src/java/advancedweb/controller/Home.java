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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
@Path("home")
public class Home {
    
    @Path("cdl")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDL() throws DataLayerException, SQLException, NamingException, Exception{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        List<CDL> cdl=dl.getCDLNoMag();
        
        Random rand= new Random();
        List<CDL> rcdl=new ArrayList();
        int n=4;
        int cdlsize=cdl.size();
        for (int i=0;i<n;i++){
                if(!cdl.isEmpty() &&i<=cdlsize){
                    int randomIndex=rand.nextInt(cdl.size());
                    rcdl.add(cdl.get(randomIndex));
                    cdl.remove(randomIndex);
                }
        }
        
        String json = new Gson().toJson(rcdl);
        dl.close();
        return Response.ok(json).build();
       
    }
    
    
    @Path("cdlm")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDLM() throws DataLayerException, SQLException, NamingException, Exception{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        List<CDL> cdlm=dl.getCDLMag();
        
        Random rand= new Random();
        List<CDL> rcdlm=new ArrayList();
        int n=4;
        int cdlmsize=cdlm.size();
        for (int i=0;i<n;i++){
                if(!cdlm.isEmpty() &&i<=cdlmsize){
                    int randomIndex=rand.nextInt(cdlm.size());
                    rcdlm.add(cdlm.get(randomIndex));
                    cdlm.remove(randomIndex);
                }
        }
        
        String json = new Gson().toJson(rcdlm);
        dl.close();
        return Response.ok(json).build();
       
    }
    
}
