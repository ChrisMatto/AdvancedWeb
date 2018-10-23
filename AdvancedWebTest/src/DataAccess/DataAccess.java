package DataAccess;

import Classi.*;
import Controller.Utils;
import javafx.util.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.core.MultivaluedMap;
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
                .select(CorsiCdl::getCdl)
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
        return stream.streamAll(em, Corso.class)
                .where(c -> c.getAnnoInizio() == year)
                .join((c,source) -> source.stream(CorsiCdl.class)
                        .where(corsiCdl -> corsiCdl.getCdl() == idCdl))
                .select(org.jinq.tuples.Pair::getOne).toList();
        /*List<Integer> idCorsi = stream.streamAll(em, CorsiCdl.class)
                .where(corsiCdl -> corsiCdl.getCdl() == idCdl)
                .select(corsiCdl -> corsiCdl.getCorso())
                .toList();
        List<Corso> corsi = new ArrayList();
        for(int id: idCorsi) {
            Optional<Corso> corso = stream.streamAll(em, Corso.class)
                    .where(c -> c.getIdCorso() == id && c.getAnnoInizio() == year && c.getAnnoFine() == year + 1)
                    .findFirst();
            corso.ifPresent(corsi::add);
        }
        return corsi;*/
    }

    public static List<Corso> getCorsiByFilter(int year, MultivaluedMap<String,String> queryParams) {
        List<Corso> corsi = new ArrayList();
        JinqStream<Corso> streamCorsi = stream.streamAll(em, Corso.class).where(c -> c.getAnnoInizio() == year);
        queryParams.forEach((String filter, List<String> values) -> {
            List<Corso> finalCorsi;
            switch(filter) {
                case "cdl":
                    int idCdl;
                    if(NumberUtils.isParsable(values.get(0))) {
                        idCdl = Integer.parseInt(values.get(0));
                    } else {
                        break;
                    }
                    //sbagliato: se prima metto un altro query param, questo mi cancella tutto.
                    finalCorsi = getCorsiByCdl(year, idCdl);
                    corsi.clear();
                    corsi.addAll(finalCorsi);
                case "id":
                    int idCorso;
                    if(NumberUtils.isParsable(values.get(0))) {
                        idCorso = Integer.parseInt(values.get(0));
                    } else {
                        break;
                    }

            }
        });
        /*switch (filter) {
            case "cdl":
                int idCdl;
                if(NumberUtils.isParsable(filterValue)) {
                    idCdl = Integer.parseInt(filterValue);
                } else {
                    return null;
                }
                return getCorsiByCdl(year, idCdl);
            case "id":
                int idCorso;
                if(NumberUtils.isParsable(filterValue)) {
                    idCorso = Integer.parseInt(filterValue);
                } else {
                    return null;
                }
                corsi.add(getCorso(year, idCorso));
                return corsi;
            case "nome":
                return getCorsiByNome(year, filterValue);
            case "ssd":
                return getCorsiBySsd(year, filterValue);
            case "semestre":
                int semestre;
                if(NumberUtils.isParsable(filterValue)) {
                    semestre = Integer.parseInt(filterValue);
                } else {
                    return null;
                }
                return getCorsiBySemestre(year, semestre);
        }
        return corsi;*/
    }

    public static List<Corso> getCorsiByNome(int year, String nome) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getAnnoInizio() == year && (corso.getNomeIt().equals(nome) || corso.getNomeEn().equals(nome)))
                .toList();
    }

    public static List<Corso> getCorsiBySsd(int year, String ssd) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getAnnoInizio() == year && corso.getSsd().equals(ssd))
                .toList();
    }

    public static List<Corso> getCorsiBySemestre(int year, int semestre) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getAnnoInizio() == year && corso.getSemestre() == semestre)
                .toList();
    }

    public static Corso getCorso(int year, int id) {
        Optional<Corso> optionalCorso = stream.streamAll(em, Corso.class)
                .where(corso -> corso.getAnnoInizio() == year && corso.getAnnoFine() == year + 1 && corso.getIdCorso() == id)
                .findFirst();
        return optionalCorso.orElse(null);
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
                .select(DocentiCorso::getDocente)
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
