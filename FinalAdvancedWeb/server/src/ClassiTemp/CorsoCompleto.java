package ClassiTemp;

import Classi.*;
import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

public class CorsoCompleto extends Corso {
    private List<DocentePerCorso> docenti;
    private List<CdlPerCorso> cdl;

    @JsonView(CorsoView.Ita.class)
    private DescrizioneIt descrizioneIt;

    @JsonView(CorsoView.En.class)
    private DescrizioneEn descrizioneEn;

    @JsonView(CorsoView.Ita.class)
    private DublinoIt dublinoIt;

    @JsonView(CorsoView.En.class)
    private DublinoEn dublinoEn;

    private List<Libro> libri;

    public CorsoCompleto(Corso corso) {
        super();
        if (corso == null) {
            return;
        }
        super.copyFrom(corso);
        docenti = new ArrayList<>();
        List<Docente> docList = DataAccess.getDocentiInCorso(super.getIdCorso());
        for (Docente doc : docList) {
            docenti.add(new DocentePerCorso(doc));
        }
        cdl = new ArrayList<>();
        List<Cdl> cdlList = DataAccess.getCdlInCorso(super.getIdCorso());
        for (Cdl c : cdlList) {
            cdl.add(new CdlPerCorso(c));
        }
        descrizioneIt = DataAccess.getDescrizioneIt(super.getIdCorso());
        descrizioneEn = DataAccess.getDescrizioneEn(super.getIdCorso());
        dublinoIt = DataAccess.getDublinoIt(super.getIdCorso());
        dublinoEn = DataAccess.getDublinoEn(super.getIdCorso());
        libri = DataAccess.getLibriInCorso(super.getIdCorso());
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
}