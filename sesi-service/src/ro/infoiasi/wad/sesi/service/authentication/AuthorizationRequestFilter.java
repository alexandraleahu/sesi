package ro.infoiasi.wad.sesi.service.authentication;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
 
public class AuthorizationRequestFilter implements ContainerRequestFilter {
 
    @Override
    public void filter(ContainerRequestContext requestContext)
                    throws IOException {
        final SecurityContext securityContext = requestContext.getSecurityContext();
        if (securityContext == null) {
            fail(requestContext);
        }
        
        String authScheme = securityContext.getAuthenticationScheme();
        if (authScheme == null
                || !authScheme.equals(SecurityContext.BASIC_AUTH)) {
            fail(requestContext);
        }
        if (securityContext == null ||
                    !securityContext.isUserInRole("privileged")) {
 
        }
    }

    private void fail(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response
            .status(Response.Status.UNAUTHORIZED)
            .entity("User cannot access the resource.")
            .build());
    }
}
