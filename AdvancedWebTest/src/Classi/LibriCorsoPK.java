package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class LibriCorsoPK implements Serializable {
    private int corso;
    private int libro;

    @Column(name = "Corso")
    @Id
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Column(name = "Libro")
    @Id
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
        LibriCorsoPK that = (LibriCorsoPK) o;
        return corso == that.corso &&
                libro == that.libro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, libro);
    }
}
