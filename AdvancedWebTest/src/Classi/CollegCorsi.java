package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "colleg_corsi", schema = "advancedweb", catalog = "")
@IdClass(CollegCorsiPK.class)
public class CollegCorsi {
    private int thisCorso;
    private int otherCorso;
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
                Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thisCorso, otherCorso, tipo);
    }
}
