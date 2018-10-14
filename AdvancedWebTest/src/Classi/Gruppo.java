package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Gruppo {
    private int idGruppo;
    private String nome;

    @Id
    @Column(name = "IDGruppo")
    public int getIdGruppo() {
        return idGruppo;
    }

    public void setIdGruppo(int idGruppo) {
        this.idGruppo = idGruppo;
    }

    @Basic
    @Column(name = "Nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gruppo gruppo = (Gruppo) o;
        return idGruppo == gruppo.idGruppo &&
                Objects.equals(nome, gruppo.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGruppo, nome);
    }
}
