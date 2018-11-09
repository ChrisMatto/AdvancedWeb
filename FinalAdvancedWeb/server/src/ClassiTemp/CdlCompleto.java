package ClassiTemp;

import Classi.Cdl;
import Classi.Corso;
import DataAccess.DataAccess;

import java.util.ArrayList;
import java.util.List;

public class CdlCompleto extends Cdl {
    private List<String> corsi;

    public CdlCompleto() {
        super();
        corsi = null;
    }

    public CdlCompleto(Cdl cdl, String baseUriCorsi) {
        super();
        if (cdl == null) {
            return;
        }
        super.copyFrom(cdl);
        corsi = new ArrayList<>();
        List<Corso> co = DataAccess.getCorsiInCdl(super.getIdcdl());
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
