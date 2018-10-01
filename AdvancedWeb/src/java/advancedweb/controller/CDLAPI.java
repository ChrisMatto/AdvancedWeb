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




@Path("cdl")
public class CDLAPI{
    
    
    /*HTTP GET Request*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDL() throws DataLayerException, SQLException, NamingException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        List<CDL> cdl=dl.getCDLNoMag();
        String json = new Gson().toJson(cdl);
        return Response.ok(json).build();
       
    }
    
}