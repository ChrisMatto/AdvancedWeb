package Classi;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Log {
    private int idLog;
    private int utente;
    private Timestamp data;
    private String descrizione;

    @Id
    @Column(name = "IDLog")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    @Basic
    @Column(name = "Utente")
    public int getUtente() {
        return utente;
    }

    public void setUtente(int utente) {
        this.utente = utente;
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
    @Column(name = "Descrizione")
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return idLog == log.idLog &&
                utente == log.utente &&
                Objects.equals(data, log.data) &&
                Objects.equals(descrizione, log.descrizione);
    }

    public void copyFrom(Log log) {
        this.idLog = log.idLog;
        this.utente = log.utente;
        this.data = log.data;
        this.descrizione = log.descrizione;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLog, utente, data, descrizione);
    }
}
