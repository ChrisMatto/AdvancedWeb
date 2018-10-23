package ClassiTemp;

import Classi.Cdl;
import Classi.Corso;
import Classi.Docente;
import DataAccess.DataAccess;

import java.util.List;

public class CorsoCompleto {
    private int idCorso;
    private String nomeIt;
    private String nomeEn;
    private String ssd;
    private String lingua;
    private Integer semestre;
    private Integer cfu;
    private int annoInizio;
    private int annoFine;
    private String tipologia;
    private Integer oldId;
    private List<Docente> docenti;
    private List<Cdl> cdl;

    public CorsoCompleto(Corso corso) {
        idCorso = corso.getIdCorso();
        nomeIt = corso.getNomeIt();
        nomeEn = corso.getNomeEn();
        ssd = corso.getSsd();
        lingua = corso.getLingua();
        semestre = corso.getSemestre();
        cfu = corso.getCfu();
        annoInizio = corso.getAnnoInizio();
        annoFine = corso.getAnnoFine();
        tipologia = corso.getTipologia();
        oldId = corso.getOldId();
        docenti = DataAccess.getDocentiInCorso(idCorso);
        cdl = DataAccess.getCdlInCorso(idCorso);
    }

    public int getIdCorso() {
        return idCorso;
    }

    public void setIdCorso(int idCorso) {
        this.idCorso = idCorso;
    }

    public String getNomeIt() {
        return nomeIt;
    }

    public void setNomeIt(String nomeIt) {
        this.nomeIt = nomeIt;
    }

    public String getNomeEn() {
        return nomeEn;
    }

    public void setNomeEn(String nomeEn) {
        this.nomeEn = nomeEn;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getCfu() {
        return cfu;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    public int getAnnoInizio() {
        return annoInizio;
    }

    public void setAnnoInizio(int annoInizio) {
        this.annoInizio = annoInizio;
    }

    public int getAnnoFine() {
        return annoFine;
    }

    public void setAnnoFine(int annoFine) {
        this.annoFine = annoFine;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public List<Docente> getDocenti() {
        return docenti;
    }

    public void setDocenti(List<Docente> docenti) {
        this.docenti = docenti;
    }

    public void addDocenti(Docente docente) {
        this.docenti.add(docente);
    }

    public List<Cdl> getCdl() {
        return cdl;
    }

    public void setCdl(List<Cdl> cdl) {
        this.cdl = cdl;
    }

    public void addCdl(Cdl cdl) {
        this.cdl.add(cdl);
    }

}