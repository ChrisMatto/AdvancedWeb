package DataAccess;

import Classi.*;
import Controller.Utils;
import javafx.util.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataAccess {
    private static EntityManager  em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
    private static JinqJPAStreamProvider stream = new JinqJPAStreamProvider(em.getMetamodel());
    private static EntityTransaction entityTransaction = em.getTransaction();

    public static List<Cdl> getCDL() {
        int year = Utils.getCurrentYear();
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == year && cdl.getAnnoFine() == year + 1 && cdl.getMagistrale() == 0)
                .toList();
    }

    public static List<Cdl> getCDLM() {
        int year = Utils.getCurrentYear();
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == year && cdl.getAnnoFine() == year + 1 && cdl.getMagistrale() == 1)
                .toList();
    }

    public static List<Cdl> getCdlInCorso(int idCorso) {
        List<Integer> idCdl = stream.streamAll(em, CorsiCdl.class)
                .where(corsiCdl -> corsiCdl.getCorso() == idCorso)
                .select(CorsiCdl::getCorso)
                .toList();
        List<Cdl> cdl = new ArrayList();
        for(int id: idCdl) {
            Optional<Cdl> optionalCdl = stream.streamAll(em, Cdl.class)
                    .where(cd -> cd.getIdcdl() == id)
                    .findFirst();
            optionalCdl.ifPresent(cdl::add);
        }
        return cdl;
    }

    public static List<Corso> getCorsi(int year) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getAnnoInizio() == year && corso.getAnnoFine() == year + 1)
                .toList();
    }

    public static List<Corso> getCorsiByCdl(int year, int idCdl) {
        
    }

    public static List<Corso> getCorsi() {
        return stream.streamAll(em, Corso.class).toList();
    }

    public static List<Docente> getDocenti() {
        return stream.streamAll(em, Docente.class).toList();
    }

    public static List<Docente> getDocentiInCorso(int idCorso) {
        List<Integer> idDocenti = stream.streamAll(em, DocentiCorso.class)
                .where(docentiCorso -> docentiCorso.getCorso() == idCorso)
                .select(DocentiCorso::getCorso)
                .toList();
        List<Docente> docenti = new ArrayList();
        for(int id: idDocenti) {
            Optional<Docente> docente = stream.streamAll(em, Docente.class)
                    .where(doc -> doc.getIdDocente() == id)
                    .findFirst();
            docente.ifPresent(docenti::add);
        }
        return docenti;
    }

    public static Utente getUtente(String username, String password) {
        Optional<Utente> u = stream.streamAll(em, Utente.class)
                .where(utente -> utente.getUsername().equals(username) && utente.getPassword().equals(password))
                .findFirst();
        return u.orElse(null);
    }

    public static String makeSessione(Utente utente) {
        Sessione sessione = new Sessione();
        sessione.setUtente(utente.getIdUtente());
        sessione.setData(new Timestamp(System.currentTimeMillis()));
        sessione.setToken(RandomStringUtils.randomAlphanumeric(32));
        String token = sessione.getToken();
        entityTransaction.begin();
        em.persist(sessione);
        entityTransaction.commit();
        Optional<Sessione> optionalSessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token)).findFirst();
        return optionalSessione.map(Sessione::getToken).orElse(null);
    }

    public static void deleteSession(String token) {
        Optional<Sessione> sessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token))
                .findFirst();
        if(sessione.isPresent()) {
            entityTransaction.begin();
            em.remove(sessione);
            entityTransaction.commit();
        }
    }

    public static Boolean checkAccessToken(String token, String service) {
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service))
                .findFirst();
        if(!optionalServizio.isPresent()) {
            return true;
        }
        Optional<Sessione> optionalSessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token))
                .findFirst();
        if(!optionalSessione.isPresent())
            return false;
        int idUtente = optionalSessione.get().getUtente();
        Optional<Utente> optionalUtente = stream.streamAll(em, Utente.class)
                .where(u -> u.getIdUtente() == idUtente)
                .findFirst();
        if(!optionalUtente.isPresent())
            return false;
        int idServizio = optionalServizio.get().getIdServizio();
        int idGruppo = optionalUtente.get().getGruppo();
        Optional<GroupServices> optionalGroupServices = stream.streamAll(em, GroupServices.class)
                .where(gs -> gs.getGruppo() == idGruppo && gs.getServizio() == idServizio)
                .findFirst();
        return optionalGroupServices.isPresent();
    }

    public static Boolean checkAccessNoToken(String service) {
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service))
                .findFirst();
        return !optionalServizio.isPresent();
    }
}
