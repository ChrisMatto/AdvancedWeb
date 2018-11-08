package API;

import Controller.Controllers;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Path("/")
public class ControllerAPI {

    @Path("{controller}")
    public Resource getController(@PathParam("controller") String controllerName, @Context Request request) {
        Controllers controller = Utils.getController(controllerName);
        if (!DataAccess.checkAccessNoToken(controllerName, request.getMethod())) {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            throw new WebApplicationException(responseBuilder.build());
        }
        switch (controller) {
            case auth:
                return new AuthAPI();
            case courses:
                return new CoursesAPI();
            case cdl:
                return new CdlAPI();
            default:
                Response.ResponseBuilder responseBuilder = Response.status(404);
                throw new WebApplicationException(responseBuilder.build());
        }
    }
}
