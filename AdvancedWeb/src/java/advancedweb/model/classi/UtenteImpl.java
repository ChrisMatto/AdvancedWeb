/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.model.classi;

import advancedweb.controller.data.DataLayerException;
import advancedweb.model.interfacce.Gruppo;
import advancedweb.model.interfacce.IgwDataLayer;
import advancedweb.model.interfacce.Utente;
import com.google.gson.annotations.Expose;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toni & Tony
 */
public class UtenteImpl implements Utente{
    
    @Expose
    private int id;
    
    @Expose
    private String username;
    
    @Expose(serialize=false,deserialize=true)
    private String password;
    
    @Expose
    private int docente;
    
    private transient Gruppo gruppo;
    
    @Expose
    private int id_gruppo;
    
    protected transient IgwDataLayer ownerdatalayer;
    
    protected transient boolean dirty;
    
    public UtenteImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.id=0;
        this.username=null;
        this.password=null;
        this.docente=0;
        this.gruppo=null;
        this.dirty=false;
        this.id_gruppo=-1;
    }
    
    public void setIDGruppo(int id_gruppo){
        this.id_gruppo=id_gruppo;
    }
    
    public int getID(){
        return this.id;
    }
    
    @Override
    public void setID(int id){
        this.id=id;
        this.dirty=true;
    }
    
    @Override
    public String getUsername(){
        return this.username;
    }
    
    @Override
    public String getPassword(){
        return this.password;
    }
    
    @Override
    public int getDocente(){
        return this.docente;
    }
    
    @Override
    public int getIDGruppo(){
        return this.id_gruppo;
    }
    
    @Override
    public Gruppo getGruppo() throws DataLayerException{
        if(gruppo==null&&id_gruppo>=0)
            gruppo=ownerdatalayer.getGruppo(id_gruppo);
        return this.gruppo;
    }

    @Override
    public void setGruppo(Gruppo gruppo){
        this.gruppo=gruppo;
        this.dirty=true;
    }
    
    @Override
    public void setUsername(String username){
        this.username=username;
        this.dirty=true;
    }
    
    @Override
    public void setPassword(String password){
        this.password=password;
        this.dirty=true;
    }
    
    @Override
    public void setDocente(int docente){
        this.docente=docente;
        this.dirty=true;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void copyFrom(Utente utente) {
        
        id = utente.getID();
        username = utente.getUsername();
        password = utente.getPassword();
        
        try{
           gruppo = utente.getGruppo();
           docente = utente.getDocente();
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }
    


    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void setDL(IgwDataLayer dl){
        this.ownerdatalayer=dl;
    }
}
