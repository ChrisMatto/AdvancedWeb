package Controller;

import API.AuthAPI;
import API.CDLAPI;
import API.CoursesAPI;
import API.DocentiAPI;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class AdvancedWebApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(CDLAPI.class);
        h.add(CoursesAPI.class);
        h.add(DocentiAPI.class);
        h.add(AuthAPI.class);
        h.add(JacksonFeature.class);
        h.add(AuthorizationFilter.class);
        return h;
    }
}
