package Controller;

import API.*;
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
        h.add(UsersAPI.class);
        h.add(CompleteCoursesAPI.class);
        h.add(ResponseFilter.class);
        return h;
    }
}
