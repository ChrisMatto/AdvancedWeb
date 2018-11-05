package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class DescrizioneItPK implements Serializable {
    private int corso;
    private int annoCorso;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescrizioneItPK that = (DescrizioneItPK) o;
        return corso == that.corso &&
                annoCorso == that.annoCorso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso);
    }
}
