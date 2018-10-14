package Classi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dublino_it", schema = "advancedweb", catalog = "")
public class DublinoIt {
    private int corso;
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
        DublinoIt dublinoIt = (DublinoIt) o;
        return corso == dublinoIt.corso &&
                Objects.equals(knowledge, dublinoIt.knowledge) &&
                Objects.equals(application, dublinoIt.application) &&
                Objects.equals(evaluation, dublinoIt.evaluation) &&
                Objects.equals(communication, dublinoIt.communication) &&
                Objects.equals(lifelong, dublinoIt.lifelong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corso, knowledge, application, evaluation, communication, lifelong);
    }
}
