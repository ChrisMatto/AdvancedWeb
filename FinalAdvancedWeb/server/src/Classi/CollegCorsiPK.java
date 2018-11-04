package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CollegCorsiPK implements Serializable {
    private int thisCorso;
    private int otherCorso;
    private int annoThisCorso;
    private int annoOtherCorso;

    @Column(name = "This_Corso")
    @Id
    public int getThisCorso() {
        return thisCorso;
    }

    public void setThisCorso(int thisCorso) {
        this.thisCorso = thisCorso;
    }

    @Column(name = "Other_Corso")
    @Id
    public int getOtherCorso() {
        return otherCorso;
    }

    public void setOtherCorso(int otherCorso) {
        this.otherCorso = otherCorso;
    }

    @Column(name = "Anno_This_Corso")
    @Id
    public int getAnnoThisCorso() {
        return annoThisCorso;
    }

    public void setAnnoThisCorso(int annoThisCorso) {
        this.annoThisCorso = annoThisCorso;
    }

    @Column(name = "Anno_Other_Corso")
    @Id
    public int getAnnoOtherCorso() {
        return annoOtherCorso;
    }

    public void setAnnoOtherCorso(int annoOtherCorso) {
        this.annoOtherCorso = annoOtherCorso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollegCorsiPK that = (CollegCorsiPK) o;
        return thisCorso == that.thisCorso &&
                otherCorso == that.otherCorso &&
                annoThisCorso == that.annoThisCorso &&
                annoOtherCorso == that.annoOtherCorso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thisCorso, otherCorso, annoThisCorso, annoOtherCorso);
    }
}
