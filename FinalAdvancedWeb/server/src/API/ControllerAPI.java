package API;

import Controller.Controllers;
import Controller.Utils;
import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Path("/")
public class ControllerAPI {

    @Inject
    private DataAccess dataAccess;

    @Context
    ResourceContext context;

    @Path("{controller}")
    public Resource getController(@PathParam("controller") String controllerName, @Context Request request) {
        Controllers controller = Utils.getController(controllerName);
        if (!dataAccess.checkAccessNoToken(controllerName, request.getMethod())) {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            throw new WebApplicationException(responseBuilder.build());
        }
        switch (controller) {
            case auth:
                return context.getResource(AuthAPI.class);
            case courses:
                return context.getResource(CoursesAPI.class);
            case cdl:
                return context.getResource(CdlAPI.class);
            case teachers:
                return context.getResource(TeachersAPI.class);
            case books:
                return context.getResource(BookAPI.class);
            case material:
                return context.getResource(MaterialAPI.class);
            default:
                Response.ResponseBuilder responseBuilder = Response.status(404);
                throw new WebApplicationException(responseBuilder.build());
        }
    }
}
