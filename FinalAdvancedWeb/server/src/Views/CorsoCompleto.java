package Views;

import Classi.*;
import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CorsoCompleto extends Corso {

    @Inject
    @JsonIgnore
    private DataAccess dataAccess;

    private List<DocentePerCorso> docenti;
    private List<CdlPerCorso> cdl;

    @JsonView(Views.CorsoIta.class)
    private DescrizioneIt descrizioneIt;

    @JsonView(Views.CorsoEn.class)
    private DescrizioneEn descrizioneEn;

    private Links links;

    @JsonView(Views.CorsoIta.class)
    private DublinoIt dublinoIt;

    @JsonView(Views.CorsoEn.class)
    private DublinoEn dublinoEn;

    private List<Libro> libri;
    private List<MaterialeCompleto> materiale;
    private RelazioniCorso relazioni;

    public void init(Corso corso, String baseUri) {
        if (corso == null) {
            return;
        }
        super.copyFrom(corso);
        docenti = new ArrayList<>();
        List<Docente> docList = dataAccess.getDocentiInCorso(super.getIdCorso(), super.getAnno());
        for (Docente doc : docList) {
            docenti.add(new DocentePerCorso(doc));
        }
        cdl = new ArrayList<>();
        List<Cdl> cdlList = dataAccess.getCdlInCorso(super.getIdCorso(), super.getAnno());
        for (Cdl c : cdlList) {
            cdl.add(new CdlPerCorso(c));
        }
        descrizioneIt = dataAccess.getDescrizioneIt(super.getIdCorso(), super.getAnno());
        descrizioneEn = dataAccess.getDescrizioneEn(super.getIdCorso(), super.getAnno());
        dublinoIt = dataAccess.getDublinoIt(super.getIdCorso(), super.getAnno());
        dublinoEn = dataAccess.getDublinoEn(super.getIdCorso(), super.getAnno());
        libri = dataAccess.getLibriInCorso(super.getIdCorso(), super.getAnno());
        materiale = dataAccess.getMaterialeCorso(super.getIdCorso(), super.getAnno());
        relazioni = dataAccess.getRelazioniCorso(super.getIdCorso(), super.getAnno(), baseUri);
        links = dataAccess.getLinks(super.getIdCorso(), super.getAnno());
    }

    public List<DocentePerCorso> getDocenti() {
        return docenti;
    }

    public void setDocenti(List<DocentePerCorso> docenti) {
        this.docenti = docenti;
    }

    public List<CdlPerCorso> getCdl() {
        return cdl;
    }

    public void setCdl(List<CdlPerCorso> cdl) {
        this.cdl = cdl;
    }

    public DescrizioneIt getDescrizioneIt() {
        return this.descrizioneIt;
    }

    public void setDescrizioneIt(DescrizioneIt descrizioneIt) {
        this.descrizioneIt = descrizioneIt;
    }

    public DescrizioneEn getDescrizioneEn() {
        return this.descrizioneEn;
    }

    public void setDescrizioneEn(DescrizioneEn descrizioneEn) {
        this.descrizioneEn = descrizioneEn;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public DublinoIt getDublinoIt() {
        return this.dublinoIt;
    }

    public void setDublinoIt(DublinoIt dublinoIt) {
        this.dublinoIt = dublinoIt;
    }

    public DublinoEn getDublinoEn() {
        return this.dublinoEn;
    }

    public void setDublinoEn(DublinoEn dublinoEn) {
        this.dublinoEn = dublinoEn;
    }

    public List<Libro> getLibri() {
        return this.libri;
    }

    public void setLibri(List<Libro> libri) {
        this.libri = libri;
    }

    public List<MaterialeCompleto> getMateriale() {
        return materiale;
    }

    public void setMateriale(List<MaterialeCompleto> materiale) {
        this.materiale = materiale;
    }

    public RelazioniCorso getRelazioni() {
        return relazioni;
    }

    public void setRelazioni(RelazioniCorso relazioni) {
        this.relazioni = relazioni;
    }
}