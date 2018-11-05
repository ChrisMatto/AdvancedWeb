package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Servizio {
    private int idServizio;
    private String nome;
    private String metodo;

    @Id
    @Column(name = "IDServizio")
    public int getIdServizio() {
        return idServizio;
    }

    public void setIdServizio(int idServizio) {
        this.idServizio = idServizio;
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
    @Column(name = "Metodo")
    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servizio servizio = (Servizio) o;
        return idServizio == servizio.idServizio &&
                Objects.equals(nome, servizio.nome) &&
                Objects.equals(metodo, servizio.metodo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idServizio, nome, metodo);
    }
}
