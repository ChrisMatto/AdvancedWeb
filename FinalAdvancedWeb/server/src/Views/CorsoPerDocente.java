package Views;

import Classi.Cdl;
import Classi.Corso;
import DataAccess.DataAccess;

import java.util.ArrayList;
import java.util.List;

public class CorsoPerDocente extends Corso {

    List<CdlPerCorso> cdl;

    public CorsoPerDocente() {
        super();
        cdl = null;
    }

    public CorsoPerDocente(Corso corso) {
        super();
        if (corso == null) {
            return;
        }
        super.copyFrom(corso);
        cdl = new ArrayList<>();
        List<Cdl> cdlList = DataAccess.getCdlInCorso(super.getIdCorso(), super.getAnno());
        for (Cdl c : cdlList) {
            cdl.add(new CdlPerCorso(c));
        }
    }

    public List<CdlPerCorso> getCdl() {
        return cdl;
    }

    public void setCdl(List<CdlPerCorso> cdl) {
        this.cdl = cdl;
    }
}
