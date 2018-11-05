package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "materiale_corso", schema = "newadvancedweb", catalog = "")
@IdClass(MaterialeCorsoPK.class)
public class MaterialeCorso {
    private int corso;
    private int annoCorso;
    private int materiale;

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

    @Id
    @Column(name = "Materiale")
    public int getMateriale() {
        return materiale;
    }

    public void setMateriale(int materiale) {
        this.materiale = materiale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialeCorso that = (MaterialeCorso) o;
        return corso == that.corso &&
                annoCorso == that.annoCorso &&
                materiale == that.materiale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, materiale);
    }
}
