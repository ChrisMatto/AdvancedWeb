package Controller;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class ResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext){
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Origin",
                "*");
        containerResponseContext.getHeaders().putSingle("Access-Control-Expose-Headers", "*");
    }
}
