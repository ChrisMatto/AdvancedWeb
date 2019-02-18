package Classi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Materiale {
    private int idMateriale;
    private String nome;
    private String link;
    private String descrizioneIt;
    private String descrizioneEn;

    @Id
    @Column(name = "IDMateriale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdMateriale() {
        return idMateriale;
    }

    public void setIdMateriale(int idMateriale) {
        this.idMateriale = idMateriale;
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
    @JsonIgnore
    public String getLink() {
        return link;
    }

    @JsonProperty
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
                Objects.equals(nome, materiale.nome) &&
                Objects.equals(link, materiale.link) &&
                Objects.equals(descrizioneIt, materiale.descrizioneIt) &&
                Objects.equals(descrizioneEn, materiale.descrizioneEn);
    }

    public void copyFrom(Materiale materiale) {
        this.idMateriale = materiale.idMateriale;
        this.nome = materiale.nome;
        this.link = materiale.link;
        this.descrizioneIt = materiale.descrizioneIt;
        this.descrizioneEn = materiale.descrizioneEn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMateriale, nome, link, descrizioneIt, descrizioneEn);
    }
}
