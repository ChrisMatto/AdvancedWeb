package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class DocentiCorsoPK implements Serializable {
    private int corso;
    private int docente;

    @Column(name = "Corso")
    @Id
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Column(name = "Docente")
    @Id
    public int getDocente() {
        return docente;
    }

    public void setDocente(int docente) {
        this.docente = docente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocentiCorsoPK that = (DocentiCorsoPK) o;
        return corso == that.corso &&
                docente == that.docente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, docente);
    }
}
