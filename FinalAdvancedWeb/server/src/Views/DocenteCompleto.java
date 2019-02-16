package Views;

import Classi.Corso;
import Classi.Docente;
import DataAccess.DataAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.inject.Inject;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

public class DocenteCompleto extends Docente {

    @Inject
    @JsonIgnore
    private DataAccess dataAccess;

    @Context
    @JsonIgnore
    private ResourceContext context;

    private List<CorsoPerDocente> corsi;

    public void init(Docente docente) {
        if (docente == null) {
            return;
        }
        super.copyFrom(docente);
        corsi = new ArrayList<>();
        List<Corso> corsiList = dataAccess.getCorsiDocente(super.getIdDocente());
        for (Corso corso : corsiList) {
            CorsoPerDocente c = context.getResource(CorsoPerDocente.class);
            c.init(corso);
            corsi.add(c);
        }
    }

    public List<CorsoPerDocente> getCorsi() {
        return corsi;
    }

    public void setCorsi(List<CorsoPerDocente> corsi) {
        this.corsi = corsi;
    }

}
