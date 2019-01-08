package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Docente {
    private int idDocente;
    private String immagine;
    private String nome;
    private String cognome;
    private String email;
    private String ufficio;
    private String telefono;
    private String specializzazione;
    private String ricerche;
    private String pubblicazioni;
    private String curriculum;
    private String ricevimento;

    @Id
    @Column(name = "IDDocente")
    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    @Basic
    @Column(name = "Immagine")
    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @Basic
    @Column(name = "Nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "Cognome")
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Ufficio")
    public String getUfficio() {
        return ufficio;
    }

    public void setUfficio(String ufficio) {
        this.ufficio = ufficio;
    }

    @Basic
    @Column(name = "Telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "Specializzazione")
    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @Basic
    @Column(name = "Ricerche")
    public String getRicerche() {
        return ricerche;
    }

    public void setRicerche(String ricerche) {
        this.ricerche = ricerche;
    }

    @Basic
    @Column(name = "Pubblicazioni")
    public String getPubblicazioni() {
        return pubblicazioni;
    }

    public void setPubblicazioni(String pubblicazioni) {
        this.pubblicazioni = pubblicazioni;
    }

    @Basic
    @Column(name = "Curriculum")
    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    @Basic
    @Column(name = "Ricevimento")
    public String getRicevimento() {
        return ricevimento;
    }

    public void setRicevimento(String ricevimento) {
        this.ricevimento = ricevimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Docente docente = (Docente) o;
        return idDocente == docente.idDocente &&
                Objects.equals(immagine, docente.immagine) &&
                Objects.equals(nome, docente.nome) &&
                Objects.equals(cognome, docente.cognome) &&
                Objects.equals(email, docente.email) &&
                Objects.equals(ufficio, docente.ufficio) &&
                Objects.equals(telefono, docente.telefono) &&
                Objects.equals(specializzazione, docente.specializzazione) &&
                Objects.equals(ricerche, docente.ricerche) &&
                Objects.equals(pubblicazioni, docente.pubblicazioni) &&
                Objects.equals(curriculum, docente.curriculum) &&
                Objects.equals(ricevimento, docente.ricevimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocente, immagine, nome, cognome, email, ufficio, telefono, specializzazione, ricerche, pubblicazioni, curriculum, ricevimento);
    }

    public void copyFrom(Docente docente) {
        this.idDocente = docente.idDocente;
        this.immagine = docente.immagine;
        this.nome = docente.nome;
        this.cognome = docente.cognome;
        this.email = docente.email;
        this.ufficio = docente.ufficio;
        this.telefono = docente.telefono;
        this.specializzazione = docente.specializzazione;
        this.ricerche = docente.ricerche;
        this.pubblicazioni = docente.pubblicazioni;
        this.curriculum = docente.curriculum;
        this.ricevimento = docente.ricevimento;
    }
}
