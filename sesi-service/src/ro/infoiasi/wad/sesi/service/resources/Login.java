package ro.infoiasi.wad.sesi.service.resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@PermitAll
@Path("/login")
public class Login {

    @Context
    private SecurityContext context;
}
