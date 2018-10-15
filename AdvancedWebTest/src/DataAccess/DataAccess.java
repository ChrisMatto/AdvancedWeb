package DataAccess;

import Classi.Cdl;
import org.jinq.jpa.JinqJPAStreamProvider;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class DataAccess {
    private static EntityManager  em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
    private static JinqJPAStreamProvider stream = new JinqJPAStreamProvider(em.getMetamodel());
    private static LocalDate date = LocalDate.now();
    private static int month = date.getMonthValue();
    private static int year = date.getYear();

    public static List<Cdl> getCDL() {
        int finalYear;
        if(month <= 8)
            finalYear = year-1;
        else
            finalYear = year;
        return stream.streamAll(em,Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == finalYear && cdl.getAnnoFine() == finalYear + 1 && cdl.getMagistrale() == 0)
                .toList();
    }

    public static List<Cdl> getCDLM() {
        int finalYear;
        if(month <= 8)
            finalYear = year-1;
        else
            finalYear = year;
        return stream.streamAll(em,Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == finalYear && cdl.getAnnoFine() == finalYear + 1 && cdl.getMagistrale() == 1)
                .toList();
    }

}
