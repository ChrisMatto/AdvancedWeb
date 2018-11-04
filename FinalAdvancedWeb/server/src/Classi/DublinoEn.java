package Classi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dublino_en", schema = "newadvancedweb", catalog = "")
@IdClass(DublinoEnPK.class)
public class DublinoEn implements Dublino {

    @JsonIgnore
    private int corso;

    @JsonIgnore
    private int annoCorso;

    private String knowledge;
    private String application;
    private String evaluation;
    private String communication;
    private String lifelong;

    @Id
    @Column(name = "Corso")
    public int getCorso() {
        return corso;
    }

    public void setCorso(int corso) {
        this.corso = corso;
    }

    @Id
    @Column(name = "AnnoCorso")
    public int getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(int annoCorso) {
        this.annoCorso = annoCorso;
    }

    @Basic
    @Column(name = "Knowledge")
    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    @Basic
    @Column(name = "Application")
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Basic
    @Column(name = "Evaluation")
    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    @Basic
    @Column(name = "Communication")
    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    @Basic
    @Column(name = "Lifelong")
    public String getLifelong() {
        return lifelong;
    }

    public void setLifelong(String lifelong) {
        this.lifelong = lifelong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DublinoEn dublinoEn = (DublinoEn) o;
        return corso == dublinoEn.corso &&
                annoCorso == dublinoEn.annoCorso &&
                Objects.equals(knowledge, dublinoEn.knowledge) &&
                Objects.equals(application, dublinoEn.application) &&
                Objects.equals(evaluation, dublinoEn.evaluation) &&
                Objects.equals(communication, dublinoEn.communication) &&
                Objects.equals(lifelong, dublinoEn.lifelong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, annoCorso, knowledge, application, evaluation, communication, lifelong);
    }
}
