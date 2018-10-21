package DataAccess;

import Classi.Cdl;
import Classi.Corso;
import Classi.Sessione;
import Classi.Utente;
import Controller.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jinq.jpa.JinqJPAStreamProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class DataAccess {
    private static EntityManager  em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
    private static JinqJPAStreamProvider stream = new JinqJPAStreamProvider(em.getMetamodel());

    public static List<Cdl> getCDL() {
        int year = Utils.getCurrentYear();
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == year && cdl.getAnnoFine() == year + 1 && cdl.getMagistrale() == 0)
                .toList();
    }

    public static List<Cdl> getCDLM() {
        int year = Utils.getCurrentYear();
        return stream.streamAll(em, Cdl.class)
                .where(cdl -> cdl.getAnnoInizio() == year && cdl.getAnnoFine() == year + 1 && cdl.getMagistrale() == 1)
                .toList();
    }

    public static List<Corso> getCorsi(int year) {
        return stream.streamAll(em, Corso.class)
                .where(corso -> corso.getAnnoInizio() == year && corso.getAnnoFine() == year + 1)
                .toList();
    }

    public static Utente getUtente(String username, String password) {
        Optional<Utente> u = stream.streamAll(em, Utente.class)
                .where(utente -> utente.getUsername().equals(username) && utente.getPassword().equals(password))
                .findFirst();
        return u.orElse(null);
    }

    public static String makeSessione(Sessione sessione) {
        sessione.setToken(RandomStringUtils.randomAlphanumeric(32));
        String token = sessione.getToken();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(sessione);
        transaction.commit();
        Optional<Sessione> optionalSessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token)).findFirst();
        return optionalSessione.map(Sessione::getToken).orElse(null);
    }

}
