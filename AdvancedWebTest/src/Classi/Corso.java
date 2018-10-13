package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Corso {
    private int idCorso;
    private String nomeIt;
    private String nomeEn;
    private String ssd;
    private String lingua;
    private Integer semestre;
    private Integer cfu;
    private Object anno;
    private String tipologia;
    private Integer oldId;

    @Id
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
    @Column(name = "Anno")
    public Object getAnno() {
        return anno;
    }

    public void setAnno(Object anno) {
        this.anno = anno;
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
                Objects.equals(nomeIt, corso.nomeIt) &&
                Objects.equals(nomeEn, corso.nomeEn) &&
                Objects.equals(ssd, corso.ssd) &&
                Objects.equals(lingua, corso.lingua) &&
                Objects.equals(semestre, corso.semestre) &&
                Objects.equals(cfu, corso.cfu) &&
                Objects.equals(anno, corso.anno) &&
                Objects.equals(tipologia, corso.tipologia) &&
                Objects.equals(oldId, corso.oldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorso, nomeIt, nomeEn, ssd, lingua, semestre, cfu, anno, tipologia, oldId);
    }
}
