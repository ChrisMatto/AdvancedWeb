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
import advancedweb.model.classi.UtenteImpl;
import advancedweb.model.interfacce.IgwDataLayer;
import advancedweb.model.interfacce.Sessione;
import advancedweb.model.interfacce.Utente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Modifier;
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
import javax.ws.rs.PUT;
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
        if(json.isEmpty())
            return Response.status(400).build();
        TempLogin login=new Gson().fromJson(json,TempLogin.class);
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        String token=null;
        if(login.getUsername()!=null&&login.getPassword()!=null&&!login.getUsername().isEmpty()&&!login.getPassword().isEmpty()){
            Utente utente=dl.getUtenti(login.getUsername(),login.getPassword());
            if(utente!=null){
                Sessione sessione=new SessioneImpl(dl);
                sessione.setIDUtente(utente.getID());
                sessione.setUtente(utente);
                sessione.setData(new Timestamp(System.currentTimeMillis()));
                token=dl.makeSessione(sessione);
                return Response.ok(new Gson().toJson(token)).build();
            }
            else{
                return Response.status(401).build();
            }
        }
        else
            return Response.status(400).build();

    }
    
    @Path("logout")
    @POST
    public Response logout(String token) throws SQLException, NamingException, DataLayerException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        Properties p=new Gson().fromJson(token,Properties.class);
        String tok=p.getProperty("token");
        dl.deleteSessione(tok);
        return Response.ok().build();
    }
    
    @Path("{SID}/users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@PathParam("SID") String sid) throws DataLayerException, SQLException, NamingException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        if(!dl.getAccessToken(sid, "Users"))
            return Response.status(403).build();
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
    
    @Path("{SID}/users")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUser(@PathParam("SID") String sid,String user) throws DataLayerException,SQLException, NamingException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        if(!dl.getAccessToken(sid, "iUsers"))
            return Response.status(403).build();
        Utente utente=new Gson().fromJson(user,UtenteImpl.class);
        if(utente.getUsername()==null||utente.getUsername().trim().isEmpty()||utente.getPassword()==null||utente.getPassword().trim().isEmpty()||(utente.getIDGruppo()!=1&&utente.getIDGruppo()!=2))
            return Response.status(400).build();
        utente.setDL(dl);
        if(dl.existUtente(utente.getUsername()))
            return Response.status(409).build();
        /*Properties utente=new Gson().fromJson(user,Properties.class);
        if(utente.getProperty("username")==null||utente.getProperty("username").trim().isEmpty()||utente.getProperty("password")==null||utente.getProperty("password").trim().isEmpty()||utente.getProperty("gruppo")==null||(Integer.parseInt(utente.getProperty("gruppo"))!=1&&Integer.parseInt(utente.getProperty("gruppo"))!=2))
            return Response.status(400).build();
        Utente us=new UtenteImpl(dl);
        us.setUsername(utente.getProperty("username"));
        us.setPassword(utente.getProperty("password"));
        us.setIDGruppo(Integer.parseInt(utente.getProperty("gruppo")));
        if(dl.existUtente(us.getUsername()))
            return Response.status(409).build();*/
        dl.storeUtente(utente);
        return Response.ok().build();
    }
    
    @Path("{SID}/users/{ID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("SID") String sid,@PathParam("ID") int id) throws DataLayerException, SQLException, NamingException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        if(!dl.getAccessToken(sid, "Users"))
            return Response.status(403).build();
        Utente utente=dl.getUtente(id);
        if(utente==null)
            return Response.noContent().build();
        return Response.ok(new Gson().toJson(utente)).build();
    }
    
    @Path("{SID}/users/{ID}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modUser(@PathParam("SID") String sid,@PathParam("ID") int id,String json) throws DataLayerException, SQLException, NamingException{
        DataConnection data=new DataConnection();
        IgwDataLayer dl = data.getData();
        if(!dl.getAccessToken(sid, "Users"))
            return Response.status(403).build();
        Utente u=new Gson().fromJson(json, UtenteImpl.class);
        Utente utente=dl.getUtente(id);
        if(u.getUsername()!=null&&!u.getUsername().trim().isEmpty()){
            if(!u.getUsername().equals(utente.getUsername())&&dl.existUtente(u.getUsername()))
                return Response.status(409).build();
            utente.setUsername(u.getUsername());
        }
        if(u.getPassword()!=null&&!u.getPassword().trim().isEmpty())
            utente.setPassword(u.getPassword());
        if(u.getDocente()!=0){
            if(dl.checkDocente(id)||dl.getDocente(id)==null)
                return Response.status(409).build();
            utente.setDocente(u.getDocente());
        }
        if(u.getIDGruppo()==1||u.getIDGruppo()==2)
            utente.setIDGruppo(u.getIDGruppo());
        dl.storeUtente(utente);
        return Response.ok().build();
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
