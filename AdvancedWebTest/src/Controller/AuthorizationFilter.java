package Controller;

import DataAccess.DataAccess;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import java.net.URI;

@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String path = containerRequestContext.getUriInfo().getPath();
        if(path.contains("auth")) {
            int startIndex = path.indexOf("auth") + 5;
            int endIndex = path.indexOf("/", startIndex);
            String token;
            String methodPath;
            if(endIndex == -1) {
                methodPath = path.substring(startIndex - 5);
                if(methodPath.equals("auth/login")) {
                    containerRequestContext.setRequestUri(URI.create(containerRequestContext.getUriInfo().getBaseUri() + methodPath));
                }
                if(methodPath.equals("auth/logout")) {
                    containerRequestContext.setRequestUri(URI.create(containerRequestContext.getUriInfo().getBaseUri() + methodPath));
                }
            } else {
                token = path.substring(startIndex, endIndex);
                methodPath = path.substring(endIndex + 1);
                if (DataAccess.checkAccessToken(token, methodPath)) {
                    containerRequestContext.setRequestUri(URI.create(containerRequestContext.getUriInfo().getBaseUri() + methodPath));
                } else {
                    Response.ResponseBuilder responseBuilder = Response.status(403);
                    throw new WebApplicationException(responseBuilder.build());
                }
            }
        } else {
            if(!DataAccess.checkAccessNoToken(path)) {
                Response.ResponseBuilder responseBuilder = Response.status(403);
                throw new WebApplicationException(responseBuilder.build());
            }
        }
    }
}
