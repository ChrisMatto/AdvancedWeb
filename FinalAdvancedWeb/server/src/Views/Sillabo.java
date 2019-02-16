package Views;

import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.inject.Inject;

public class Sillabo {

    @Inject
    @JsonIgnore
    private DataAccess dataAccess;

    @JsonView(Views.SillaboIt.class)
    private String sillaboIt;

    @JsonView(Views.SillaboEn.class)
    private String sillaboEn;

    public void init(int id, int anno) {
        this.sillaboIt = dataAccess.getDescrizioneIt(id, anno).getSillabo();
        this.sillaboEn = dataAccess.getDescrizioneEn(id, anno).getSillabo();
    }

    public String getSillaboIt() {
        return this.sillaboIt;
    }

    public void setSillaboIt(String sillaboIt) {
        this.sillaboIt = sillaboIt;
    }

    public String getSillaboEn() {
        return this.sillaboEn;
    }

    public void setSillaboEn(String sillaboEn) {
        this.sillaboEn = sillaboEn;
    }
}
