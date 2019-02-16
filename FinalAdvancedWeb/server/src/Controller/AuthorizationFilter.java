package Controller;

import DataAccess.DataAccess;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import java.net.URI;

@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    private DataAccess dataAccess;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String path = containerRequestContext.getUriInfo().getPath();
        String method = containerRequestContext.getMethod();
        if(path.contains("auth")) {
            int startIndex = path.indexOf("auth") + 5;
            int endIndex = path.indexOf("/", startIndex);
            int classEndIndex;
            String token;
            String fullMethodPath;
            if(endIndex == -1) {
                fullMethodPath = path.substring(startIndex - 5);
                if(fullMethodPath.equals("auth/login")) {
                    containerRequestContext.setRequestUri(URI.create(containerRequestContext.getUriInfo().getBaseUri() + fullMethodPath));
                }
                if(fullMethodPath.equals("auth/logout")) {
                    containerRequestContext.setRequestUri(URI.create(containerRequestContext.getUriInfo().getBaseUri() + fullMethodPath));
                }
            } else {
                token = path.substring(startIndex, endIndex);
                classEndIndex = path.indexOf("/", endIndex + 1);
                if (classEndIndex > -1) {
                    String classPath = path.substring(endIndex + 1, classEndIndex);
                    if (!dataAccess.checkAccessToken(token, classPath, method)) {
                        Response.ResponseBuilder responseBuilder = Response.status(403);
                        throw  new WebApplicationException(responseBuilder.build());
                    }
                }
                fullMethodPath = path.substring(endIndex + 1);
                if (dataAccess.checkAccessToken(token, fullMethodPath, method)) {
                    containerRequestContext.setRequestUri(URI.create(containerRequestContext.getUriInfo().getBaseUri() + fullMethodPath));
                    containerRequestContext.setProperty("token", token);
                } else {
                    Response.ResponseBuilder responseBuilder = Response.status(403);
                    throw new WebApplicationException(responseBuilder.build());
                }
            }
        } else {
            int classEndIndex = path.indexOf("/");
            if (classEndIndex > -1) {
                String classPath = path.substring(0,classEndIndex);
                if (!dataAccess.checkAccessNoToken(classPath, method)) {
                    Response.ResponseBuilder responseBuilder = Response.status(403);
                    throw  new WebApplicationException(responseBuilder.build());
                }
            }
            if(!dataAccess.checkAccessNoToken(path, method)) {
                Response.ResponseBuilder responseBuilder = Response.status(403);
                throw new WebApplicationException(responseBuilder.build());
            }
        }
    }
}
