package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CorsoPK implements Serializable {
    private int idCorso;
    private int anno;

    @Column(name = "IDCorso")
    @Id
    public int getIdCorso() {
        return idCorso;
    }

    public void setIdCorso(int idCorso) {
        this.idCorso = idCorso;
    }

    @Column(name = "Anno")
    @Id
    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorsoPK corsoPK = (CorsoPK) o;
        return idCorso == corsoPK.idCorso &&
                anno == corsoPK.anno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorso, anno);
    }
}
