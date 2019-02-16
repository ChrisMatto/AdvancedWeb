package Classi;

import Views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(CorsoPK.class)
public class Corso {
    private int idCorso;
    private int anno;

    @JsonView(Views.CorsoIta.class)
    private String nomeIt;

    @JsonView(Views.CorsoEn.class)
    private String nomeEn;

    private String ssd;
    private String lingua;
    private Integer semestre;
    private Integer cfu;
    private String tipologia;

    @Id
    @Column(name = "IDCorso")
    public int getIdCorso() {
        return idCorso;
    }

    public void setIdCorso(int idCorso) {
        this.idCorso = idCorso;
    }

    @Id
    @Column(name = "Anno")
    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
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
    @Column(name = "Tipologia")
    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corso corso = (Corso) o;
        return idCorso == corso.idCorso &&
                anno == corso.anno &&
                Objects.equals(nomeIt, corso.nomeIt) &&
                Objects.equals(nomeEn, corso.nomeEn) &&
                Objects.equals(ssd, corso.ssd) &&
                Objects.equals(lingua, corso.lingua) &&
                Objects.equals(semestre, corso.semestre) &&
                Objects.equals(cfu, corso.cfu) &&
                Objects.equals(tipologia, corso.tipologia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorso, anno, nomeIt, nomeEn, ssd, lingua, semestre, cfu, tipologia);
    }

    public void copyFrom(Corso corso) {
        this.idCorso = corso.idCorso;
        this.nomeIt = corso.nomeIt;
        this.nomeEn = corso.nomeEn;
        this.ssd = corso.ssd;
        this.lingua = corso.lingua;
        this.semestre = corso.semestre;
        this.cfu = corso.cfu;
        this.anno = corso.anno;
        this.tipologia = corso.tipologia;
    }
}
