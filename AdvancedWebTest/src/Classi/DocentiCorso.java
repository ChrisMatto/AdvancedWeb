package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "docenti_corso", schema = "advancedweb", catalog = "")
@IdClass(DocentiCorsoPK.class)
public class DocentiCorso {
    private int corso;
    private int docente;

    @Id
    @Column(name = "Corso")
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Id
    @Column(name = "Docente")
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
        DocentiCorso that = (DocentiCorso) o;
        return corso == that.corso &&
                docente == that.docente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, docente);
    }
}
