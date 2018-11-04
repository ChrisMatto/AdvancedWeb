package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "libri_corso", schema = "newadvancedweb", catalog = "")
@IdClass(LibriCorsoPK.class)
public class LibriCorso {
    private int corso;
    private int annoCorso;
    private int libro;

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
    @Column(name = "Libro")
    public int getLibro() {
        return libro;
    }

    public void setLibro(int libro) {
        this.libro = libro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibriCorso that = (LibriCorso) o;
        return corso == that.corso &&
                annoCorso == that.annoCorso &&
                libro == that.libro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, libro);
    }
}
