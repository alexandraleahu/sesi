package ro.infoiasi.wad.sesi.service.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Internships Resource (exposed at "/api/internships" path)
 */
@Path("/internships")
public class InternshipsResource {

    @Context
    Context context;

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllInternships(@QueryParam("q") String searchParam,
                                              @QueryParam("fields") List<String> fields,
                                              @QueryParam("matching") String studentId) {

        return null;
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getInternship(@PathParam("id") String internshipId,
                                    @QueryParam("fields") List<String> fields) {
        return null;
    }

    @GET
    @Path("/{id}/applications")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getInternshipStudents(
                                                @PathParam("id") String internshipId,
                                                @QueryParam("fields") List<String> fields,
                                                @QueryParam("accepted") Boolean accepted) {

        return null;
    }
}
