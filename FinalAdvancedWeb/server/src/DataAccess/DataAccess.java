package DataAccess;

import Classi.*;
import ClassiTemp.HistoryCorso;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;
import org.jinq.tuples.Pair;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.*;

public class DataAccess {
    private static EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
    private static JinqJPAStreamProvider stream = new JinqJPAStreamProvider(em.getMetamodel());
    private static EntityTransaction entityTransaction = em.getTransaction();

    public static List<Integer> getCorsiByFilter(int year, MultivaluedMap<String,String> queryParams) {
        JinqStream<Corso> streamCorsi = stream.streamAll(em,Corso.class)
                .where(corso -> corso.getAnnoInizio() == year);
        for (String key : queryParams.keySet()) {
            String param = queryParams.get(key).get(0);
            switch (key) {
                case "cdl":
                    int idCdl;
                    if (NumberUtils.isParsable(param)) {
                        idCdl = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi
                            .join((c,source) -> source.stream(CorsiCdl.class))
                            .where(corsoCorsiCdlPair -> corsoCorsiCdlPair.getTwo().getCdl() == idCdl && corsoCorsiCdlPair.getOne().getIdCorso() == corsoCorsiCdlPair.getTwo().getCorso())
                            .select(org.jinq.tuples.Pair::getOne);
                    break;
                case "nome":
                    streamCorsi = streamCorsi.where(corso -> corso.getNomeIt().equals(param) || corso.getNomeEn().equals(param));
                    break;
                case "lingua":
                    streamCorsi = streamCorsi.where(corso -> corso.getLingua().equals(param));
                    break;
                case "ssd":
                    streamCorsi = streamCorsi.where(corso -> corso.getSsd().equals(param));
                    break;
                case "semestre":
                    int semestre;
                    if (NumberUtils.isParsable(param)) {
                        semestre = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi.where(corso -> corso.getSemestre() == semestre);
                    break;
                case "cfu":
                    int cfu;
                    if (NumberUtils.isParsable(param)) {
                        cfu = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi.where(corso -> corso.getCfu() == cfu);
                    break;
                case "tipologia":
                    streamCorsi = streamCorsi.where(corso -> corso.getTipologia().equals(param));
                    break;
            }
        }
        return streamCorsi
                .select(Corso::getIdCorso)
                .toList();
    }

    public static Corso getCorso(int id, int year) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getIdCorso() == id && corso.getAnnoInizio() == year)
                .findFirst()
                .orElse(null);
    }

    public static void insertCorso(Corso corso) {
        entityTransaction.begin();
        em.persist(corso);
        entityTransaction.commit();
    }

    public static List<HistoryCorso> getHistoryCorso(int id, String baseUri) {
        List<HistoryCorso> history = new ArrayList<>();
        Corso corso = stream.streamAll(em, Corso.class)
                .where(corso1 -> corso1.getIdCorso() == id)
                .findFirst()
                .orElse(null);
        if (corso == null) {
            return history;
        }
        history.add(new HistoryCorso(corso.getAnnoInizio(), baseUri + corso.getAnnoInizio() + "/" + corso.getIdCorso()));
        while (corso.getOldId() != null) {
            int oldId = corso.getOldId();
            corso = stream.streamAll(em, Corso.class)
                    .where(corso1 -> corso1.getIdCorso() == oldId)
                    .findFirst()
                    .orElse(null);
            if (corso != null) {
                history.add(new HistoryCorso(corso.getAnnoInizio(), baseUri + corso.getAnnoInizio() + "/" + corso.getIdCorso()));
            }
        }
        return history;
    }

    public static List<Docente> getDocentiInCorso(int idCorso) {
        return stream.streamAll(em, Docente.class)
                .join((docente, source) -> source.stream(DocentiCorso.class))
                .where(docenteDocentiCorsoPair -> docenteDocentiCorsoPair.getTwo().getCorso() == idCorso && docenteDocentiCorsoPair.getOne().getIdDocente() == docenteDocentiCorsoPair.getTwo().getDocente())
                .select(Pair::getOne).toList();
    }

    public static List<Cdl> getCdlInCorso(int idCorso) {
        return stream.streamAll(em, Cdl.class)
                .join((cdl, source) -> source.stream(CorsiCdl.class))
                .where(cdlCorsiCdlPair -> cdlCorsiCdlPair.getTwo().getCorso() == idCorso && cdlCorsiCdlPair.getOne().getIdcdl() == cdlCorsiCdlPair.getTwo().getCdl())
                .select(Pair::getOne).toList();
    }

    public static DescrizioneIt getDescrizioneIt(int idCorso) {
        return stream.streamAll(em, DescrizioneIt.class)
                .where(descrizioneIt -> descrizioneIt.getCorso() == idCorso)
                .findFirst()
                .orElse(null);
    }

    public static DescrizioneEn getDescrizioneEn(int idCorso) {
        return stream.streamAll(em, DescrizioneEn.class)
                .where(descrizioneEn -> descrizioneEn.getCorso() == idCorso)
                .findFirst()
                .orElse(null);
    }

    public static DublinoIt getDublinoIt(int idCorso) {
        return stream.streamAll(em, DublinoIt.class)
                .where(dublinoIt -> dublinoIt.getCorso() == idCorso)
                .findFirst()
                .orElse(null);
    }

    public static DublinoEn getDublinoEn(int idCorso) {
        return stream.streamAll(em, DublinoEn.class)
                .where(dublinoEn -> dublinoEn.getCorso() == idCorso)
                .findFirst()
                .orElse(null);
    }

    public static List<Libro> getLibriInCorso(int idCorso) {
        return stream.streamAll(em, LibriCorso.class)
                .where(libriCorso -> libriCorso.getCorso() == idCorso)
                .join((c, source) -> source.stream(Libro.class))
                .where(libriCorsoLibroPair -> libriCorsoLibroPair.getOne().getLibro() == libriCorsoLibroPair.getTwo().getIdLibro())
                .select(Pair::getTwo)
                .toList();
    }

    public static List<Integer> getUtenti() {
        return stream.streamAll(em, Utente.class)
                .select(Utente::getIdUtente)
                .toList();
    }

    public static Utente getUtente(String username, String password) {
        Optional<Utente> u = stream.streamAll(em, Utente.class)
                .where(utente -> utente.getUsername().equals(username) && utente.getPassword().equals(password))
                .findFirst();
        return u.orElse(null);
    }

    public static Utente getUtenteNoPassword(int id) {
        return stream.streamAll(em, Utente.class)
                .where(utente -> utente.getIdUtente() == id)
                .findFirst()
                .orElse(null);
    }

    public static void insertUtente(Utente utente) {
        entityTransaction.begin();
        em.persist(utente);
        entityTransaction.commit();
    }

    public static void updateUtente(Utente utente) {
        int idUtente = utente.getIdUtente();
        Utente u = stream.streamAll(em, Utente.class)
                .where(utente1 -> utente1.getIdUtente() == idUtente)
                .findFirst()
                .orElse(null);
        if (u != null) {
            if (utente.getUsername() != null) {
                if (!utente.getUsername().equals(u.getUsername()) && existUsernameUtente(utente.getUsername())) {
                    Response.ResponseBuilder responseBuilder = Response.status(409);
                    throw new WebApplicationException(responseBuilder.build());
                }
                u.setUsername(utente.getUsername());
            }
            if (utente.getPassword() != null) {
                u.setPassword(utente.getPassword());
            }
            if (utente.getGruppo() != null) {
                if (!existGruppo(utente.getGruppo())) {
                    Response.ResponseBuilder responseBuilder = Response.status(409);
                    throw new WebApplicationException(responseBuilder.build());
                }
                u.setGruppo(utente.getGruppo());
            }
            if (utente.getDocente() != null) {
                if (!utente.getDocente().equals(u.getDocente()) && existDocente(utente.getDocente()) && existDocente(utente.getDocente())) {
                    Response.ResponseBuilder responseBuilder = Response.status(409);
                    throw new WebApplicationException(responseBuilder.build());
                }
                u.setDocente(utente.getDocente());
            }
            entityTransaction.begin();
            em.persist(u);
            entityTransaction.commit();
        } else {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            throw new WebApplicationException(responseBuilder.build());
        }
    }

    public static void deleteUtente(int id) {
        Utente utente = stream.streamAll(em, Utente.class)
                .where(u -> u.getIdUtente() == id)
                .findFirst()
                .orElse(null);
        entityTransaction.begin();
        em.remove(utente);
        entityTransaction.commit();
    }

    public static Boolean existDocente(int id) {
        return stream.streamAll(em, Docente.class)
                .where(docente -> docente.getIdDocente() == id)
                .findFirst()
                .isPresent();
    }

    public static Boolean existUsernameUtente(String username) {
        return stream.streamAll(em, Utente.class)
                .where(u -> u.getUsername().equals(username))
                .findFirst().isPresent();
    }

    public static Boolean existDocenteInUtente(int idDocente) {
        return stream.streamAll(em, Utente.class)
                .where(u -> u.getDocente() == idDocente)
                .findFirst()
                .isPresent();
    }

    public static Boolean existGruppo(int id) {
        return stream.streamAll(em, Gruppo.class)
                .where(gruppo -> gruppo.getIdGruppo() == id)
                .findFirst()
                .isPresent();
    }

    public static Boolean existSessioneUtente(int idUtente) {
        return stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getUtente() == idUtente)
                .findFirst()
                .isPresent();
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
            em.remove(sessione.get());
            entityTransaction.commit();
        }
    }

    public static Boolean checkAccessToken(String token, String service, String method) {
        Optional<Sessione> optionalSessione = stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getToken().equals(token))
                .findFirst();
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service) && s.getMetodo().equals(method))
                .findFirst();
        if(!optionalSessione.isPresent()) {
            return false;
        } else {
            if(!optionalServizio.isPresent()) {
                return true;
            }
        }

        Optional<Servizio> servizio = stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getToken().equals(token))
                .join((s, source) -> source.stream(Utente.class))
                .where(sessioneUtentePair -> sessioneUtentePair.getOne().getUtente() == sessioneUtentePair.getTwo().getIdUtente())
                .select(Pair::getTwo)
                .join((s, source) -> source.stream(GroupServices.class))
                .where(utenteGroupServicesPair -> utenteGroupServicesPair.getOne().getGruppo() == utenteGroupServicesPair.getTwo().getGruppo())
                .select(Pair::getTwo)
                .join((s, source) -> source.stream(Servizio.class))
                .where(groupServicesServizioPair -> groupServicesServizioPair.getOne().getServizio() == groupServicesServizioPair.getTwo().getIdServizio() &&
                        groupServicesServizioPair.getTwo().getNome().equals(service) && groupServicesServizioPair.getTwo().getMetodo().equals(method))
                .select(Pair::getTwo)
                .findFirst();
        return servizio.isPresent();
    }

    public static Boolean checkAccessNoToken(String service, String method) {
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service) && s.getMetodo().equals(method))
                .findFirst();
        return !optionalServizio.isPresent();
    }
}