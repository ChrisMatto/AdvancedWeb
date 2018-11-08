package ClassiTemp;

import Classi.*;
import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

public class CorsoCompleto extends Corso {
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
    private List<Materiale> materiale;
    private RelazioniCorso relazioni;

    public CorsoCompleto() {
        super();
        docenti = null;
        cdl = null;
        descrizioneEn = null;
        descrizioneIt = null;
        dublinoIt = null;
        dublinoEn = null;
        libri = null;
        materiale = null;
        relazioni = null;
        links = null;
    }

    public CorsoCompleto(Corso corso) {
        super();
        if (corso == null) {
            return;
        }
        super.copyFrom(corso);
        docenti = new ArrayList<>();
        List<Docente> docList = DataAccess.getDocentiInCorso(super.getIdCorso(), super.getAnno());
        for (Docente doc : docList) {
            docenti.add(new DocentePerCorso(doc));
        }
        cdl = new ArrayList<>();
        List<Cdl> cdlList = DataAccess.getCdlInCorso(super.getIdCorso(), super.getAnno());
        for (Cdl c : cdlList) {
            cdl.add(new CdlPerCorso(c));
        }
        descrizioneIt = DataAccess.getDescrizioneIt(super.getIdCorso(), super.getAnno());
        descrizioneEn = DataAccess.getDescrizioneEn(super.getIdCorso(), super.getAnno());
        dublinoIt = DataAccess.getDublinoIt(super.getIdCorso(), super.getAnno());
        dublinoEn = DataAccess.getDublinoEn(super.getIdCorso(), super.getAnno());
        libri = DataAccess.getLibriInCorso(super.getIdCorso(), super.getAnno());
        materiale = DataAccess.getMaterialeCorso(super.getIdCorso(), super.getAnno());
        relazioni = DataAccess.getRelazioniCorso(super.getIdCorso(), super.getAnno());
        links = DataAccess.getLinks(super.getIdCorso(), super.getAnno());
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

    public List<Materiale> getMateriale() {
        return materiale;
    }

    public void setMateriale(List<Materiale> materiale) {
        this.materiale = materiale;
    }

    public RelazioniCorso getRelazioni() {
        return relazioni;
    }

    public void setRelazioni(RelazioniCorso relazioni) {
        this.relazioni = relazioni;
    }
}