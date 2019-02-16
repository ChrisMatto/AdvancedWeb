package Views;

import Classi.Cdl;
import Classi.Corso;
import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CorsoPerDocente extends Corso {

    @Inject
    @JsonIgnore
    private DataAccess dataAccess;

    private List<CdlPerCorso> cdl;

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
        List<Cdl> cdlList = dataAccess.getCdlInCorso(super.getIdCorso(), super.getAnno());
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
