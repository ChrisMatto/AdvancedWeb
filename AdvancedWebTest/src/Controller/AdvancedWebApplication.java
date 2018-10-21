package Controller;

import API.CDLAPI;
import API.CoursesAPI;
import API.DocentiAPI;

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
        return h;
    }
}
