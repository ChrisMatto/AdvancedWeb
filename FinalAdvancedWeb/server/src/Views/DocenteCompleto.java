package Views;

import Classi.Corso;
import Classi.Docente;
import DataAccess.DataAccess;

import java.util.ArrayList;
import java.util.List;

public class DocenteCompleto extends Docente {

    private List<CorsoPerDocente> corsi;

    public DocenteCompleto() {
        super();
        corsi = null;
    }

    public DocenteCompleto(Docente docente) {
        super();
        if (docente == null) {
            return;
        }
        super.copyFrom(docente);
        corsi = new ArrayList<>();
        List<Corso> corsiList = DataAccess.getCorsiDocente(super.getIdDocente());
        for (Corso corso : corsiList) {
            corsi.add(new CorsoPerDocente(corso));
        }
    }

    public List<CorsoPerDocente> getCorsi() {
        return corsi;
    }

    public void setCorsi(List<CorsoPerDocente> corsi) {
        this.corsi = corsi;
    }

}
