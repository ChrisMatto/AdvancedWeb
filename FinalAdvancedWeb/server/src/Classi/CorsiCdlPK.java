package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CorsiCdlPK implements Serializable {
    private int corso;
    private int annoCorso;
    private int cdl;

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

    @Column(name = "CDL")
    @Id
    public int getCdl() {
        return cdl;
    }

    public void setCdl(int cdl) {
        this.cdl = cdl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorsiCdlPK that = (CorsiCdlPK) o;
        return corso == that.corso &&
                annoCorso == that.annoCorso &&
                cdl == that.cdl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, cdl);
    }
}
