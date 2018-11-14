package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Versioni {
    private String tabella;
    private int versione;

    @Id
    @Column(name = "Tabella")
    public String getTabella() {
        return tabella;
    }

    public void setTabella(String tabella) {
        this.tabella = tabella;
    }

    @Basic
    @Column(name = "Versione")
    public int getVersione() {
        return versione;
    }

    public void setVersione(int versione) {
        this.versione = versione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Versioni versioni = (Versioni) o;
        return versione == versioni.versione &&
                Objects.equals(tabella, versioni.tabella);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabella, versione);
    }
}
