package Views;

import Classi.Cdl;
import Classi.Corso;
import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CdlCompleto extends Cdl {

    @Inject
    @JsonIgnore
    private DataAccess dataAccess;

    private List<String> corsi;

    public void init(Cdl cdl, String baseUriCorsi) {
        if (cdl == null) {
            return;
        }
        super.copyFrom(cdl);
        corsi = new ArrayList<>();
        List<Corso> co = dataAccess.getCorsiInCdl(super.getIdcdl());
        for (Corso corso : co) {
            corsi.add(baseUriCorsi + corso.getAnno() + "/" + corso.getIdCorso());
        }
    }

    public List<String> getCorsi() {
        return corsi;
    }

    public void setCorsi(List<String> corsi) {
        this.corsi = corsi;
    }
}
