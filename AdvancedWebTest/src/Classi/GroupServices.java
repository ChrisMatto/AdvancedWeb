package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_services", schema = "advancedweb", catalog = "")
@IdClass(GroupServicesPK.class)
public class GroupServices {
    private int gruppo;
    private int servizio;

    @Id
    @Column(name = "Gruppo")
    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }

    @Id
    @Column(name = "Servizio")
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
        GroupServices that = (GroupServices) o;
        return gruppo == that.gruppo &&
                servizio == that.servizio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gruppo, servizio);
    }
}
