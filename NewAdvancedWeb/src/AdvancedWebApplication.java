import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

@ApplicationPath("/AdvancedWeb")

public class AdvancedWebApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        //h.add( HelloWorld.class );
        return h;
    }
}
