/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package advancedweb.model.interfacce;

import advancedweb.controller.data.DataLayerException;
import java.sql.Timestamp;

/**
 *
 * @author Chris-PC
 */
public interface Sessione {
    
    void setToken(String token);
    
    String getToken();
    
    void setData(Timestamp data);
    
    Timestamp getData();
    
    int getIDUtente();
    
    void setIDUtente(int id);
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Sessione sessione);
}
