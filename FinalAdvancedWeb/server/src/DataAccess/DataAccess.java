package DataAccess;

import Classi.*;
import ClassiTemp.CorsoCompleto;
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
                .where(corso -> corso.getAnno() == year);
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
                .where(corso -> corso.getIdCorso() == id && corso.getAnno() == year)
                .findFirst()
                .orElse(null);
    }

    public static void insertCorso(CorsoCompleto corsoCompleto) {
        int newId = stream.streamAll(em,Corso.class)
                .max(Corso::getIdCorso) +1;
        corsoCompleto.setIdCorso(newId);
        Corso corso = new Corso();
        corso.copyFrom(corsoCompleto);
        entityTransaction.begin();
        em.persist(corso);
        entityTransaction.commit();
        if (corsoCompleto.getDescrizioneIt() != null) {
            corsoCompleto.getDescrizioneIt().setCorso(corsoCompleto.getIdCorso());
            corsoCompleto.getDescrizioneIt().setAnnoCorso(corsoCompleto.getAnno());
            insertDescrizioneIt(corsoCompleto.getDescrizioneIt());
        }
        if (corsoCompleto.getDescrizioneEn() != null) {
            corsoCompleto.getDescrizioneEn().setCorso(corsoCompleto.getIdCorso());
            corsoCompleto.getDescrizioneEn().setAnnoCorso(corsoCompleto.getAnno());
            insertDescrizioneEn(corsoCompleto.getDescrizioneEn());
        }
        if (corsoCompleto.getDublinoIt() != null) {
            corsoCompleto.getDublinoIt().setCorso(corsoCompleto.getIdCorso());
            corsoCompleto.getDublinoIt().setAnnoCorso(corsoCompleto.getAnno());
            insertDublinoIt(corsoCompleto.getDublinoIt());
        }
        if (corsoCompleto.getDublinoEn() != null) {
            corsoCompleto.getDublinoEn().setCorso(corsoCompleto.getIdCorso());
            corsoCompleto.getDublinoEn().setAnnoCorso(corsoCompleto.getAnno());
            insertDublinoEn(corsoCompleto.getDublinoEn());
        }
        if (corsoCompleto.getIdDocenti() != null && !corsoCompleto.getIdDocenti().isEmpty()) {
            insertDocentiCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getIdDocenti());
        }
        if (corsoCompleto.getIdCdl() != null && !corsoCompleto.getIdCdl().isEmpty()) {
            insertCdlCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getIdCdl());
        }
        if (corsoCompleto.getIdLibri() != null && !corsoCompleto.getIdLibri().isEmpty()) {
            insertLibriCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getIdLibri());
        }
        if (corsoCompleto.getIdMateriale() != null && !corsoCompleto.getIdMateriale().isEmpty()) {
            insertMaterialeCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getIdMateriale());
        }
    }

    private static void insertDescrizioneIt(DescrizioneIt descrizioneIt) {
        entityTransaction.begin();
        em.persist(descrizioneIt);
        entityTransaction.commit();
    }

    private static void insertDescrizioneEn(DescrizioneEn descrizioneEn) {
        entityTransaction.begin();
        em.persist(descrizioneEn);
        entityTransaction.commit();
    }

    private static void insertDublinoIt(DublinoIt dublinoIt) {
        entityTransaction.begin();
        em.persist(dublinoIt);
        entityTransaction.commit();
    }

    private static void insertDublinoEn(DublinoEn dublinoEn) {
        entityTransaction.begin();
        em.persist(dublinoEn);
        entityTransaction.commit();
    }

    private static void insertDocentiCorso(int idCorso, int annoCorso, List<Integer> docenti) {
        for (int idDocente : docenti) {
            DocentiCorso docentiCorso = new DocentiCorso();
            docentiCorso.setCorso(idCorso);
            docentiCorso.setAnnoCorso(annoCorso);
            docentiCorso.setDocente(idDocente);
            entityTransaction.begin();
            em.persist(docentiCorso);
            entityTransaction.commit();
        }
    }

    private static void insertCdlCorso(int idCorso, int annoCorso, List<Integer> cdl) {
        for (int idCdl : cdl) {
            CorsiCdl corsiCdl = new CorsiCdl();
            corsiCdl.setCorso(idCorso);
            corsiCdl.setAnnoCorso(annoCorso);
            corsiCdl.setCdl(idCdl);
            entityTransaction.begin();
            em.persist(corsiCdl);
            entityTransaction.commit();
        }
    }

    private static void insertLibriCorso(int idCorso, int annoCorso, List<Integer> libri) {
        for (int idLibro : libri) {
            LibriCorso libriCorso = new LibriCorso();
            libriCorso.setCorso(idCorso);
            libriCorso.setAnnoCorso(annoCorso);
            libriCorso.setLibro(idLibro);
            entityTransaction.begin();
            em.persist(libriCorso);
            entityTransaction.commit();
        }
    }

    private static void insertMaterialeCorso(int idCorso, int annoCorso, List<Integer> materiale) {
        for (int idMateriale : materiale) {
            Materiale mat = stream.streamAll(em,Materiale.class)
                    .where(m -> m.getIdMateriale() == idMateriale)
                    .findFirst()
                    .orElse(null);
            if (mat != null) {
                mat.setCorso(idCorso);
                mat.setAnnoCorso(annoCorso);
                entityTransaction.begin();
                em.persist(mat);
                entityTransaction.commit();
            }
        }
    }

    public static void updateCorso(int idCorso, int year, CorsoCompleto corsoCompleto) {
        Corso corso = stream.streamAll(em, Corso.class)
                .where(c -> c.getIdCorso() == idCorso && c.getAnno() == year)
                .findFirst()
                .orElse(null);
        if (corso == null) {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            throw new WebApplicationException(responseBuilder.build());
        }
        if (corsoCompleto.getNomeIt() != null) {
            corso.setNomeIt(corsoCompleto.getNomeIt());
        }
        if (corsoCompleto.getNomeEn() != null) {
            corso.setNomeEn(corsoCompleto.getNomeEn());
        }
        if (corsoCompleto.getSsd() != null) {
            corso.setSsd(corsoCompleto.getSsd());
        }
        if (corsoCompleto.getLingua() != null) {
            corso.setLingua(corsoCompleto.getLingua());
        }
        if (corsoCompleto.getSemestre() != null) {
            corso.setSemestre(corsoCompleto.getSemestre());
        }
        if (corsoCompleto.getCfu() != null) {
            corso.setCfu(corsoCompleto.getCfu());
        }
        if (corsoCompleto.getTipologia() != null) {
            corso.setTipologia(corsoCompleto.getTipologia());
        }
        entityTransaction.begin();
        em.persist(corso);
        entityTransaction.commit();

        if (corsoCompleto.getDescrizioneIt() != null) {
            updateDescrizione(idCorso, year, corsoCompleto.getDescrizioneIt());
        }
        if (corsoCompleto.getDescrizioneEn() != null) {
            updateDescrizione(idCorso, year, corsoCompleto.getDescrizioneEn());
        }
        if (corsoCompleto.getDublinoIt() != null) {
            updateDublino(idCorso, year, corsoCompleto.getDublinoIt());
        }
        if (corsoCompleto.getDublinoEn() != null) {
            updateDublino(idCorso, year, corsoCompleto.getDublinoEn());
        }
        if (corsoCompleto.getIdDocenti() != null) {
            //da completare e vedere get e post di docenti
        }
    }

    private static void updateDescrizione(int idCorso, int year, Descrizione descrizione) {
        Descrizione oldDescrizione;
        if (descrizione instanceof DescrizioneIt) {
            oldDescrizione = stream.streamAll(em, DescrizioneIt.class)
                    .where(descr -> descr.getCorso() == idCorso && descr.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        } else {
            oldDescrizione = stream.streamAll(em, DescrizioneEn.class)
                    .where(descr -> descr.getCorso() == idCorso && descr.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        }

        if (oldDescrizione != null) {
            if (descrizione.getPrerequisiti() != null) {
                oldDescrizione.setPrerequisiti(descrizione.getPrerequisiti());
            }
            if (descrizione.getObiettivi() != null) {
                oldDescrizione.setObiettivi(descrizione.getObiettivi());
            }
            if (descrizione.getModEsame() != null) {
                oldDescrizione.setModEsame(descrizione.getModEsame());
            }
            if (descrizione.getModInsegnamento() != null) {
                oldDescrizione.setModInsegnamento(descrizione.getModInsegnamento());
            }
            if (descrizione.getSillabo() != null) {
                oldDescrizione.setSillabo(descrizione.getSillabo());
            }
            if (descrizione.getNote() != null) {
                oldDescrizione.setNote(descrizione.getNote());
            }
            if (descrizione.getHomepage() != null) {
                oldDescrizione.setHomepage(descrizione.getHomepage());
            }
            if (descrizione.getForum() != null) {
                oldDescrizione.setForum(descrizione.getForum());
            }
            if (descrizione.getRisorseExt() != null) {
                oldDescrizione.setRisorseExt(descrizione.getRisorseExt());
            }
            entityTransaction.begin();
            em.persist(descrizione);
            entityTransaction.commit();
        } else {
            descrizione.setCorso(idCorso);
            descrizione.setAnnoCorso(year);
            entityTransaction.begin();
            em.persist(descrizione);
            entityTransaction.commit();
        }
    }

    private static void updateDublino(int idCorso, int year, Dublino dublino) {
        Dublino oldDublino;
        if (dublino instanceof DublinoIt) {
            oldDublino = stream.streamAll(em, DublinoIt.class)
                    .where(dublinoIt -> dublinoIt.getCorso() == idCorso && dublinoIt.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        } else {
            oldDublino = stream.streamAll(em, DublinoEn.class)
                    .where(dublinoIt -> dublinoIt.getCorso() == idCorso && dublinoIt.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        }
        if (oldDublino != null) {
            if (dublino.getKnowledge() != null) {
                oldDublino.setKnowledge(dublino.getKnowledge());
            }
            if (dublino.getApplication() != null) {
                oldDublino.setApplication(dublino.getApplication());
            }
            if (dublino.getEvaluation() != null) {
                oldDublino.setEvaluation(dublino.getEvaluation());
            }
            if (dublino.getCommunication() != null) {
                oldDublino.setCommunication(dublino.getCommunication());
            }
            if (dublino.getLifelong() != null) {
                oldDublino.setLifelong(dublino.getLifelong());
            }
            entityTransaction.begin();
            em.persist(oldDublino);
            entityTransaction.commit();
        } else {
            dublino.setCorso(idCorso);
            dublino.setAnnoCorso(year);
            entityTransaction.begin();
            em.persist(dublino);
            entityTransaction.commit();
        }
    }

    public static List<HistoryCorso> getHistoryCorso(int id, String baseUri) {
        List<HistoryCorso> history = new ArrayList<>();
        List<Corso> corsi = stream.streamAll(em, Corso.class)
                .where(corso -> corso.getIdCorso() == id)
                .toList();
        for (Corso corso : corsi) {
            history.add(new HistoryCorso(corso.getAnno(), baseUri + corso.getAnno() + "/" + corso.getIdCorso()));
        }
        return history;
    }

    public static List<Docente> getDocentiInCorso(int idCorso, int year) {
        return stream.streamAll(em, Docente.class)
                .join((docente, source) -> source.stream(DocentiCorso.class))
                .where(docenteDocentiCorsoPair -> docenteDocentiCorsoPair.getTwo().getCorso() == idCorso && docenteDocentiCorsoPair.getTwo().getAnnoCorso() == year && docenteDocentiCorsoPair.getOne().getIdDocente() == docenteDocentiCorsoPair.getTwo().getDocente())
                .select(Pair::getOne).toList();
    }

    public static List<Cdl> getCdlInCorso(int idCorso, int year) {
        return stream.streamAll(em, Cdl.class)
                .join((cdl, source) -> source.stream(CorsiCdl.class))
                .where(cdlCorsiCdlPair -> cdlCorsiCdlPair.getTwo().getCorso() == idCorso && cdlCorsiCdlPair.getTwo().getAnnoCorso() == year && cdlCorsiCdlPair.getOne().getIdcdl() == cdlCorsiCdlPair.getTwo().getCdl())
                .select(Pair::getOne).toList();
    }

    public static DescrizioneIt getDescrizioneIt(int idCorso, int year) {
        return stream.streamAll(em, DescrizioneIt.class)
                .where(descrizioneIt -> descrizioneIt.getCorso() == idCorso && descrizioneIt.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public static DescrizioneEn getDescrizioneEn(int idCorso, int year) {
        return stream.streamAll(em, DescrizioneEn.class)
                .where(descrizioneEn -> descrizioneEn.getCorso() == idCorso && descrizioneEn.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public static DublinoIt getDublinoIt(int idCorso, int year) {
        return stream.streamAll(em, DublinoIt.class)
                .where(dublinoIt -> dublinoIt.getCorso() == idCorso && dublinoIt.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public static DublinoEn getDublinoEn(int idCorso, int year) {
        return stream.streamAll(em, DublinoEn.class)
                .where(dublinoEn -> dublinoEn.getCorso() == idCorso && dublinoEn.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public static List<Libro> getLibriInCorso(int idCorso, int year) {
        return stream.streamAll(em, LibriCorso.class)
                .where(libriCorso -> libriCorso.getCorso() == idCorso && libriCorso.getAnnoCorso() == year)
                .join((c, source) -> source.stream(Libro.class))
                .where(libriCorsoLibroPair -> libriCorsoLibroPair.getOne().getLibro() == libriCorsoLibroPair.getTwo().getIdLibro())
                .select(Pair::getTwo)
                .toList();
    }

    public static List<Materiale> getMaterialeCorso(int idCorso, int year) {
        return stream.streamAll(em, Materiale.class)
                .where(materiale -> materiale.getCorso() == idCorso && materiale.getAnnoCorso() == year)
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

    public static void updateUtente(int idUtente, Utente utente) {
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
            Response.ResponseBuilder responseBuilder = Response.status(400);
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