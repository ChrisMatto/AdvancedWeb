package Views;

import Classi.Docente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocentePerCorso {

    private Integer idDocente;
    private String nome;
    private String cognome;

    public DocentePerCorso() {
        this.idDocente = null;
        this.nome = null;
        this.cognome = null;
    }

    public DocentePerCorso(int idDocente, String nome, String cognome) {
        this.idDocente = idDocente;
        this.nome = nome;
        this.cognome = cognome;
    }

    public DocentePerCorso(Docente docente) {
        this.idDocente = docente.getIdDocente();
        this.nome = docente.getNome();
        this.cognome = docente.getCognome();
    }

    public Integer getIdDocente() {
        return this.idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    @JsonProperty
    public String getNome() {
        return this.nome;
    }

    @JsonIgnore
    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonProperty
    public String getCognome() {
        return this.cognome;
    }

    @JsonIgnore
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
