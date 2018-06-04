/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.controller;

import advancedweb.controller.data.DataConnection;
import advancedweb.controller.data.DataLayerException;
import advancedweb.model.classi.SessioneImpl;
import advancedweb.model.classi.TempLogin;
import advancedweb.model.interfacce.IgwDataLayer;
import advancedweb.model.interfacce.Sessione;
import advancedweb.model.interfacce.Utente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Chris-PC
 */
@Path("auth")
public class Auth {
    
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) throws SQLException, NamingException, DataLayerException{
        //Map<String,String> list=new Gson().fromJson(json,new TypeToken<Map<String,String>>() {}.getType());
        TempLogin login=new Gson().fromJson(json,TempLogin.class);
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        String token=null;
        if(login.getUsername()!=null&&login.getPassword()!=null){
            Utente utente=dl.getUtenti(login.getUsername(),login.getPassword());
            if(utente!=null){
                Sessione sessione=new SessioneImpl(dl);
                sessione.setIDUtente(utente.getID());
                sessione.setUtente(utente);
                sessione.setData(new Timestamp(System.currentTimeMillis()));
                token=dl.makeSessione(sessione);
            }
            else{
                token="Wrong Username or Password";
            }
        }
        
        return Response.ok(new Gson().toJson(token)).build();
    }
    
    @Path("logout")
    @DELETE
    public void logout(String token) throws SQLException, NamingException, DataLayerException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        Properties p=new Gson().fromJson(token,Properties.class);
        String tok=p.getProperty("token");
        dl.deleteSessione(tok);
    }
    
    @Path("{SID}/users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@PathParam("SID") String sid) throws DataLayerException, SQLException, NamingException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        List<Integer> users=dl.getAllUsers();
        String base="http://localhost:8084/AdvancedWeb/rest/auth/"+sid+"/users/";
        List<String> json=new ArrayList();
        Iterator<Integer> it=users.iterator();
        while(it.hasNext()){
            int uid=it.next();
            json.add(base+uid);
        }
        return Response.ok(new Gson().toJson(json)).build();
    }
    
    @Path("{SID}/cdl")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDL() throws DataLayerException, SQLException, NamingException{
        return (new CDLAPI()).getCDL();
    }
    
    @Path("{SID}/cdlm")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCDLM() throws DataLayerException, SQLException, NamingException{
        return (new CDLMAPI()).getCDLM();
    }
}
