package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Sessione {
    private String token;
    private Timestamp data;
    private Integer utente;

    @Id
    @Column(name = "Token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "Data")
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Basic
    @Column(name = "Utente")
    public Integer getUtente() {
        return utente;
    }

    public void setUtente(Integer utente) {
        this.utente = utente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessione sessione = (Sessione) o;
        return Objects.equals(token, sessione.token) &&
                Objects.equals(data, sessione.data) &&
                Objects.equals(utente, sessione.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, data, utente);
    }
}
