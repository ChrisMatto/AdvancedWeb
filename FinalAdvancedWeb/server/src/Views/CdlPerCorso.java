package Views;

import Classi.Cdl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class CdlPerCorso {

    private Integer idCdl;

    @JsonView(Views.CorsoIta.class)
    private String abbrIt;

    @JsonView(Views.CorsoEn.class)
    private String abbrEn;

    @JsonView(Views.CorsoIta.class)
    private String nomeIt;

    @JsonView(Views.CorsoEn.class)
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
    @JsonProperty
    public String getAbbrIt() {
        return this.abbrIt;
    }
    @JsonIgnore
    public void setAbbrIt(String abbrIt) {
        this.abbrIt = abbrIt;
    }
    @JsonProperty
    public String getAbbrEn(String abbrEn) {
        return this.abbrEn;
    }
    @JsonIgnore
    public void setAbbrEn(String abbrEn) {
        this.abbrEn = abbrEn;
    }
    @JsonProperty
    public String getNomeIt() {
        return nomeIt;
    }
    @JsonIgnore
    public void setNomeIt(String nomeIt) {
        this.nomeIt = nomeIt;
    }
    @JsonProperty
    public String getNomeEn() {
        return nomeEn;
    }
    @JsonIgnore
    public void setNomeEn(String nomeEn) {
        this.nomeEn = nomeEn;
    }
}
