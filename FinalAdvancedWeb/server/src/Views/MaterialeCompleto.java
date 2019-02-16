package Views;

import Classi.Materiale;
import Controller.Utils;

import java.io.File;

public class MaterialeCompleto extends Materiale {
    private double dimensioni;

    public MaterialeCompleto(Materiale materiale) {
        super();
        if (materiale == null) {
            return;
        }
        super.copyFrom(materiale);
        File file = Utils.getFile(this.getLink());
        dimensioni = file.length();
    }

    public double getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(long dimensioni) {
        this.dimensioni = dimensioni;
    }
}
