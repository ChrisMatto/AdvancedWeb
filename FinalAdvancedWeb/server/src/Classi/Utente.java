package Classi;

import ClassiTemp.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Utente {

    @JsonView(Views.SimpleUtente.class)
    private int idUtente;

    @JsonView(Views.SimpleUtente.class)
    private String username;

    private String password;

    private Integer docente;

    @JsonView(Views.FullUtente.class)
    private Integer gruppo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUtente")
    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    @Basic
    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @JsonIgnore
    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "Docente")
    public Integer getDocente() {
        return docente;
    }

    public void setDocente(Integer docente) {
        this.docente = docente;
    }

    @Basic
    @Column(name = "Gruppo")
    public Integer getGruppo() {
        return gruppo;
    }

    public void setGruppo(Integer gruppo) {
        this.gruppo = gruppo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return idUtente == utente.idUtente &&
                Objects.equals(username, utente.username) &&
                Objects.equals(password, utente.password) &&
                Objects.equals(docente, utente.docente) &&
                Objects.equals(gruppo, utente.gruppo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtente, username, password, docente, gruppo);
    }
}
