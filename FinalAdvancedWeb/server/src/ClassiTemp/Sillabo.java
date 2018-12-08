package ClassiTemp;

import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonView;

public class Sillabo {

    @JsonView(Views.SillaboIt.class)
    private String sillaboIt;

    @JsonView(Views.SillaboEn.class)
    private String sillaboEn;

    public Sillabo(int id, int anno) {
        this.sillaboIt = DataAccess.getDescrizioneIt(id, anno).getSillabo();
        this.sillaboEn = DataAccess.getDescrizioneEn(id, anno).getSillabo();
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
