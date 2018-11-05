package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MaterialeCorsoPK implements Serializable {
    private int corso;
    private int annoCorso;
    private int materiale;

    @Column(name = "Corso")
    @Id
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Column(name = "AnnoCorso")
    @Id
    public int getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(int annoCorso) {
        this.annoCorso = annoCorso;
    }

    @Column(name = "Materiale")
    @Id
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
        MaterialeCorsoPK that = (MaterialeCorsoPK) o;
        return corso == that.corso &&
                annoCorso == that.annoCorso &&
                materiale == that.materiale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, materiale);
    }
}
