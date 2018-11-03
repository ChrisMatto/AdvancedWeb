package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Cdl {
    private int idcdl;
    private String nomeIt;
    private String nomeEn;
    private int annoInizio;
    private int annoFine;
    private int cfu;
    private byte magistrale;
    private String immagine;
    private String descrizioneIt;
    private String descrizioneEn;
    private String abbrIt;
    private String abbrEn;

    @Id
    @Column(name = "IDCDL")
    public int getIdcdl() {
        return idcdl;
    }

    public void setIdcdl(int idcdl) {
        this.idcdl = idcdl;
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
    @Column(name = "CFU")
    public int getCfu() {
        return cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    @Basic
    @Column(name = "Magistrale")
    public byte getMagistrale() {
        return magistrale;
    }

    public void setMagistrale(byte magistrale) {
        this.magistrale = magistrale;
    }

    @Basic
    @Column(name = "Immagine")
    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
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

    @Basic
    @Column(name = "Abbr_it")
    public String getAbbrIt() {
        return abbrIt;
    }

    public void setAbbrIt(String abbrIt) {
        this.abbrIt = abbrIt;
    }

    @Basic
    @Column(name = "Abbr_en")
    public String getAbbrEn() {
        return abbrEn;
    }

    public void setAbbrEn(String abbrEn) {
        this.abbrEn = abbrEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cdl cdl = (Cdl) o;
        return idcdl == cdl.idcdl &&
                annoInizio == cdl.annoInizio &&
                annoFine == cdl.annoFine &&
                cfu == cdl.cfu &&
                magistrale == cdl.magistrale &&
                Objects.equals(nomeIt, cdl.nomeIt) &&
                Objects.equals(nomeEn, cdl.nomeEn) &&
                Objects.equals(immagine, cdl.immagine) &&
                Objects.equals(descrizioneIt, cdl.descrizioneIt) &&
                Objects.equals(descrizioneEn, cdl.descrizioneEn) &&
                Objects.equals(abbrIt, cdl.abbrIt) &&
                Objects.equals(abbrEn, cdl.abbrEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcdl, nomeIt, nomeEn, annoInizio, annoFine, cfu, magistrale, immagine, descrizioneIt, descrizioneEn, abbrIt, abbrEn);
    }
}
