package DataController;

import Classi.Cdl;
import org.jinq.jpa.JinqJPAStreamProvider;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class DataController {
    private static EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
    private static JinqJPAStreamProvider stream = new JinqJPAStreamProvider(em.getMetamodel());

    public static List<Cdl> getCDL() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        if(month <= 8)
            year = year-1;
        int finalYear = year;
        return stream.streamAll(em,Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == finalYear && cdl.getAnnoFine() == finalYear + 1)
                .toList();
    }
}
