package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Materiale {
    private int idMateriale;
    private Integer corso;
    private Integer annoCorso;
    private String nome;
    private String link;
    private String descrizioneIt;
    private String descrizioneEn;

    @Id
    @Column(name = "IDMateriale")
    public int getIdMateriale() {
        return idMateriale;
    }

    public void setIdMateriale(int idMateriale) {
        this.idMateriale = idMateriale;
    }

    @Basic
    @Column(name = "Corso")
    public Integer getCorso() {
        return corso;
    }

    public void setCorso(Integer corso) {
        this.corso = corso;
    }

    @Basic
    @Column(name = "AnnoCorso")
    public Integer getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(Integer annoCorso) {
        this.annoCorso = annoCorso;
    }

    @Basic
    @Column(name = "Nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "Link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Basic
    @Column(name = "Descrizione_it")
    public String getDescrizioneIt() {
        return descrizioneIt;
    }

    public void setDescrizioneIt(String descrizioneIt) {
        this.descrizioneIt = descrizioneIt;
    }

    @Basic
    @Column(name = "Descrizione_en")
    public String getDescrizioneEn() {
        return descrizioneEn;
    }

    public void setDescrizioneEn(String descrizioneEn) {
        this.descrizioneEn = descrizioneEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materiale materiale = (Materiale) o;
        return idMateriale == materiale.idMateriale &&
                Objects.equals(corso, materiale.corso) &&
                Objects.equals(annoCorso, materiale.annoCorso) &&
                Objects.equals(nome, materiale.nome) &&
                Objects.equals(link, materiale.link) &&
                Objects.equals(descrizioneIt, materiale.descrizioneIt) &&
                Objects.equals(descrizioneEn, materiale.descrizioneEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMateriale, corso, annoCorso, nome, link, descrizioneIt, descrizioneEn);
    }
}
