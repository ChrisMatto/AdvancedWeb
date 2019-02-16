package Controller;

import API.*;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
/*public class AdvancedWebApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(JacksonFeature.class);
        h.add(ResponseFilter.class);
        h.add(ControllerAPI.class);
        return h;
    }
}*/

public class AdvancedWebApplication extends ResourceConfig {
    public AdvancedWebApplication() {
        register(JacksonFeature.class);
        register(ResponseFilter.class);
        register(ControllerAPI.class);
    }
}
