package Classi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Libro {
    private int idLibro;
    private String autore;
    private String titolo;
    private Integer volume;
    private int anno;
    private String editore;
    private String link;

    @Id
    @Column(name = "IDLibro")
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    @Basic
    @Column(name = "Autore")
    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    @Basic
    @Column(name = "Titolo")
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Basic
    @Column(name = "Volume")
    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "Anno")
    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    @Basic
    @Column(name = "Editore")
    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    @Basic
    @Column(name = "Link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return idLibro == libro.idLibro &&
                anno == libro.anno &&
                Objects.equals(autore, libro.autore) &&
                Objects.equals(titolo, libro.titolo) &&
                Objects.equals(volume, libro.volume) &&
                Objects.equals(editore, libro.editore) &&
                Objects.equals(link, libro.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLibro, autore, titolo, volume, anno, editore, link);
    }
}
