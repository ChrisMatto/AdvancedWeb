package Classi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(LinksPK.class)
public class Links {

    @JsonIgnore
    private int corso;
    @JsonIgnore
    private int annoCorso;

    private String homepage;
    private String forum;
    private String elearning;
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
    @Column(name = "Elearning")
    public String getElearning() {
        return elearning;
    }

    public void setElearning(String elearning) {
        this.elearning = elearning;
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
        Links links = (Links) o;
        return corso == links.corso &&
                annoCorso == links.annoCorso &&
                Objects.equals(homepage, links.homepage) &&
                Objects.equals(forum, links.forum) &&
                Objects.equals(elearning, links.elearning) &&
                Objects.equals(risorseExt, links.risorseExt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, homepage, forum, elearning, risorseExt);
    }
}
