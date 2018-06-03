/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.model.classi;

import advancedweb.controller.data.DataLayerException;
import advancedweb.model.interfacce.IgwDataLayer;
import advancedweb.model.interfacce.Sessione;
import advancedweb.model.interfacce.Utente;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris-PC
 */
public class SessioneImpl implements Sessione{
    
    private String token;
    
    private Timestamp data;
    
    private Utente utente;
    
    private int id_utente;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    public SessioneImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.token=null;
        this.data=null;
        this.utente=null;
        this.id_utente=-1;
        this.dirty=false;
    }

    @Override
    public void setToken(String token) {
        this.token=token;
        this.dirty=true;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public void setData(Timestamp data) {
        this.data=data;
        this.dirty=true;
    }

    @Override
    public Timestamp getData() {
        return this.data;
    }
    
    @Override
    public int getIDUtente(){
        return this.id_utente;
    }
    
    @Override
    public void setIDUtente(int id){
        this.id_utente=id;
        this.dirty=true;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente=utente;
        this.dirty=true;
    }

    @Override
    public Utente getUtente() throws DataLayerException {
        if(utente==null&&id_utente>=0)
            utente=ownerdatalayer.getUtente(id_utente);
        return utente;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty=dirty;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void copyFrom(Sessione sessione) {
        this.token=sessione.getToken();
        this.data=sessione.getData();
        this.id_utente=sessione.getIDUtente();
        try {
            utente=sessione.getUtente();
        } catch (DataLayerException ex) {
            Logger.getLogger(LogImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty=true;
    }
    
}
