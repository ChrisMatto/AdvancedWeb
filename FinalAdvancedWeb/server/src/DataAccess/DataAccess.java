package DataAccess;

import Classi.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;
import org.jinq.tuples.Pair;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class DataAccess {
    private static EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
    private static JinqJPAStreamProvider stream = new JinqJPAStreamProvider(em.getMetamodel());
    private static EntityTransaction entityTransaction = em.getTransaction();

    public static List<Corso> getCorsiByFilter(int year, MultivaluedMap<String,String> queryParams) {
        JinqStream<Corso> streamCorsi = stream.streamAll(em,Corso.class)
                .where(corso -> corso.getAnnoInizio() == year);
        for (String key : queryParams.keySet()) {
            String param = queryParams.get(key).get(0);
            switch (key) {
                case "cdl":
                    int idCdl;
                    if (NumberUtils.isParsable(param)) {
                        idCdl = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi
                            .join((c,source) -> source.stream(CorsiCdl.class))
                            .where(corsoCorsiCdlPair -> corsoCorsiCdlPair.getTwo().getCdl() == idCdl && corsoCorsiCdlPair.getOne().getIdCorso() == corsoCorsiCdlPair.getTwo().getCorso())
                            .select(org.jinq.tuples.Pair::getOne);
                    break;
                case "nome":
                    streamCorsi = streamCorsi.where(corso -> corso.getNomeIt().equals(param) || corso.getNomeEn().equals(param));
                    break;
                case "lingua":
                    streamCorsi = streamCorsi.where(corso -> corso.getLingua().equals(param));
                    break;
                case "ssd":
                    streamCorsi = streamCorsi.where(corso -> corso.getSsd().equals(param));
                    break;
                case "semestre":
                    int semestre;
                    if (NumberUtils.isParsable(param)) {
                        semestre = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi.where(corso -> corso.getSemestre() == semestre);
                    break;
                case "cfu":
                    int cfu;
                    if (NumberUtils.isParsable(param)) {
                        cfu = Integer.parseInt(param);
                    } else {
                        break;
                    }
                    streamCorsi = streamCorsi.where(corso -> corso.getCfu() == cfu);
                    break;
                case "tipologia":
                    streamCorsi = streamCorsi.where(corso -> corso.getTipologia().equals(param));
                    break;
            }
        }
        return streamCorsi.toList();
    }

    public static Utente getUtente(String username, String password) {
        Optional<Utente> u = stream.streamAll(em, Utente.class)
                .where(utente -> utente.getUsername().equals(username) && utente.getPassword().equals(password))
                .findFirst();
        return u.orElse(null);
    }

    public static String makeSessione(Utente utente) {
        Sessione sessione = new Sessione();
        sessione.setUtente(utente.getIdUtente());
        sessione.setData(new Timestamp(System.currentTimeMillis()));
        sessione.setToken(RandomStringUtils.randomAlphanumeric(32));
        String token = sessione.getToken();
        entityTransaction.begin();
        em.persist(sessione);
        entityTransaction.commit();
        Optional<Sessione> optionalSessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token)).findFirst();
        return optionalSessione.map(Sessione::getToken).orElse(null);
    }

    public static void deleteSession(String token) {
        Optional<Sessione> sessione = stream.streamAll(em, Sessione.class)
                .where(s -> s.getToken().equals(token))
                .findFirst();
        if(sessione.isPresent()) {
            entityTransaction.begin();
            em.remove(sessione.get());
            entityTransaction.commit();
        }
    }

    public static Boolean checkAccessToken(String token, String service, String method) {
        Optional<Sessione> optionalSessione = stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getToken().equals(token))
                .findFirst();
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service) && s.getMetodo().equals(method))
                .findFirst();
        if(!optionalSessione.isPresent()) {
            return false;
        } else {
            if(!optionalServizio.isPresent()) {
                return true;
            }
        }

        Optional<Servizio> servizio = stream.streamAll(em, Sessione.class)
                .where(sessione -> sessione.getToken().equals(token))
                .join((s, source) -> source.stream(Utente.class))
                .where(sessioneUtentePair -> sessioneUtentePair.getOne().getUtente() == sessioneUtentePair.getTwo().getIdUtente())
                .select(Pair::getTwo)
                .join((s, source) -> source.stream(GroupServices.class))
                .where(utenteGroupServicesPair -> utenteGroupServicesPair.getOne().getGruppo() == utenteGroupServicesPair.getTwo().getGruppo())
                .select(Pair::getTwo)
                .join((s, source) -> source.stream(Servizio.class))
                .where(groupServicesServizioPair -> groupServicesServizioPair.getOne().getServizio() == groupServicesServizioPair.getTwo().getIdServizio() &&
                        groupServicesServizioPair.getTwo().getNome().equals(service) && groupServicesServizioPair.getTwo().getMetodo().equals(method))
                .select(Pair::getTwo)
                .findFirst();
        return servizio.isPresent();
    }

    public static Boolean checkAccessNoToken(String service, String method) {
        Optional<Servizio> optionalServizio = stream.streamAll(em, Servizio.class)
                .where(s -> s.getNome().equals(service) && s.getMetodo().equals(method))
                .findFirst();
        return !optionalServizio.isPresent();
    }
}