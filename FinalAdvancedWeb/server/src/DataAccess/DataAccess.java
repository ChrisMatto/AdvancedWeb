package DataAccess;

import Classi.*;
import Views.*;
import Controller.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;
import org.jinq.tuples.Pair;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class DataAccess {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    private EntityManager em;
    private JinqJPAStreamProvider stream;
    private EntityTransaction entityTransaction;

    public DataAccess() {
        em = entityManagerFactory.createEntityManager();
        stream = new JinqJPAStreamProvider(em.getMetamodel());
        entityTransaction = em.getTransaction();
    }

    public List<Integer> getCorsiByFilter(int year, MultivaluedMap<String,String> queryParams) {
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
                            .where(corsoCorsiCdlPair -> corsoCorsiCdlPair.getTwo().getCdl() == idCdl && corsoCorsiCdlPair.getOne().getIdCorso() == corsoCorsiCdlPair.getTwo().getCorso() &&
                                    corsoCorsiCdlPair.getTwo().getAnnoCorso() == corsoCorsiCdlPair.getOne().getAnno())
                            .select(Pair::getOne);
                    break;
                case "teacher":
                    int idDocente;
                    if (NumberUtils.isParsable(param)) {
                        idDocente = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi
                            .join((c, source) -> source.stream(DocentiCorso.class))
                            .where(corsoDocentiCorsoPair -> corsoDocentiCorsoPair.getTwo().getDocente() == idDocente && corsoDocentiCorsoPair.getOne().getIdCorso() == corsoDocentiCorsoPair.getTwo().getCorso() &&
                                    corsoDocentiCorsoPair.getTwo().getAnnoCorso() == corsoDocentiCorsoPair.getOne().getAnno())
                            .select(Pair::getOne);
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

    public List<Integer> getAnniCorsi() {
        return stream.streamAll(em, Corso.class)
                .select(Corso::getAnno)
                .distinct()
                .toList();
    }

    public Corso getCorso(int id, int year) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getIdCorso() == id && corso.getAnno() == year)
                .findFirst()
                .orElse(null);
    }

    public List<String> getDocenti(String baseUri) {
        List<Integer> docenti = stream.streamAll(em, Docente.class)
                .select(Docente::getIdDocente)
                .toList();
        List<String> docentiUri = new ArrayList<>();
        for (int id : docenti) {
            docentiUri.add(baseUri + id);
        }
        return docentiUri;
    }

    public Docente getDocente(int id) {
        return stream.streamAll(em, Docente.class)
                .where(docente -> docente.getIdDocente() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Corso> getCorsiDocente(int idDocente) {
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

    public MaterialeCompleto getMateriale(int idMateriale) {
        return new MaterialeCompleto(stream.streamAll(em, Materiale.class)
                .where(materiale -> materiale.getIdMateriale() == idMateriale)
                .findFirst()
                .orElse(null));
    }

    public void insertCorso(CorsoCompleto corsoCompleto) {
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

    private void insertDescrizioneIt(DescrizioneIt descrizioneIt) {
        entityTransaction.begin();
        em.persist(descrizioneIt);
        entityTransaction.commit();
    }

    private void insertDescrizioneEn(DescrizioneEn descrizioneEn) {
        entityTransaction.begin();
        em.persist(descrizioneEn);
        entityTransaction.commit();
    }

    private void insertLinks(Links links) {
        entityTransaction.begin();
        em.persist(links);
        entityTransaction.commit();
    }

    private void insertDublinoIt(DublinoIt dublinoIt) {
        entityTransaction.begin();
        em.persist(dublinoIt);
        entityTransaction.commit();
    }

    private void insertDublinoEn(DublinoEn dublinoEn) {
        entityTransaction.begin();
        em.persist(dublinoEn);
        entityTransaction.commit();
    }

    private void insertDocentiCorso(int idCorso, int annoCorso, List<DocentePerCorso> docenti) {
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

    private void insertCdlCorso(int idCorso, int annoCorso, List<CdlPerCorso> cdl) {
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

    private void insertLibriCorso(int idCorso, int annoCorso, List<Libro> libri) {
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

    private void insertMaterialeCorso(int idCorso, int annoCorso, List<MaterialeCompleto> materiale) {
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

    private void insertRelazioniCorso(int idCorso, int annoCorso, List<String> uriCorsi, String tipo) {
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

    public void updateCorso(int idCorso, int year, CorsoCompleto corsoCompleto) {
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

    private void updateDescrizione(int idCorso, int year, Descrizione descrizione) {
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

    public void updateLinks(int idCorso, int year, Links links) {
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
                oldLinks.setRisorseExt(links.getRisorseExt());
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

    private void updateDublino(int idCorso, int year, Dublino dublino) {
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

    public void updateDocentiCorso(int idCorso, int year, List<DocentePerCorso> docenti) {
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

    private void updateCdlCorso(int idCorso, int year, List<CdlPerCorso> cdlPerCorso) {
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

    private void updateLibriCorso(int idCorso, int year, List<Libro> libri) {
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

    private void updateMaterialeCorso(int idCorso, int year, List<MaterialeCompleto> materialeCorso) {
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

    public void updateRelazioniCorso(int idCorso, int annoCorso, List<String> uriCorsi, String tipo) {
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

    public List<HistoryCorso> getHistoryCorso(int id, String baseUri) {
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

    public List<Docente> getDocentiInCorso(int idCorso, int year) {
        return stream.streamAll(em, Docente.class)
                .join((docente, source) -> source.stream(DocentiCorso.class))
                .where(docenteDocentiCorsoPair -> docenteDocentiCorsoPair.getTwo().getCorso() == idCorso && docenteDocentiCorsoPair.getTwo().getAnnoCorso() == year && docenteDocentiCorsoPair.getOne().getIdDocente() == docenteDocentiCorsoPair.getTwo().getDocente())
                .select(Pair::getOne).toList();
    }

    public List<Cdl> getCdlInCorso(int idCorso, int year) {
        return stream.streamAll(em, Cdl.class)
                .join((cdl, source) -> source.stream(CorsiCdl.class))
                .where(cdlCorsiCdlPair -> cdlCorsiCdlPair.getTwo().getCorso() == idCorso && cdlCorsiCdlPair.getTwo().getAnnoCorso() == year && cdlCorsiCdlPair.getOne().getIdcdl() == cdlCorsiCdlPair.getTwo().getCdl())
                .select(Pair::getOne).toList();
    }

    public DescrizioneIt getDescrizioneIt(int idCorso, int year) {
        return stream.streamAll(em, DescrizioneIt.class)
                .where(descrizioneIt -> descrizioneIt.getCorso() == idCorso && descrizioneIt.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public DescrizioneEn getDescrizioneEn(int idCorso, int year) {
        return stream.streamAll(em, DescrizioneEn.class)
                .where(descrizioneEn -> descrizioneEn.getCorso() == idCorso && descrizioneEn.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public DublinoIt getDublinoIt(int idCorso, int year) {
        return stream.streamAll(em, DublinoIt.class)
                .where(dublinoIt -> dublinoIt.getCorso() == idCorso && dublinoIt.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public DublinoEn getDublinoEn(int idCorso, int year) {
        return stream.streamAll(em, DublinoEn.class)
                .where(dublinoEn -> dublinoEn.getCorso() == idCorso && dublinoEn.getAnnoCorso() == year)
                .findFirst()
                .orElse(null);
    }

    public List<Libro> getLibriInCorso(int idCorso, int year) {
        return stream.streamAll(em, LibriCorso.class)
                .where(libriCorso -> libriCorso.getCorso() == idCorso && libriCorso.getAnnoCorso() == year)
                .join((c, source) -> source.stream(Libro.class))
                .where(libriCorsoLibroPair -> libriCorsoLibroPair.getOne().getLibro() == libriCorsoLibroPair.getTwo().getIdLibro())
                .select(Pair::getTwo)
                .toList();
    }

    public List<MaterialeCompleto> getMaterialeCorso(int idCorso, int year) {
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

    public RelazioniCorso getRelazioniCorso(int idCorso, int year, String baseUri) {
        List<CollegCorsi> collegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(cc -> cc.getThisCorso() == idCorso && cc.getAnnoThisCorso() == year && cc.getTipo().equals("propedeutico"))
                .toList();
        List<String> propedeudici = new ArrayList<>();
        for (CollegCorsi propedeutico : collegCorsi) {
            propedeudici.add(baseUri + propedeutico.getAnnoOtherCorso() + "/" + propedeutico.getOtherCorso());
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

    public Links getLinks(int idCorso, int year) {
        return stream.streamAll(em, Links.class)
                .where(links -> links.getCorso() == idCorso && links.getAnnoCorso() == year)
                .findFirst()
                .orElse(new Links());
    }

    public List<Cdl> getAllCdl() {
        return stream.streamAll(em, Cdl.class)
                .toList();
    }

    public List<Cdl> getCdlTriennali() {
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getMagistrale() == 0)
                .toList();
    }

    public List<Cdl> getCdlMagistrali() {
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getMagistrale() == 1)
                .toList();
    }

    public Cdl getCdl(int id) {
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getIdcdl() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Corso> getCorsiInCdl(int idCdl) {
        return stream.streamAll(em, CorsiCdl.class)
                .where(corsiCdl -> corsiCdl.getCdl() == idCdl)
                .join((c, source) -> source.stream(Corso.class))
                .where(corsiCdlCorsoPair -> corsiCdlCorsoPair.getOne().getCorso() == corsiCdlCorsoPair.getTwo().getIdCorso() && corsiCdlCorsoPair.getOne().getAnnoCorso() == corsiCdlCorsoPair.getTwo().getAnno())
                .select(Pair::getTwo)
                .toList();
    }

    public int insertDocente(Docente docente) {
        entityTransaction.begin();
        em.persist(docente);
        entityTransaction.commit();
        updateVersione("docente");
        return docente.getIdDocente();
    }

    public void updateDocente(int idDocente, Docente docente) {
        Docente doc = stream.streamAll(em, Docente.class)
                .where(d -> d.getIdDocente() == idDocente)
                .findFirst()
                .orElse(null);
        if (doc == null) {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            throw new WebApplicationException(responseBuilder.build());
        }
        String immagine = doc.getImmagine();
        String curriculum = doc.getCurriculum();

        doc.copyFrom(docente);

        if (doc.getCurriculum() == null || doc.getCurriculum().trim().isEmpty()) {
            doc.setCurriculum(curriculum);
        }
        if (doc.getImmagine() == null || doc.getImmagine().trim().isEmpty()) {
            doc.setImmagine(immagine);
        }
        entityTransaction.begin();
        em.persist(doc);
        entityTransaction.commit();
        updateVersione("docente");
    }

    public List<Integer> getUtenti() {
        return stream.streamAll(em, Utente.class)
                .select(Utente::getIdUtente)
                .toList();
    }

    public Utente getUtente(String username, String password) {
        Optional<Utente> u = stream.streamAll(em, Utente.class)
                .where(utente -> utente.getUsername().equals(username) && utente.getPassword().equals(password))
                .findFirst();
        return u.orElse(null);
    }

    public Utente getUtenteNoPassword(int id) {
        return stream.streamAll(em, Utente.class)
                .where(utente -> utente.getIdUtente() == id)
                .findFirst()
                .orElse(null);
    }

    public void insertUtente(Utente utente) {
        if (utente.getDocente() == null) {
            utente.setGruppo(1);
        }
        entityTransaction.begin();
        em.persist(utente);
        entityTransaction.commit();
    }

    public void updateUtente(int idUtente, Utente utente) {
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

    public void deleteUtente(int id) {
        Utente utente = stream.streamAll(em, Utente.class)
                .where(u -> u.getIdUtente() == id)
                .findFirst()
                .orElse(null);
        entityTransaction.begin();
        em.remove(utente);
        entityTransaction.commit();
    }

    public Boolean existDocente(int id) {
        return stream.streamAll(em, Docente.class)
                .where(docente -> docente.getIdDocente() == id)
                .findFirst()
                .isPresent();
    }

    public Boolean existUsernameUtente(String username) {
        return stream.streamAll(em, Utente.class)
                .where(u -> u.getUsername().equals(username))
                .findFirst().isPresent();
    }

    public Boolean existDocenteInUtente(int idDocente) {
        return stream.streamAll(em, Utente.class)
                .where(u -> u.getDocente() == idDocente)
                .findFirst()
                .isPresent();
    }

    public Boolean existGruppo(int id) {
        return stream.streamAll(em, Gruppo.class)
                .where(gruppo -> gruppo.getIdGruppo() == id)
                .findFirst()
                .isPresent();
    }

    public Boolean existSessioneUtente(int idUtente) {
        return stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getUtente() == idUtente)
                .findFirst()
                .isPresent();
    }

    public Boolean existsSessione(String token) {
        return stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getToken().equals(token))
                .findFirst()
                .isPresent();
    }

    public void deleteOldSessions() {
        List<Sessione> sessioni = stream.streamAll(em, Sessione.class)
                .toList();
        entityTransaction.begin();
        for (Sessione sessione : sessioni) {
            if (Utils.shouldDeleteSession(sessione.getData())) {
                em.remove(sessione);
            }
        }
        entityTransaction.commit();
    }

    public String makeSessione(Utente utente) {
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

    public String getSessionToken(int idUtente) {
        return stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getUtente() == idUtente)
                .select(Sessione::getToken)
                .findFirst()
                .orElse(null);
    }

    public Utente getSessionUtente(String token) {
        return stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getToken().equals(token))
                .join((c, source) -> source.stream(Utente.class))
                .where(sessioneUtentePair -> sessioneUtentePair.getOne().getUtente() == sessioneUtentePair.getTwo().getIdUtente())
                .select(Pair::getTwo)
                .findFirst()
                .orElse(null);
    }

    public void deleteSession(String token) {
        Optional<Sessione> sessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token))
                .findFirst();
        if(sessione.isPresent()) {
            entityTransaction.begin();
            em.remove(sessione.get());
            entityTransaction.commit();
        }
    }

    public Boolean checkAccessToken(String token, String service, String method) {
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

    public Boolean checkAccessNoToken(String service, String method) {
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service) && s.getMetodo().equals(method))
                .findFirst();
        return !optionalServizio.isPresent();
    }

    public void deleteCorso(int idCorso, int year) {
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

    private void deleteDescrizione(int idCorso, int year, Class classe) {
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

    public void deleteLinks(int idCorso, int year) {
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

    private void deleteDublino(int idCorso, int year, Class classe) {
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

    public void deleteDocentiCorso(int idCorso, int year) {
        List<DocentiCorso> docentiCorso = stream.streamAll(em, DocentiCorso.class)
                .where(dc -> dc.getCorso() == idCorso && dc.getAnnoCorso() == year)
                .toList();
        for (DocentiCorso doc : docentiCorso) {
            entityTransaction.begin();
            em.remove(doc);
            entityTransaction.commit();
        }
    }

    private void deleteCdlCorso(int idCorso, int year) {
        List<CorsiCdl> corsiCdl = stream.streamAll(em, CorsiCdl.class)
                .where(cc -> cc.getCorso() == idCorso && cc.getAnnoCorso() == year)
                .toList();
        for (CorsiCdl cc : corsiCdl) {
            entityTransaction.begin();
            em.remove(cc);
            entityTransaction.commit();
        }
    }

    private void deleteLibriCorso(int idCorso, int year) {
        List<LibriCorso> libriCorso = stream.streamAll(em, LibriCorso.class)
                .where(lc -> lc.getCorso() == idCorso && lc.getAnnoCorso() == year)
                .toList();
        for (LibriCorso lc : libriCorso) {
            entityTransaction.begin();
            em.remove(lc);
            entityTransaction.commit();
        }
    }

    private void deleteMaterialeCorso(int idCorso, int year) {
        List<MaterialeCorso> materialeCorso = stream.streamAll(em, MaterialeCorso.class)
                .where(mc -> mc.getCorso() == idCorso && mc.getAnnoCorso() == year)
                .toList();
        for (MaterialeCorso matCorso : materialeCorso) {
            entityTransaction.begin();
            em.remove(matCorso);
            entityTransaction.commit();
        }
    }

    private void deleteRelazioniCorso(int idCorso, int annoCorso, String tipo) {
        List<CollegCorsi> collegCorsi = stream.streamAll(em, CollegCorsi.class)
                .where(cg -> cg.getThisCorso() == idCorso && cg.getAnnoThisCorso() == annoCorso && cg.getTipo().equals(tipo))
                .toList();
        for (CollegCorsi colCorso : collegCorsi) {
            entityTransaction.begin();
            em.remove(colCorso);
            entityTransaction.commit();
        }
    }

    public List<Versioni> getVersioni() {
        return stream.streamAll(em, Versioni.class)
                .toList();
    }

    public Versioni getVersione(String tabella) {
        return stream.streamAll(em, Versioni.class)
                .where(versioni -> versioni.getTabella().equals(tabella))
                .findFirst()
                .orElse(null);
    }

    public void updateVersione(String tabella) {
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

    public void saveLog(String token, String descrizione) {
        Utente utente = getSessionUtente(token);
        Log log = new Log();
        log.setUtente(utente.getIdUtente());
        log.setDescrizione(utente.getUsername() + " " + descrizione);
        log.setData(new Timestamp(System.currentTimeMillis()));
        entityTransaction.begin();
        em.persist(log);
        entityTransaction.commit();
    }

    public List<Log> getLogs() {
        return stream.streamAll(em, Log.class)
                .toList();
    }

}