package Classi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "descrizione_en", schema = "newadvancedweb", catalog = "")
@IdClass(DescrizioneEnPK.class)
public class DescrizioneEn {

    @JsonIgnore
    private int corso;

    @JsonIgnore
    private int annoCorso;

    private String prerequisiti;
    private String obiettivi;
    private String modEsame;
    private String modInsegnamento;
    private String sillabo;
    private String note;
    private String homepage;
    private String forum;
    private String risorseExt;

    @Id
    @Column(name = "Corso")
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Id
    @Column(name = "AnnoCorso")
    public int getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(int annoCorso) {
        this.annoCorso = annoCorso;
    }

    @Basic
    @Column(name = "Prerequisiti")
    public String getPrerequisiti() {
        return prerequisiti;
    }

    public void setPrerequisiti(String prerequisiti) {
        this.prerequisiti = prerequisiti;
    }

    @Basic
    @Column(name = "Obiettivi")
    public String getObiettivi() {
        return obiettivi;
    }

    public void setObiettivi(String obiettivi) {
        this.obiettivi = obiettivi;
    }

    @Basic
    @Column(name = "Mod_Esame")
    public String getModEsame() {
        return modEsame;
    }

    public void setModEsame(String modEsame) {
        this.modEsame = modEsame;
    }

    @Basic
    @Column(name = "Mod_Insegnamento")
    public String getModInsegnamento() {
        return modInsegnamento;
    }

    public void setModInsegnamento(String modInsegnamento) {
        this.modInsegnamento = modInsegnamento;
    }

    @Basic
    @Column(name = "Sillabo")
    public String getSillabo() {
        return sillabo;
    }

    public void setSillabo(String sillabo) {
        this.sillabo = sillabo;
    }

    @Basic
    @Column(name = "Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "Homepage")
    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Basic
    @Column(name = "Forum")
    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    @Basic
    @Column(name = "Risorse_ext")
    public String getRisorseExt() {
        return risorseExt;
    }

    public void setRisorseExt(String risorseExt) {
        this.risorseExt = risorseExt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescrizioneEn that = (DescrizioneEn) o;
        return corso == that.corso &&
                annoCorso == that.annoCorso &&
                Objects.equals(prerequisiti, that.prerequisiti) &&
                Objects.equals(obiettivi, that.obiettivi) &&
                Objects.equals(modEsame, that.modEsame) &&
                Objects.equals(modInsegnamento, that.modInsegnamento) &&
                Objects.equals(sillabo, that.sillabo) &&
                Objects.equals(note, that.note) &&
                Objects.equals(homepage, that.homepage) &&
                Objects.equals(forum, that.forum) &&
                Objects.equals(risorseExt, that.risorseExt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, prerequisiti, obiettivi, modEsame, modInsegnamento, sillabo, note, homepage, forum, risorseExt);
    }
}
