package Classi;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class GroupServicesPK implements Serializable {
    private int gruppo;
    private int servizio;

    @Column(name = "Gruppo")
    @Id
    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }

    @Column(name = "Servizio")
    @Id
    public int getServizio() {
        return servizio;
    }

    public void setServizio(int servizio) {
        this.servizio = servizio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupServicesPK that = (GroupServicesPK) o;
        return gruppo == that.gruppo &&
                servizio == that.servizio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gruppo, servizio);
    }
}
