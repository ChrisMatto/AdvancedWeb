/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.model.interfacce;
import advancedweb.controller.data.DataLayerException;

/**
 *
 * @author Toni & Tony
 */
public interface Utente {
    
    int getIDGruppo();
    
    void setIDGruppo(int id_gruppo);
    
    void setID(int id);
    
    int getID();
    
    String getUsername();
    
    String getPassword();
    
    int getDocente();
    
    Gruppo getGruppo() throws DataLayerException;
    
    void setGruppo(Gruppo gruppo);
    
    void setUsername(String username);
    
    void setPassword(String password);
    
    void setDocente(int docente);

    boolean isDirty();
    
    void copyFrom(Utente utente);

    void setDirty(boolean dirty);
    
    void setDL(IgwDataLayer dl);
    
}
