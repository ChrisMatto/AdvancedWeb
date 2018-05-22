/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.model.interfacce;

import advancedweb.controller.data.DataLayerException;
import java.util.List;

/**
 *
 * @author Toni & Tony
 */
public interface Gruppo {
    
    void setIDGruppo(int id);
    
    int getIDGruppo();
    
    String getNome();
    
    void setNome(String nome);
    
    List<Utente> getUtenti() throws DataLayerException;
    
    void setUtenti(List<Utente> utente);
    
    void addUtente(Utente utente);
    
    List<Servizio> getServizi() throws DataLayerException;
    
    void setServizi(List<Servizio> servizi);
    
    void addServizio(Servizio servizio);
    
}
