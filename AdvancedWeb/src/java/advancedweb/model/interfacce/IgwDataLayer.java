/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.model.interfacce;



import advancedweb.controller.data.DataLayer;
import advancedweb.controller.data.DataLayerException;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Toni & Tony
 */
public interface IgwDataLayer extends DataLayer {
    
    CDL createCDL();
    
    Corso createCorso();
    
    Docente createDocente();
    
    //Docenti_Corso createDocenti_Corso();
    
    Descrizione_it createDescrizione_it();
    
    Descrizione_en createDescrizione_en();
    
    Dublino_it createDublino_it();

    Dublino_en createDublino_en();

    Materiale createMateriale();
    
    Libro createLibro();
    
   // Libri_Corso createLibri_corso();
    
    //Colleg_Corsi createColleg_Corsi();
    
    Gruppo createGruppo();
    
    Utente createUtente();
    
    Servizio createServizio();
    
    //Group_Services createGroup_Services();
    
    Log CreateLog();
    
    Sessione createSessione();
    
    void deleteSessione(String token) throws DataLayerException;
    
    CDL getCDL(int IDCdl) throws DataLayerException;
    
    List<CDL> getCDL() throws DataLayerException;
    
    List<CDL> getCDLMag() throws DataLayerException;
    
    Corso getCorso(int IDCorso) throws DataLayerException;
    
    List<Corso> getCorso() throws DataLayerException;

    Docente getDocente(int IDDocente) throws DataLayerException;
    
    //Docenti_Corso getDocenti_Corso(int Corso, int Docente) throws DataLayerException;
    
    Descrizione_it getDescrizione_it(Corso corso) throws DataLayerException;
    
    Descrizione_en getDescrizione_en(Corso corso) throws DataLayerException;
    
    Dublino_it getDublino_it(Corso corso) throws DataLayerException;
    
    Dublino_en getDublino_en(Corso corso) throws DataLayerException;
    
    Materiale getMateriale(int IDMateriale) throws DataLayerException;
    
    Libro getLibro(int IDLibro) throws DataLayerException;
    
    
    //Libri_Corso getLibri_Corso(int corso) throws DataLayerException;
    
    //Colleg_Corsi getColleg_Corsi(int This_Corso, int Other_Corso) throws DataLayerException;
    
    Gruppo getGruppo(int IDGruppo) throws DataLayerException;
    
    Utente getUtente(int IDUtente) throws DataLayerException;
    
    Servizio getServizio(int IDServizio) throws DataLayerException;
    
    //Group_Services getGroup_Services(int Gruppo, int Servizio) throws DataLayerException;
    
    Log getLog(int IDLog) throws DataLayerException;
    
    public List<Corso> getCorsiMutuati(Corso corso) throws DataLayerException;
    
    public Corso getCorsoMutua(Corso corso) throws DataLayerException;

    public List<Corso> getCorsiPrerequisiti(Corso corso) throws DataLayerException;

    public List<Corso> getCorsiModulo(Corso corso) throws DataLayerException;

    public List<Docente> getDocentiCorso(Corso corso) throws DataLayerException;
    
    public List<Libro> getLibriCorso(Corso corso) throws DataLayerException;
    
    public List<Materiale> getMaterialeCorso(int corso) throws DataLayerException;
    
    public List<Corso> getCorsiInCdl(CDL cdl) throws DataLayerException;
    
    public List<Utente> getUtentiInGruppo(Gruppo gruppo) throws DataLayerException;
    
    public List<Servizio> getServiziPerGruppo(Gruppo gruppo) throws DataLayerException;
    
    public List<Corso> getCorsiDelDocente(Docente docente) throws DataLayerException;
    
    public List<Corso> getCorsiDelLibro(Libro libro) throws DataLayerException;
        
    public List<Gruppo> getGruppiDelServizio(Servizio servizio) throws DataLayerException;

    public List<Docente> getDocente() throws DataLayerException;
    
    public List<Corso> getCorsiByAnno(int anno) throws DataLayerException;
    
    public List<CDL> getCDLNoMag() throws DataLayerException;
    
    public Utente getUtenti(String username, String password) throws DataLayerException;
    
    public String makeSessione(Sessione sessione) throws DataLayerException;
    
    public Sessione getSessione(String token) throws DataLayerException;
    
    public List<CDL> getCDLInCorso(Corso corso) throws DataLayerException;
    
    public List<Corso> getCorsiInCdlNoAnno(CDL cdl) throws DataLayerException;
    
    public boolean getAccessUtente(String username,String script) throws DataLayerException;
    
    //store inizio
    public void storeDocente(Docente docente) throws DataLayerException;

    public void storeCorso(Corso corso) throws DataLayerException;

    public void storeUtente(Utente utente) throws DataLayerException;

    public void storeCDL(CDL cdl);

    public boolean existUtente(String username);
    
    public List<Corso> getAnniPrecedenti(Corso corso) throws DataLayerException;

    public List<Log> getLog()throws DataLayerException;
    
    public void storeDescrizione_it(Descrizione_it descrizione) throws DataLayerException;
    
    public void storeDescrizione_en(Descrizione_en descrizione) throws DataLayerException;
    
    public void storeDublino_it(Dublino_it dublino) throws DataLayerException;
    
    public void storeDublino_en(Dublino_en dublino) throws DataLayerException;

    public void storeMateriale(Materiale materiale) throws DataLayerException;

    public void storeLibro(Libro libro, int corso) throws DataLayerException;
    

    public void deleteCorso(Corso corso) throws DataLayerException;

    public void storeLog(Log log) throws DataLayerException;
    
    public void deleteCDL(CDL cdl) throws DataLayerException;

    public void deleteDocente(Docente docente) throws DataLayerException;
    
    public Utente getUtenteByDocente(Docente docente) throws DataLayerException, SQLException;

    public void deleteUtente(Utente utente) throws DataLayerException;

    public void deleteMateriale(Materiale materiale) throws DataLayerException;

    public void deleteLibro(Libro libro) throws DataLayerException,SQLException;
    
    
    List<Integer> getAllUsers() throws DataLayerException;
    
    public boolean getAccessToken(String token,String servizio)throws DataLayerException;
    
    public boolean checkDocente(int doc) throws DataLayerException;
  
}
