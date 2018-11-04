package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "colleg_corsi", schema = "newadvancedweb", catalog = "")
@IdClass(CollegCorsiPK.class)
public class CollegCorsi {
    private int thisCorso;
    private int otherCorso;
    private int annoThisCorso;
    private int annoOtherCorso;
    private String tipo;

    @Id
    @Column(name = "This_Corso")
    public int getThisCorso() {
        return thisCorso;
    }

    public void setThisCorso(int thisCorso) {
        this.thisCorso = thisCorso;
    }

    @Id
    @Column(name = "Other_Corso")
    public int getOtherCorso() {
        return otherCorso;
    }

    public void setOtherCorso(int otherCorso) {
        this.otherCorso = otherCorso;
    }

    @Id
    @Column(name = "Anno_This_Corso")
    public int getAnnoThisCorso() {
        return annoThisCorso;
    }

    public void setAnnoThisCorso(int annoThisCorso) {
        this.annoThisCorso = annoThisCorso;
    }

    @Id
    @Column(name = "Anno_Other_Corso")
    public int getAnnoOtherCorso() {
        return annoOtherCorso;
    }

    public void setAnnoOtherCorso(int annoOtherCorso) {
        this.annoOtherCorso = annoOtherCorso;
    }

    @Basic
    @Column(name = "Tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollegCorsi that = (CollegCorsi) o;
        return thisCorso == that.thisCorso &&
                otherCorso == that.otherCorso &&
                annoThisCorso == that.annoThisCorso &&
                annoOtherCorso == that.annoOtherCorso &&
                Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thisCorso, otherCorso, annoThisCorso, annoOtherCorso, tipo);
    }
}
