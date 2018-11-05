package ClassiTemp;

import java.util.List;

public class RelazioniCorso {
    private List<String> propedeudici;
    private List<String> mutuati;
    private List<String> modulo;

    public RelazioniCorso() {
        propedeudici = null;
        modulo = null;
    }

    public RelazioniCorso(List<String> propedeudici, List<String > mutuati, List<String> modulo) {
        this.propedeudici = propedeudici;
        this.mutuati = mutuati;
        this.modulo = modulo;
    }

    public List<String> getPropedeudici() {
        return propedeudici;
    }

    public void setPropedeudici(List<String> propedeudici) {
        this.propedeudici = propedeudici;
    }

    public List<String> getMutuati() {
        return mutuati;
    }

    public void setMutuati(List<String> mutuati) {
        this.mutuati = mutuati;
    }

    public List<String> getModulo() {
        return modulo;
    }

    public void setModulo(List<String> modulo) {
        this.modulo = modulo;
    }
}
