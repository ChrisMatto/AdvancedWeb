package ClassiTemp;

import Classi.Cdl;
import com.fasterxml.jackson.annotation.JsonView;

public class CdlPerCorso {

    private Integer idCdl;

    @JsonView(CorsoView.Ita.class)
    private String abbrIt;

    @JsonView(CorsoView.En.class)
    private String abbrEn;

    @JsonView(CorsoView.Ita.class)
    private String nomeIt;

    @JsonView(CorsoView.En.class)
    private String nomeEn;

    public CdlPerCorso () {
        this.idCdl = null;
        this.abbrIt = null;
        this.abbrEn = null;
        this.nomeIt = null;
        this.nomeEn = null;
    }

    public CdlPerCorso (Cdl cdl) {
        this.idCdl = cdl.getIdcdl();
        this.abbrIt = cdl.getAbbrIt();
        this.abbrEn = cdl.getAbbrEn();
        this.nomeIt = cdl.getNomeIt();
        this.nomeEn = cdl.getNomeEn();
    }

    public Integer getIdCdl() {
        return this.idCdl;
    }

    public void setIdCdl(int idCdl) {
        this.idCdl = idCdl;
    }

    public String getAbbrIt() {
        return this.abbrIt;
    }

    public void setAbbrIt(String abbrIt) {
        this.abbrIt = abbrIt;
    }

    public String getAbbrEn(String abbrEn) {
        return this.abbrEn;
    }

    public void setAbbrEn(String abbrEn) {
        this.abbrEn = abbrEn;
    }

    public String getNomeIt() {
        return nomeIt;
    }

    public void setNomeIt(String nomeIt) {
        this.nomeIt = nomeIt;
    }

    public String getNomeEn() {
        return nomeEn;
    }

    public void setNomeEn(String nomeEn) {
        this.nomeEn = nomeEn;
    }
}
