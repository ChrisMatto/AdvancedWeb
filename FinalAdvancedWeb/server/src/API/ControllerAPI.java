package API;

import Controller.Utils;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Path("/")
public class ControllerAPI {

    @Path("{controller}")
    public Object getController(@PathParam("controller") String controllerName, @Context Request request) {
        String method = request.getMethod();
        return Utils.getController(controllerName);
    }
}
