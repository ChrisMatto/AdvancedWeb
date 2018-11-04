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
    private List<Integer> idDocenti;
    private List<CdlPerCorso> cdl;
    private List<Integer> idCdl;

    @JsonView(Views.CorsoIta.class)
    private DescrizioneIt descrizioneIt;

    @JsonView(Views.CorsoEn.class)
    private DescrizioneEn descrizioneEn;

    @JsonView(Views.CorsoIta.class)
    private DublinoIt dublinoIt;

    @JsonView(Views.CorsoEn.class)
    private DublinoEn dublinoEn;

    private List<Libro> libri;
    private List<Integer> idLibri;
    private List<Materiale> materiale;
    private List<Integer> idMateriale;

    public CorsoCompleto() {
        super();
        docenti = null;
        idDocenti = null;
        cdl = null;
        idCdl = null;
        descrizioneEn = null;
        descrizioneIt = null;
        dublinoIt = null;
        dublinoEn = null;
        libri = null;
        idLibri = null;
        materiale = null;
        idMateriale = null;
    }

    public CorsoCompleto(Corso corso) {
        super();
        if (corso == null) {
            return;
        }
        super.copyFrom(corso);
        docenti = new ArrayList<>();
        idDocenti = new ArrayList<>();
        List<Docente> docList = DataAccess.getDocentiInCorso(super.getIdCorso(), super.getAnno());
        for (Docente doc : docList) {
            docenti.add(new DocentePerCorso(doc));
            idDocenti.add(doc.getIdDocente());
        }
        cdl = new ArrayList<>();
        idCdl = new ArrayList<>();
        List<Cdl> cdlList = DataAccess.getCdlInCorso(super.getIdCorso(), super.getAnno());
        for (Cdl c : cdlList) {
            cdl.add(new CdlPerCorso(c));
            idCdl.add(c.getIdcdl());
        }
        descrizioneIt = DataAccess.getDescrizioneIt(super.getIdCorso(), super.getAnno());
        descrizioneEn = DataAccess.getDescrizioneEn(super.getIdCorso(), super.getAnno());
        dublinoIt = DataAccess.getDublinoIt(super.getIdCorso(), super.getAnno());
        dublinoEn = DataAccess.getDublinoEn(super.getIdCorso(), super.getAnno());
        libri = DataAccess.getLibriInCorso(super.getIdCorso(), super.getAnno());
        idLibri = new ArrayList<>();
        for (Libro libro : libri) {
            idLibri.add(libro.getIdLibro());
        }
        materiale = DataAccess.getMaterialeCorso(super.getIdCorso(), super.getAnno());
        idMateriale = new ArrayList<>();
        for (Materiale materiale : materiale) {
            idMateriale.add(materiale.getIdMateriale());
        }
    }

    public List<DocentePerCorso> getDocenti() {
        return docenti;
    }

    public void setDocenti(List<DocentePerCorso> docenti) {
        this.docenti = docenti;
    }

    @JsonIgnore
    public List<Integer> getIdDocenti() {
        return idDocenti;
    }

    @JsonProperty
    public void setIdDocenti(List<Integer> idDocenti) {
        this.idDocenti = idDocenti;
    }

    public List<CdlPerCorso> getCdl() {
        return cdl;
    }

    @JsonIgnore
    public List<Integer> getIdCdl() {
        return idCdl;
    }

    @JsonProperty
    public void setIdCdl(List<Integer> idCdl) {
        this.idCdl = idCdl;
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

    @JsonIgnore
    public List<Integer> getIdLibri() {
        return idLibri;
    }

    @JsonProperty
    public void setIdLibri(List<Integer> idLibri) {
        this.idLibri = idLibri;
    }

    public List<Materiale> getMateriale() {
        return materiale;
    }

    public void setMateriale(List<Materiale> materiale) {
        this.materiale = materiale;
    }

    @JsonIgnore
    public List<Integer> getIdMateriale() {
        return idMateriale;
    }

    @JsonProperty
    public void setIdMateriale(List<Integer> idMateriale) {
        this.idMateriale = idMateriale;
    }
}