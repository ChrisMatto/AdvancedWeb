package Controller;

import API.*;
import DataAccess.DataAccess;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

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
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(DataAccess.class);
            }
        });
    }
}
