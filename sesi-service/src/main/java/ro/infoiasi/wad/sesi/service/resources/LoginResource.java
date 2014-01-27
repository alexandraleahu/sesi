package ro.infoiasi.wad.sesi.service.resources;

import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@PermitAll
@Path("/login")
public class LoginResource {

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
    public Response getType(@PathParam("username") String username, @Context HttpHeaders headers) {
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
