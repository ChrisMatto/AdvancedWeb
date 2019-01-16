package DataAccess;

import Classi.*;
import ClassiTemp.*;
import Controller.Utils;
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

    public static List<Integer> getAnniCorsi() {
        return stream.streamAll(em, Corso.class)
                .select(Corso::getAnno)
                .distinct()
                .toList();
    }

    public static Corso getCorso(int id, int year) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getIdCorso() == id && corso.getAnno() == year)
                .findFirst()
                .orElse(null);
    }

    public static List<String> getDocenti(String baseUri) {
        List<Integer> docenti = stream.streamAll(em, Docente.class)
                .select(Docente::getIdDocente)
                .toList();
        List<String> docentiUri = new ArrayList<>();
        for (int id : docenti) {
            docentiUri.add(baseUri + id);
        }
        return docentiUri;
    }

    public static Docente getDocente(int id) {
        return stream.streamAll(em, Docente.class)
                .where(docente -> docente.getIdDocente() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Corso> getCorsiDocente(int idDocente) {
        int year = Utils.getCurrentYear();
        return stream.streamAll(em, Corso.class)
                .join((corso, source) -> source.stream(DocentiCorso.class))
                .where(corsoDocentiCorsoPair -> corsoDocentiCorsoPair.getTwo().getDocente() == idDocente &&
                        corsoDocentiCorsoPair.getTwo().getCorso() == corsoDocentiCorsoPair.getOne().getIdCorso() &&
                        corsoDocentiCorsoPair.getTwo().getAnnoCorso() == corsoDocentiCorsoPair.getOne().getAnno() &&
                        corsoDocentiCorsoPair.getTwo().getAnnoCorso() == year)
                .select(Pair::getOne)
                .toList();
    }

    public static MaterialeCompleto getMateriale(int idMateriale) {
        return new MaterialeCompleto(stream.streamAll(em, Materiale.class)
                .where(materiale -> materiale.getIdMateriale() == idMateriale)
                .findFirst()
                .orElse(null));
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
        if (corsoCompleto.getLinks() != null) {
            corsoCompleto.getLinks().setCorso(corsoCompleto.getIdCorso());
            corsoCompleto.getLinks().setAnnoCorso(corsoCompleto.getAnno());
            insertLinks(corsoCompleto.getLinks());
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
        if (corsoCompleto.getDocenti() != null && !corsoCompleto.getDocenti().isEmpty()) {
            insertDocentiCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getDocenti());
        }
        if (corsoCompleto.getCdl() != null && !corsoCompleto.getCdl().isEmpty()) {
            insertCdlCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getCdl());
        }
        if (corsoCompleto.getLibri() != null && !corsoCompleto.getLibri().isEmpty()) {
            insertLibriCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getLibri());
        }
        if (corsoCompleto.getMateriale() != null && !corsoCompleto.getMateriale().isEmpty()) {
            insertMaterialeCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getMateriale());
        }
        if (corsoCompleto.getRelazioni() != null) {
            insertRelazioniCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getRelazioni().getPropedeudici(), "propedeutico");
            insertRelazioniCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getRelazioni().getMutuati(), "mutuato");
            insertRelazioniCorso(corsoCompleto.getIdCorso(), corsoCompleto.getAnno(), corsoCompleto.getRelazioni().getModulo(), "modulo");
        }
        updateVersione("corso");
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

    private static void insertLinks(Links links) {
        entityTransaction.begin();
        em.persist(links);
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

    private static void insertDocentiCorso(int idCorso, int annoCorso, List<DocentePerCorso> docenti) {
        for (DocentePerCorso docente : docenti) {
            DocentiCorso docentiCorso = new DocentiCorso();
            docentiCorso.setCorso(idCorso);
            docentiCorso.setAnnoCorso(annoCorso);
            docentiCorso.setDocente(docente.getIdDocente());
            entityTransaction.begin();
            em.persist(docentiCorso);
            entityTransaction.commit();
        }
    }

    private static void insertCdlCorso(int idCorso, int annoCorso, List<CdlPerCorso> cdl) {
        for (CdlPerCorso c : cdl) {
            CorsiCdl corsiCdl = new CorsiCdl();
            corsiCdl.setCorso(idCorso);
            corsiCdl.setAnnoCorso(annoCorso);
            corsiCdl.setCdl(c.getIdCdl());
            entityTransaction.begin();
            em.persist(corsiCdl);
            entityTransaction.commit();
        }
    }

    private static void insertLibriCorso(int idCorso, int annoCorso, List<Libro> libri) {
        for (Libro libro : libri) {
            LibriCorso libriCorso = new LibriCorso();
            libriCorso.setCorso(idCorso);
            libriCorso.setAnnoCorso(annoCorso);
            libriCorso.setLibro(libro.getIdLibro());
            entityTransaction.begin();
            em.persist(libriCorso);
            entityTransaction.commit();
        }
    }

    private static void insertMaterialeCorso(int idCorso, int annoCorso, List<MaterialeCompleto> materiale) {
        for (Materiale mat : materiale) {
            MaterialeCorso materialeCorso = new MaterialeCorso();
            materialeCorso.setCorso(idCorso);
            materialeCorso.setAnnoCorso(annoCorso);
            materialeCorso.setMateriale(mat.getIdMateriale());
            entityTransaction.begin();
            em.persist(materialeCorso);
            entityTransaction.commit();
        }
    }

    private static void insertRelazioniCorso(int idCorso, int annoCorso, List<String> uriCorsi, String tipo) {
        if (uriCorsi != null && !uriCorsi.isEmpty()) {
            for (String uri : uriCorsi) {
                CollegCorsi collegCorsi = new CollegCorsi();
                collegCorsi.setThisCorso(idCorso);
                collegCorsi.setAnnoThisCorso(annoCorso);
                collegCorsi.setTipo(tipo);
                int startIndex = uri.indexOf("courses/") + 8;
                int endIndexYear = uri.indexOf("/", startIndex);
                String year = uri.substring(startIndex, endIndexYear);
                int anno = Utils.getYear(year);
                collegCorsi.setAnnoOtherCorso(anno);
                String idString = uri.substring(endIndexYear + 1);
                int id;
                if (NumberUtils.isParsable(idString)) {
                    id = Integer.parseInt(idString);
                    collegCorsi.setOtherCorso(id);
                } else {
                    continue;
                }
                entityTransaction.begin();
                em.persist(collegCorsi);
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
        corso.setNomeIt(corsoCompleto.getNomeIt());
        corso.setNomeEn(corsoCompleto.getNomeEn());
        corso.setSsd(corsoCompleto.getSsd());
        corso.setLingua(corsoCompleto.getLingua());
        corso.setSemestre(corsoCompleto.getSemestre());
        corso.setCfu(corsoCompleto.getCfu());
        corso.setTipologia(corsoCompleto.getTipologia());
        entityTransaction.begin();
        em.persist(corso);
        entityTransaction.commit();

        if (corsoCompleto.getDescrizioneIt() != null) {
            updateDescrizione(idCorso, year, corsoCompleto.getDescrizioneIt());
        } else {
            deleteDescrizione(idCorso, year, DescrizioneIt.class);
        }
        if (corsoCompleto.getDescrizioneEn() != null) {
            updateDescrizione(idCorso, year, corsoCompleto.getDescrizioneEn());
        } else {
            deleteDescrizione(idCorso, year, DescrizioneEn.class);
        }
        if (corsoCompleto.getLinks() != null) {
            updateLinks(idCorso, year, corsoCompleto.getLinks());
        } else {
            deleteLinks(idCorso, year);
        }
        if (corsoCompleto.getDublinoIt() != null) {
            updateDublino(idCorso, year, corsoCompleto.getDublinoIt());
        } else {
            deleteDublino(idCorso, year, DublinoIt.class);
        }
        if (corsoCompleto.getDublinoEn() != null) {
            updateDublino(idCorso, year, corsoCompleto.getDublinoEn());
        } else {
            deleteDublino(idCorso, year, DublinoEn.class);
        }
        if (corsoCompleto.getDocenti() != null && !corsoCompleto.getDocenti().isEmpty()) {
            updateDocentiCorso(idCorso, year, corsoCompleto.getDocenti());
        } else {
            deleteDocentiCorso(idCorso, year);
        }
        if (corsoCompleto.getCdl() != null && !corsoCompleto.getCdl().isEmpty()) {
            updateCdlCorso(idCorso, year, corsoCompleto.getCdl());
        } else {
            deleteCdlCorso(idCorso, year);
        }
        if (corsoCompleto.getLibri() != null && !corsoCompleto.getLibri().isEmpty()) {
            updateLibriCorso(idCorso, year, corsoCompleto.getLibri());
        } else {
            deleteLibriCorso(idCorso, year);
        }
        if (corsoCompleto.getMateriale() != null && !corsoCompleto.getMateriale().isEmpty()) {
            updateMaterialeCorso(idCorso, year, corsoCompleto.getMateriale());
        } else {
            deleteMaterialeCorso(idCorso, year);
        }
        if (corsoCompleto.getRelazioni() != null) {
            updateRelazioniCorso(idCorso, year, corsoCompleto.getRelazioni().getPropedeudici(), "propedeutico");
            updateRelazioniCorso(idCorso, year, corsoCompleto.getRelazioni().getMutuati(), "mutuato");
            updateRelazioniCorso(idCorso, year, corsoCompleto.getRelazioni().getModulo(), "modulo");
        } else {
            deleteRelazioniCorso(idCorso, year, "propedeutico");
            deleteRelazioniCorso(idCorso, year, "mutuato");
            deleteRelazioniCorso(idCorso, year, "modulo");
        }
        updateVersione("corso");
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
            entityTransaction.begin();
            em.persist(oldDescrizione);
            entityTransaction.commit();
        } else {
            descrizione.setCorso(idCorso);
            descrizione.setAnnoCorso(year);
            entityTransaction.begin();
            em.persist(descrizione);
            entityTransaction.commit();
        }
    }

    public static void updateLinks(int idCorso, int year, Links links) {
        Links oldLinks = stream.streamAll(em, Links.class)
                .where(l -> l.getCorso() == idCorso && l.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
        if (oldLinks != null) {
            if (links.getHomepage() != null) {
                oldLinks.setHomepage(links.getHomepage());
            }
            if (links.getElearning() != null) {
                oldLinks.setElearning(links.getElearning());
            }
            if (links.getForum() != null) {
                oldLinks.setForum(links.getForum());
            }
            if (links.getRisorseExt() != null) {
                oldLinks.setElearning(links.getElearning());
            }
            entityTransaction.begin();
            em.persist(oldLinks);
            entityTransaction.commit();
        } else {
            links.setCorso(idCorso);
            links.setAnnoCorso(year);
            entityTransaction.begin();
            em.persist(links);
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

    public static void updateDocentiCorso(int idCorso, int year, List<DocentePerCorso> docenti) {
        List<DocentiCorso> oldDocentiCorso = stream.streamAll(em, DocentiCorso.class)
                .where(docentiCorso -> docentiCorso.getCorso() == idCorso && docentiCorso.getAnnoCorso() == year)
                .toList();
        for (DocentePerCorso docente : docenti) {
            DocentiCorso docentiCorso = new DocentiCorso();
            docentiCorso.setCorso(idCorso);
            docentiCorso.setAnnoCorso(year);
            docentiCorso.setDocente(docente.getIdDocente());
            if (oldDocentiCorso.isEmpty()) {
                entityTransaction.begin();
                em.persist(docentiCorso);
                entityTransaction.commit();
            } else {
                if (oldDocentiCorso.contains(docentiCorso)) {
                    oldDocentiCorso.remove(docentiCorso);
                } else {
                    entityTransaction.begin();
                    em.persist(docentiCorso);
                    entityTransaction.commit();
                }
            }
        }
        if (!oldDocentiCorso.isEmpty()) {
            for (DocentiCorso docentiCorso : oldDocentiCorso) {
                entityTransaction.begin();
                em.remove(docentiCorso);
                entityTransaction.commit();
            }
        }
    }

    private static void updateCdlCorso(int idCorso, int year, List<CdlPerCorso> cdlPerCorso) {
        List<CorsiCdl> oldCorsiCdl = stream.streamAll(em, CorsiCdl.class)
                .where(corsiCdl -> corsiCdl.getCorso() == idCorso && corsiCdl.getAnnoCorso() == year)
                .toList();
        for (CdlPerCorso cdl : cdlPerCorso) {
            CorsiCdl corsiCdl = new CorsiCdl();
            corsiCdl.setCorso(idCorso);
            corsiCdl.setAnnoCorso(year);
            corsiCdl.setCdl(cdl.getIdCdl());
            if (oldCorsiCdl.isEmpty()) {
                entityTransaction.begin();
                em.persist(corsiCdl);
                entityTransaction.commit();
            } else if (oldCorsiCdl.contains(corsiCdl)) {
                oldCorsiCdl.remove(corsiCdl);
            } else {
                entityTransaction.begin();
                em.persist(corsiCdl);
                entityTransaction.commit();
            }
        }
        if (!oldCorsiCdl.isEmpty()) {
            for (CorsiCdl corsiCdl : oldCorsiCdl) {
                entityTransaction.begin();
                em.remove(corsiCdl);
                entityTransaction.commit();
            }
        }
    }

    private static void updateLibriCorso(int idCorso, int year, List<Libro> libri) {
        List<LibriCorso> oldLibriCorso = stream.streamAll(em, LibriCorso.class)
                .where(libriCorso -> libriCorso.getCorso() == idCorso && libriCorso.getAnnoCorso() == year)
                .toList();
        for (Libro libro : libri) {
            LibriCorso libriCorso = new LibriCorso();
            libriCorso.setCorso(idCorso);
            libriCorso.setAnnoCorso(year);
            libriCorso.setLibro(libro.getIdLibro());
            if (oldLibriCorso.isEmpty()) {
                entityTransaction.begin();
                em.persist(libriCorso);
                entityTransaction.commit();
            } else if (oldLibriCorso.contains(libriCorso)) {
                oldLibriCorso.remove(libriCorso);
            } else {
                entityTransaction.begin();
                em.persist(libriCorso);
                entityTransaction.commit();
            }
        }
        if (!oldLibriCorso.isEmpty()) {
            for (LibriCorso libriCorso : oldLibriCorso) {
                entityTransaction.begin();
                em.remove(libriCorso);
                entityTransaction.commit();
            }
        }
    }

    private static void updateMaterialeCorso(int idCorso, int year, List<MaterialeCompleto> materialeCorso) {
        List<MaterialeCorso> oldMaterialeCorso = stream.streamAll(em, MaterialeCorso.class)
                .where(mc -> mc.getCorso() == idCorso && mc.getAnnoCorso() == year)
                .toList();
        for (Materiale materiale : materialeCorso) {
            MaterialeCorso matCorso = new MaterialeCorso();
            matCorso.setCorso(idCorso);
            matCorso.setAnnoCorso(year);
            matCorso.setMateriale(materiale.getIdMateriale());
            if (oldMaterialeCorso.isEmpty()) {
                entityTransaction.begin();
                em.persist(matCorso);
                entityTransaction.commit();
            } else if (oldMaterialeCorso.contains(matCorso)) {
                oldMaterialeCorso.remove(matCorso);
            } else {
                entityTransaction.begin();
                em.persist(matCorso);
                entityTransaction.commit();
            }
        }
        if (!oldMaterialeCorso.isEmpty()) {
            for (MaterialeCorso matCorso : oldMaterialeCorso) {
                entityTransaction.begin();
                em.remove(matCorso);
                entityTransaction.commit();
            }
        }
    }

    public static void updateRelazioniCorso(int idCorso, int annoCorso, List<String> uriCorsi, String tipo) {
        if (uriCorsi == null || uriCorsi.isEmpty()) {
            deleteRelazioniCorso(idCorso, annoCorso, tipo);
            return;
        }
        List<CollegCorsi> oldCollegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(collegCorsi -> collegCorsi.getThisCorso() == idCorso && collegCorsi.getAnnoThisCorso() == annoCorso && collegCorsi.getTipo().equals(tipo))
                .toList();
        for (String uri : uriCorsi) {
            CollegCorsi collegCorsi = new CollegCorsi();
            collegCorsi.setThisCorso(idCorso);
            collegCorsi.setAnnoThisCorso(annoCorso);
            collegCorsi.setTipo(tipo);
            int startIndex = uri.indexOf("courses/") + 8;
            int endIndexYear = uri.indexOf("/", startIndex);
            String year = uri.substring(startIndex, endIndexYear);
            int anno = Utils.getYear(year);
            collegCorsi.setAnnoOtherCorso(anno);
            String idString = uri.substring(endIndexYear + 1);
            int id;
            if (NumberUtils.isParsable(idString)) {
                id = Integer.parseInt(idString);
                collegCorsi.setOtherCorso(id);
            } else {
                continue;
            }
            int otherCorso = collegCorsi.getOtherCorso();
            int annoOtherCorso = collegCorsi.getAnnoOtherCorso();
            CollegCorsi errorColleg = stream.streamAll(em, CollegCorsi.class)
                    .where(cc -> cc.getThisCorso() == idCorso && cc.getAnnoThisCorso() == annoCorso && cc.getOtherCorso() == otherCorso && cc.getAnnoOtherCorso() == annoOtherCorso)
                    .findFirst()
                    .orElse(null);
            if (errorColleg != null) {
                oldCollegCorsi.remove(collegCorsi);
                entityTransaction.begin();
                em.remove(errorColleg);
                entityTransaction.commit();
            }
            if (oldCollegCorsi.isEmpty()) {
                entityTransaction.begin();
                em.persist(collegCorsi);
                entityTransaction.commit();
            } else if (oldCollegCorsi.contains(collegCorsi)) {
                oldCollegCorsi.remove(collegCorsi);
            } else {
                entityTransaction.begin();
                em.persist(collegCorsi);
                entityTransaction.commit();
            }
        }
        if (!oldCollegCorsi.isEmpty()) {
            for (CollegCorsi collegCorsi : oldCollegCorsi) {
                entityTransaction.begin();
                em.remove(collegCorsi);
                entityTransaction.commit();
            }
        }
    }

    public static List<HistoryCorso> getHistoryCorso(int id, String baseUri) {
        List<HistoryCorso> history = new ArrayList<>();
        List<Corso> corsi = stream.streamAll(em, Corso.class)
                .where(corso -> corso.getIdCorso() == id)
                .toList();
        for (Corso corso : corsi) {
            String anno;
            if (corso.getAnno() == Utils.getCurrentYear()) {
                anno = "current";
            } else {
                anno = String.valueOf(corso.getAnno());
            }
            history.add(new HistoryCorso(corso.getAnno(), baseUri + anno + "/" + corso.getIdCorso()));
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

    public static List<MaterialeCompleto> getMaterialeCorso(int idCorso, int year) {
        List<Materiale> materiale = stream.streamAll(em, MaterialeCorso.class)
                .where(materialeCorso -> materialeCorso.getCorso() == idCorso && materialeCorso.getAnnoCorso() == year)
                .join((c, source) -> source.stream(Materiale.class))
                .where(materialeCorsoMaterialePair -> materialeCorsoMaterialePair.getOne().getMateriale() == materialeCorsoMaterialePair.getTwo().getIdMateriale())
                .select(Pair::getTwo)
                .toList();
        List<MaterialeCompleto> materialeCompleto = new ArrayList<>();
        for (Materiale mat : materiale) {
            materialeCompleto.add(new MaterialeCompleto(mat));
        }
        return materialeCompleto;
    }

    public static RelazioniCorso getRelazioniCorso(int idCorso, int year, String baseUri) {
        List<CollegCorsi> collegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(cc -> cc.getThisCorso() == idCorso && cc.getAnnoThisCorso() == year && cc.getTipo().equals("propedeudico"))
                .toList();
        List<String> propedeudici = new ArrayList<>();
        for (CollegCorsi propedeudico : collegCorsi) {
            propedeudici.add(baseUri + propedeudico.getAnnoOtherCorso() + "/" + propedeudico.getOtherCorso());
        }
        collegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(cc -> cc.getThisCorso() == idCorso && cc.getAnnoThisCorso() == year && cc.getTipo().equals("mutuato"))
                .toList();
        List<String> mutuati = new ArrayList<>();
        for (CollegCorsi mutuato : collegCorsi) {
            mutuati.add(baseUri + mutuato.getAnnoOtherCorso() + "/" + mutuato.getOtherCorso());
        }
        collegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(cc -> cc.getThisCorso() == idCorso && cc.getAnnoThisCorso() == year && cc.getTipo().equals("modulo"))
                .toList();
        List<String> moduli = new ArrayList<>();
        for (CollegCorsi modulo : collegCorsi) {
            moduli.add(baseUri + modulo.getAnnoOtherCorso() + "/" + modulo.getOtherCorso());
        }
        CollegCorsi corsoMutua = stream.streamAll(em, CollegCorsi.class)
                .where(cc -> cc.getOtherCorso() == idCorso && cc.getAnnoOtherCorso() == year && cc.getTipo().equals("mutuato"))
                .findFirst()
                .orElse(null);
        String mutua = null;
        if (corsoMutua != null) {
            mutua = baseUri + corsoMutua.getAnnoThisCorso() + "/" + corsoMutua.getThisCorso();
        }
        return new RelazioniCorso(propedeudici, mutuati, moduli, mutua);
    }

    public static Links getLinks(int idCorso, int year) {
        return stream.streamAll(em, Links.class)
                .where(links -> links.getCorso() == idCorso && links.getAnnoCorso() == year)
                .findFirst()
                .orElse(new Links());
    }

    public static List<Cdl> getAllCdl() {
        return stream.streamAll(em, Cdl.class)
                .toList();
    }

    public static List<Cdl> getCdlTriennali() {
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getMagistrale() == 0)
                .toList();
    }

    public static List<Cdl> getCdlMagistrali() {
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getMagistrale() == 1)
                .toList();
    }

    public static Cdl getCdl(int id) {
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getIdcdl() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Corso> getCorsiInCdl(int idCdl) {
        return stream.streamAll(em, CorsiCdl.class)
                .where(corsiCdl -> corsiCdl.getCdl() == idCdl)
                .join((c, source) -> source.stream(Corso.class))
                .where(corsiCdlCorsoPair -> corsiCdlCorsoPair.getOne().getCorso() == corsiCdlCorsoPair.getTwo().getIdCorso() && corsiCdlCorsoPair.getOne().getAnnoCorso() == corsiCdlCorsoPair.getTwo().getAnno())
                .select(Pair::getTwo)
                .toList();
    }

    public static void insertCdl() {

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

    public static void deleteCorso(int idCorso, int year) {
        Corso corso = stream.streamAll(em, Corso.class)
                .where(c -> c.getIdCorso() == idCorso && c.getAnno() == year)
                .findFirst()
                .orElse(null);
        if (corso == null) {
            return;
        }
        deleteDescrizione(idCorso, year, DescrizioneIt.class);
        deleteDescrizione(idCorso, year, DescrizioneEn.class);
        deleteLinks(idCorso, year);
        deleteDublino(idCorso, year, DublinoIt.class);
        deleteDublino(idCorso, year, DublinoEn.class);
        deleteDocentiCorso(idCorso, year);
        deleteCdlCorso(idCorso, year);
        deleteLibriCorso(idCorso, year);
        deleteMaterialeCorso(idCorso, year);
        deleteRelazioniCorso(idCorso, year, "propedeutico");
        deleteRelazioniCorso(idCorso, year, "modulo");
        deleteRelazioniCorso(idCorso, year, "mutuato");
        entityTransaction.begin();
        em.remove(corso);
        entityTransaction.commit();
        updateVersione("corso");
    }

    private static void deleteDescrizione(int idCorso, int year, Class classe) {
        Descrizione descrizione;
        if (classe.equals(DescrizioneIt.class)) {
            descrizione = stream.streamAll(em, DescrizioneIt.class)
                    .where(descrizioneIt -> descrizioneIt.getCorso() == idCorso && descrizioneIt.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        } else {
            descrizione = stream.streamAll(em, DescrizioneEn.class)
                    .where(descrizioneEn -> descrizioneEn.getCorso() == idCorso && descrizioneEn.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        }
        if (descrizione != null) {
            entityTransaction.begin();
            em.remove(descrizione);
            entityTransaction.commit();
        }
    }

    public static void deleteLinks(int idCorso, int year) {
        Links links = stream.streamAll(em, Links.class)
                .where(l -> l.getCorso() == idCorso && l.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
        if (links != null) {
            entityTransaction.begin();
            em.remove(links);
            entityTransaction.commit();
        }
    }

    private static void deleteDublino(int idCorso, int year, Class classe) {
        Dublino dublino;
        if (classe.equals(DublinoIt.class)) {
            dublino = stream.streamAll(em, DublinoIt.class)
                    .where(dublinoIt -> dublinoIt.getCorso() == idCorso && dublinoIt.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        } else {
            dublino = stream.streamAll(em, DublinoEn.class)
                    .where(dublinoEn -> dublinoEn.getCorso() == idCorso && dublinoEn.getAnnoCorso() == year)
                    .findFirst()
                    .orElse(null);
        }
        if (dublino != null) {
            entityTransaction.begin();
            em.remove(dublino);
            entityTransaction.commit();
        }
    }

    public static void deleteDocentiCorso(int idCorso, int year) {
        List<DocentiCorso> docentiCorso = stream.streamAll(em, DocentiCorso.class)
                .where(dc -> dc.getCorso() == idCorso && dc.getAnnoCorso() == year)
                .toList();
        for (DocentiCorso doc : docentiCorso) {
            entityTransaction.begin();
            em.remove(doc);
            entityTransaction.commit();
        }
    }

    private static void deleteCdlCorso(int idCorso, int year) {
        List<CorsiCdl> corsiCdl = stream.streamAll(em, CorsiCdl.class)
                .where(cc -> cc.getCorso() == idCorso && cc.getAnnoCorso() == year)
                .toList();
        for (CorsiCdl cc : corsiCdl) {
            entityTransaction.begin();
            em.remove(cc);
            entityTransaction.commit();
        }
    }

    private static void deleteLibriCorso(int idCorso, int year) {
        List<LibriCorso> libriCorso = stream.streamAll(em, LibriCorso.class)
                .where(lc -> lc.getCorso() == idCorso && lc.getAnnoCorso() == year)
                .toList();
        for (LibriCorso lc : libriCorso) {
            entityTransaction.begin();
            em.remove(lc);
            entityTransaction.commit();
        }
    }

    private static void deleteMaterialeCorso(int idCorso, int year) {
        List<MaterialeCorso> materialeCorso = stream.streamAll(em, MaterialeCorso.class)
                .where(mc -> mc.getCorso() == idCorso && mc.getAnnoCorso() == year)
                .toList();
        for (MaterialeCorso matCorso : materialeCorso) {
            entityTransaction.begin();
            em.remove(matCorso);
            entityTransaction.commit();
        }
    }

    private static void deleteRelazioniCorso(int idCorso, int annoCorso, String tipo) {
        List<CollegCorsi> collegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(cg -> cg.getThisCorso() == idCorso && cg.getAnnoThisCorso() == annoCorso && cg.getTipo().equals(tipo))
                .toList();
        for (CollegCorsi colCorso : collegCorsi) {
            entityTransaction.begin();
            em.remove(colCorso);
            entityTransaction.commit();
        }
    }

    public static List<Versioni> getVersioni() {
        return stream.streamAll(em, Versioni.class)
                .toList();
    }

    public static Versioni getVersione(String tabella) {
        return stream.streamAll(em, Versioni.class)
                .where(versioni -> versioni.getTabella().equals(tabella))
                .findFirst()
                .orElse(null);
    }

    public static void updateVersione(String tabella) {
        Random random = new Random();
        int version = random.nextInt();
        Versioni versione = stream.streamAll(em, Versioni.class)
                .where(versioni -> versioni.getTabella().equals(tabella))
                .findFirst()
                .orElse(null);
        if (versione != null) {
            while (versione.getVersione() == version) {
                version = random.nextInt();
            }
            versione.setVersione(version);
            entityTransaction.begin();
            em.persist(versione);
            entityTransaction.commit();
        }
    }
}