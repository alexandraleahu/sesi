package ro.infoiasi.wad.sesi.service.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

@PermitAll
@Path("/login")
public class Login {

    @Context
    private SecurityContext context;

    private static final UsersTable usersTable = new UsersTable();

    static {
        usersTable.createTable();
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        if (usersTable.login(username, password) != null) {
            return Response.ok().build();
        }
        return Response.status(Status.FORBIDDEN).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(@FormParam("username") String username, @FormParam("password") String password,
            @FormParam("type") String type) {
        if(usersTable.addUser(new DBUser(username, password, type))) {
            return Response.ok().build();
        }
        return Response.status(Status.FORBIDDEN).build();
    }

    @GET
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getType(@QueryParam("username") String username, @Context HttpHeaders headers) {
        List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
        MediaTypeConstants.MediaTypeAndRdfFormat returnTypes =
                MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);
        String type = usersTable.getUserType(username);
        if (type != null) {
            return Response.ok(type, returnTypes.getMediaType()).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
}
