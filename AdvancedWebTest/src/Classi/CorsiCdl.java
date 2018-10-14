package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "corsi_cdl", schema = "advancedweb", catalog = "")
@IdClass(CorsiCdlPK.class)
public class CorsiCdl {
    private int corso;
    private int cdl;

    @Id
    @Column(name = "Corso")
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Id
    @Column(name = "CDL")
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
        CorsiCdl corsiCdl = (CorsiCdl) o;
        return corso == corsiCdl.corso &&
                cdl == corsiCdl.cdl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, cdl);
    }
}
