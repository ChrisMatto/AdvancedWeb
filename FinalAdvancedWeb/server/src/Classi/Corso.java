package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Corso {
    private Integer idCorso;
    private String nomeIt;
    private String nomeEn;
    private String ssd;
    private String lingua;
    private Integer semestre;
    private Integer cfu;
    private Integer annoInizio;
    private Integer annoFine;
    private String tipologia;
    private Integer oldId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCorso")
    public int getIdCorso() {
        return idCorso;
    }

    public void setIdCorso(int idCorso) {
        this.idCorso = idCorso;
    }

    @Basic
    @Column(name = "Nome_it")
    public String getNomeIt() {
        return nomeIt;
    }

    public void setNomeIt(String nomeIt) {
        this.nomeIt = nomeIt;
    }

    @Basic
    @Column(name = "Nome_en")
    public String getNomeEn() {
        return nomeEn;
    }

    public void setNomeEn(String nomeEn) {
        this.nomeEn = nomeEn;
    }

    @Basic
    @Column(name = "SSD")
    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    @Basic
    @Column(name = "Lingua")
    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    @Basic
    @Column(name = "Semestre")
    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    @Basic
    @Column(name = "CFU")
    public Integer getCfu() {
        return cfu;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    @Basic
    @Column(name = "AnnoInizio")
    public int getAnnoInizio() {
        return annoInizio;
    }

    public void setAnnoInizio(int annoInizio) {
        this.annoInizio = annoInizio;
    }

    @Basic
    @Column(name = "AnnoFine")
    public int getAnnoFine() {
        return annoFine;
    }

    public void setAnnoFine(int annoFine) {
        this.annoFine = annoFine;
    }

    @Basic
    @Column(name = "Tipologia")
    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Basic
    @Column(name = "OldID")
    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corso corso = (Corso) o;
        return idCorso == corso.idCorso &&
                annoInizio == corso.annoInizio &&
                annoFine == corso.annoFine &&
                Objects.equals(nomeIt, corso.nomeIt) &&
                Objects.equals(nomeEn, corso.nomeEn) &&
                Objects.equals(ssd, corso.ssd) &&
                Objects.equals(lingua, corso.lingua) &&
                Objects.equals(semestre, corso.semestre) &&
                Objects.equals(cfu, corso.cfu) &&
                Objects.equals(tipologia, corso.tipologia) &&
                Objects.equals(oldId, corso.oldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorso, nomeIt, nomeEn, ssd, lingua, semestre, cfu, annoInizio, annoFine, tipologia, oldId);
    }

    public void copyFrom(Corso corso) {
        this.idCorso = corso.idCorso;
        this.nomeIt = corso.nomeIt;
        this.nomeEn = corso.nomeEn;
        this.ssd = corso.ssd;
        this.lingua = corso.lingua;
        this.semestre = corso.semestre;
        this.cfu = corso.cfu;
        this.annoInizio = corso.annoInizio;
        this.annoFine = corso.annoFine;
        this.tipologia = corso.tipologia;
        this.oldId = corso.oldId;
    }
}
